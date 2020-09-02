/*
 * Copyright (c) 2018 Keep, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.hdsx.simpledata;


import com.hdsx.simpledata.annotation.ParamField;
import com.hdsx.simpledata.exception.XResolverAnnotationException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * XResolver 注解关联工具
 */
@SuppressWarnings("unchecked")
class AnnotationUtils {

    /**
     * ParamField order comparator
     */
    private static final Comparator<Field> ORDER_COMPARATOR = new Comparator<Field>() {
        @Override
        public int compare(Field left, Field right) {
            return left.getAnnotation(ParamField.class).order() - right.getAnnotation(ParamField.class).order();
        }
    };

    /**
     * 缓存检查通过的类类型
     */
    private static final Set<Class<? extends XResolverData>> ANNOTATION_CHECK_CACHE = new HashSet<>();

    private AnnotationUtils() {}

    /**
     * 在处理前预先检查注释
     *
     * illegal use will throw {@link XResolverAnnotationException}
     */
    public static void checkAnnotationOrThrow(Class<? extends XResolverData> clazz) throws XResolverAnnotationException {
        if (ANNOTATION_CHECK_CACHE.contains(clazz)) {
            return;
        }
        Set<Class<? extends XResolverData>> recursiveTypeSet = new HashSet<>();
        checkAnnotationOrThrow(clazz, false, recursiveTypeSet);
        ANNOTATION_CHECK_CACHE.add(clazz);
    }

    /**
     * 在处理前预先检查注释
     *
     * @param clazz class type
     * @param isRecursive whether it is a recursive call
     * @param recursiveTypeSet TairaData types in recursive calls
     */
    private static void checkAnnotationOrThrow(Class<? extends XResolverData> clazz, boolean isRecursive,
                                               Set<Class<? extends XResolverData>> recursiveTypeSet) {
        List<Field> fields = ReflectionUtils.extractAnnotatedFields(clazz, ParamField.class);

        if (fields.isEmpty()) {
            throw new XResolverAnnotationException("No @ParamField declared in class [" + clazz.getName() + "]");
        }
        if (!ReflectionUtils.isNonParamConstructorExists(clazz)) {
            throw new XResolverAnnotationException(
                "Class [" + clazz.getName() + "] should define a non-parameter constructor");
        }
        if (recursiveTypeSet.contains(clazz)) {
            throw new XResolverAnnotationException("Recursive TairaData type " + clazz.getName() + " already exists");
        }

        Collections.sort(fields, ORDER_COMPARATOR);

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            Class fieldType = field.getType();
            XResolverPrimitive primitive = XResolverTypeConst.findPrimitive(fieldType);

            // 检查ParamField
            checkParamFieldUsage(i, fields.size(), field, clazz, primitive, isRecursive);

            // 检查 Fields
            if (primitive != null) {
                continue;
            }

            // 是否是ResolverData类型
            if (XResolverTypeConst.isXResolverDataClass(fieldType)) {
                recursiveTypeSet.add(clazz);
                checkAnnotationOrThrow(fieldType, true, recursiveTypeSet);
                continue;
            }

            // String & byte[]，skip
            if (XResolverTypeConst.isByteArray(field)) {
                continue;
            }

            // 集合或者是其他数组类型
            if (XResolverTypeConst.isSupportedCollection(fieldType) || fieldType.isArray()) {
                Class memberType = ReflectionUtils.getCollectionFirstMemberType(field);
                // abstract or interface member type not supported
                if (ReflectionUtils.isInterfaceOrAbstract(memberType)) {
                    throw new XResolverAnnotationException(
                        "Member type of collection field [" + field.getName() + "] in class [" + clazz.getName()
                            + "] should not be interface or abstract");
                }

                // 成员类型，递归检查
                if (XResolverTypeConst.isXResolverDataClass(memberType)) {
                    recursiveTypeSet.add(clazz);
                    checkAnnotationOrThrow(memberType, true, recursiveTypeSet);
                } else {
                    PrimitiveProcessor memberProcessor = XResolverTypeConst.findPrimitive(memberType);
                    // 不支持的成员类型
                    if (memberProcessor == null) {
                        throw new XResolverAnnotationException(
                            "Member type of collection field [" + field.getName() + "] in class [" + clazz.getName()
                                + "] can only be primitive type or TairaData");
                    }
                }
                continue;
            }

            // 不支持的字段类型
            throw new XResolverAnnotationException(
                "Type of field [" + field.getName() + "] in class [" + clazz.getName() + "] is not supported");
        }
    }

    /**
     * check ParamField
     */
    private static void checkParamFieldUsage(int fieldIndex, int fieldsSize, Field field, Class clazz,
                                             XResolverPrimitive primitive, boolean isRecursive) {
        ParamField annotation = field.getAnnotation(ParamField.class);

        // 检测排序
        if (annotation.order() != fieldIndex) {
            throw new XResolverAnnotationException(
                "[order] on field [" + field.getName() + "] in class [" + clazz.getName() + "] is not sequential");
        }

        // check String & byte[]
        if (XResolverTypeConst.isByteArray(field) && annotation.bytes() <= 0) {
            // non-recursive byte array can pass
            if (!isRecursive) {
                return;
            }
            throw new XResolverAnnotationException(
                "Field [" + field.getName() + "] in class [" + clazz.getName() + "] should specify [bytes] value");
        }

        // 检测 primitive type bytes overflow
        if (primitive != null && annotation.bytes() > 0 && annotation.bytes() > primitive.byteSize()) {
            throw new XResolverAnnotationException("[bytes] on field [" + field.getName() + "] in class [" + clazz.getName()
                + "] is too large (which should be lesser than or equal to " + primitive.byteSize() + ")");
        }

        // 检测 集合 & 数组 长度
        if (!XResolverTypeConst.isByteArray(field) && (XResolverTypeConst.isSupportedCollection(field.getType())
            || field.getType().isArray())) {
            // tail field without length
            if (fieldIndex < fieldsSize - 1 && annotation.length() <= 0) {
                throw new XResolverAnnotationException(
                    "Field [" + field.getName() + "] in class [" + clazz.getName() + "] should specify [length] value");
            }
            // recursive TairaData tail field without length
            if (fieldIndex == fieldsSize - 1 && isRecursive && annotation.length() <= 0) {
                throw new XResolverAnnotationException(
                    "Field [" + field.getName() + "] in recursive class [" + clazz.getName()
                        + "] should specify [length] value");
            }
        }
    }

    /**
     * 获取 @ParamField 注解 字段s, 并且根据order排序
     *
     * @param clazz class type
     * @return sorted fields
     */
    public static List<Field> getSortedParamFields(Class<?> clazz) {
        List<Field> paramFields = ReflectionUtils.extractAnnotatedFields(clazz, ParamField.class);
        Collections.sort(paramFields, ORDER_COMPARATOR);
        return paramFields;
    }
}
