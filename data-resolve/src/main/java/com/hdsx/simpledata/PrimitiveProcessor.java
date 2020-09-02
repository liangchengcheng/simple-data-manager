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

import java.nio.ByteBuffer;

/**
 * 过程 原始类型
 */
interface PrimitiveProcessor {

    /**
     * 序列化当前值到 buffer
     *
     * @param size lower byte size
     */
    void serialize(Object value, ByteBuffer buffer, int size);

    /**
     * 反序列化值从 buffer
     *
     * @param size byte size
     */
    Object deserialize(ByteBuffer buffer, int size);

    /**
     * 是否可加工的
     */
    boolean canProcess(Class clazz);

    /**
     * 原始字节大小
     */
    int byteSize();

    /**
     * 反序列化时的默认值
     */
    Object defaultValue();
}
