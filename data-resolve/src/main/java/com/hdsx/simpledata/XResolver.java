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

import com.hdsx.simpledata.exception.XResolverAnnotationException;
import com.hdsx.simpledata.exception.XResolverIllegalValueException;
import com.hdsx.simpledata.exception.XResolverInternalException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * XResolver API
 */
public class XResolver {

    /**
     * 默认的初始化
     */
    public static final XResolver DEFAULT = new XResolver();

    /**
     * 是否是调试模式
     *
     * in debug mode, {@link XResolverInternalException} may throw
     */
    public static boolean DEBUG = false;

    /**
     * 默认的String编码方式 encoding/decoding
     */
    private Charset charset = Charset.forName("utf-8");

    /**
     * 默认的字节的排序方式- 大小端
     */
    private ByteOrder order = ByteOrder.BIG_ENDIAN;

    /**
     * cache XResolverData root, speed up further executions
     */
    private final Map<Class<? extends XResolverData>, XResolverDataNode> rootNodeCache = new HashMap<>();

    /**
     * 默认
     */
    private XResolver() {}

    /**
     *  初始化的构造器
     *
     * @param order specified byte order
     */
    public XResolver(ByteOrder order) {
        this.order = order;
    }

    /**
     * 初始化的构造器
     */
    public XResolver(Charset charset) {
        this.charset = charset;
    }

    /**
     * 初始化的构造器
     */
    public XResolver(Charset charset, ByteOrder order) {
        this.charset = charset;
        this.order = order;
    }

    /**
     * 序列化对象变成byte array
     *
     * @param object object instance to serialize
     * @return byte array
     * @throws XResolverAnnotationException when annotation error
     * @throws XResolverIllegalValueException when value error
     */
    public <T extends XResolverData> byte[] toBytes(T object) throws XResolverAnnotationException, XResolverIllegalValueException {
        if (object == null) {
            return null;
        }
        try {
            AnnotationUtils.checkAnnotationOrThrow(object.getClass());
            return serializeArray(object);
        } catch (XResolverInternalException e) {
            if (DEBUG) {
                throw e;
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 序列化对象ByteBuffer
     *
     * @param object object instance to serialize
     * @return byte array
     * @throws XResolverAnnotationException when annotation error
     * @throws XResolverIllegalValueException when value error
     */
    public <T extends XResolverData> ByteBuffer toByteBuffer(T object)
        throws XResolverAnnotationException, XResolverIllegalValueException {
        if (object == null) {
            return null;
        }
        try {
            AnnotationUtils.checkAnnotationOrThrow(object.getClass());
            return serializeBuffer(object);
        } catch (XResolverInternalException e) {
            if (DEBUG) {
                throw e;
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反序列化数据到从 byte array 到 对象
     *
     * @param data byte array data
     * @param clazz TairaData type
     * @return TairaData instance
     * @throws XResolverAnnotationException when annotation error
     */
    public <T extends XResolverData> T fromBytes(byte[] data, Class<T> clazz) throws XResolverAnnotationException {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            AnnotationUtils.checkAnnotationOrThrow(clazz);
            return deserializeArray(data, clazz);
        } catch (XResolverInternalException e) {
            if (DEBUG) {
                throw e;
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反序列化数据到从 ByteBuffer 到 对象
     *
     * @param data byte array data
     * @param clazz TairaData type
     * @return TairaData instance
     * @throws XResolverAnnotationException when annotation error
     */
    public <T extends XResolverData> T fromByteBuffer(ByteBuffer data, Class<T> clazz) throws XResolverAnnotationException {
        if (data == null || !data.hasRemaining()) {
            return null;
        }
        try {
            AnnotationUtils.checkAnnotationOrThrow(clazz);
            return deserializeBuffer(data, clazz);
        } catch (XResolverInternalException e) {
            if (DEBUG) {
                throw e;
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    private <T extends XResolverData> byte[] serializeArray(T data) {
        return serializeBuffer(data).array();
    }

    @SuppressWarnings("unchecked")
    private <T extends XResolverData> T deserializeBuffer(ByteBuffer buffer, Class<T> clazz) {
        XResolverDataNode root = getXResolverDataNode(clazz, charset);
        return (T) root.deserialize(buffer);
    }

    private <T extends XResolverData> ByteBuffer serializeBuffer(T data) {
        XResolverDataNode root = getXResolverDataNode(data.getClass(), charset);
        int byteSize = root.evaluateSize(data);
        ByteBuffer buffer;
        try {
            buffer = ByteBuffer.allocate(byteSize).order(order);
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            throw new XResolverInternalException(e);
        }
        root.serialize(buffer, data);
        buffer.flip();
        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();
        ByteBuffer compactBuffer = ByteBuffer.allocate(readonlyBuffer.limit()).order(order);
        compactBuffer.put(Arrays.copyOfRange(buffer.array(), 0, readonlyBuffer.limit()));

        return compactBuffer;
    }

    private <T extends XResolverData> T deserializeArray(byte[] array, Class<T> clazz) {
        ByteBuffer buffer;
        try {
            buffer = ByteBuffer.wrap(array).order(order);
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            throw new XResolverInternalException(e);
        }
        return deserializeBuffer(buffer, clazz);
    }

    /**
     * 获取数据节点从缓存或者新创建一个
     *
     * @param clazz class
     * @return node
     */
    private XResolverDataNode getXResolverDataNode(Class<? extends XResolverData> clazz, Charset charset) {
        XResolverDataNode node = rootNodeCache.get(clazz);
        if (node == null) {
            node = new XResolverDataNode(clazz, charset);
            rootNodeCache.put(clazz, node);
        }
        return node;
    }
}
