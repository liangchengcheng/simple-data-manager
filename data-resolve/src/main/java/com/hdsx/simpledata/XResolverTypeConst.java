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

import java.lang.reflect.Field;
import java.util.*;

/**
 * 定义一些 XResolver 支持的类型和函数
 */
final class XResolverTypeConst {

    /**
     * XResolver 初始化类型
     */
    private static final Class<XResolverData> X_RESOLVER_DATA_CLASS = XResolverData.class;

    /**
     * 当前 XResolver 支持 List & Set
     */
    private static final Set<Class<? extends Collection>> SUPPORTED_COLLECTION_TYPE = new HashSet<>();

    static {
        SUPPORTED_COLLECTION_TYPE.add(List.class);
        SUPPORTED_COLLECTION_TYPE.add(Set.class);
    }

    private XResolverTypeConst() {}

    /**
     * 获取一个基本类型处理器
     *
     * @param clazz class
     * @return primitive type
     */
    public static XResolverPrimitive findPrimitive(Class clazz) {
        for (XResolverPrimitive type : XResolverPrimitive.values()) {
            if (type.canProcess(clazz)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 它是否是一个受支持的集合？
     *
     * @param fieldType class
     * @return true if supported
     */
    public static boolean isSupportedCollection(Class fieldType) {
        for (Class clazz : XResolverTypeConst.SUPPORTED_COLLECTION_TYPE) {
            if (clazz.equals(fieldType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在反序列化时创建集合实例
     *
     * @return collection instance
     */
    public static Collection newCollection(Class fieldType) {
        if (List.class.equals(fieldType)) {
            return new ArrayList();
        } else if (Set.class.equals(fieldType)) {
            return new HashSet();
        }
        return null;
    }

    /**
     * 当 XResolver 初始化的时候
     *
     * @param clazz class
     */
    public static boolean isXResolverDataClass(Class clazz) {
        return clazz != null && ReflectionUtils.isParentClass(X_RESOLVER_DATA_CLASS, clazz);
    }

    /**
     * 当当前是byte array 或者是 string的时候
     * @param field field
     * @return true if it's String or byte[]
     */
    public static boolean isByteArray(Field field) {
        if (field == null) {
            return false;
        }
        if (field.getType().isArray()) {
            Class memberType = ReflectionUtils.getCollectionFirstMemberType(field);
            return byte.class.equals(memberType) || Byte.class.equals(memberType);
        }
        return String.class.equals(field.getType());
    }

    /**
     * 输出log日志
     *
     * @param from log tag
     * @param message log content
     */
    public static void log(String from, String message) {
        if (XResolver.DEBUG) {
            System.out.println("[" + from + "] " + message);
        }
    }
}
