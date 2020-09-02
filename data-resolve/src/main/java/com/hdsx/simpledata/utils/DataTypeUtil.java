package com.hdsx.simpledata.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * 数据类型转换
 * 梁铖城
 * 2020年08月21日07:25:20
 */
public class DataTypeUtil {
    /**
     * byte转Uint8
     */
    public static int byte2Uint8(byte b) {
        int n = b & 0xff;
        return n;
    }

    /**
     * bytes转short，有符号
     */
    public static short byteArray2Int16(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getShort();
    }

    /**
     * bytes转Uint16，无符号
     */
    public static int byteArray2Uint16(byte[] b) {
        short s = byteArray2Int16(b);
        int i = s & 0xffff;
        return i;
    }

    /**
     * bytes转int32，有符号
     * LITTLE_ENDIAN (大小端)
     */
    public static int byteArray2Int32(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }

    /**
     * bytes转uint32，无符号
     */
    public static long byteArray2UInt32(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int i = buffer.getInt();
        long n = i & 0xffffffffl;
        return n;
    }

    /**
     * bytes转BCD编码
     */
    public static String byteArray2BCD(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte v : b) {
            sb.append(byte2BCD(v));
        }
        return sb.toString();
    }

    /**
     * bytes转BCD编码 + len
     */
    public static String byteArray2BCD(byte[] b, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(byte2BCD(b[i]));
        }
        return sb.toString();
    }

    /**
     * byte转BCD
     */
    public static String byte2BCD(byte b) {
        String t = Integer.toHexString(b & 0xff);
        if (t.length() == 1) {
            t = "0" + t;
        }
        return t.toUpperCase();
    }

    /**
     * 字符转BCD码
     */
    public static byte[] str2BCD(String str, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append("00");
        }
        sb.append(str);
        String str1 = sb.substring(sb.length() - len * 2);

        byte[] bcd = str2BCD(str1);
        return bcd;
    }

    /**
     * 字符转BCD码
     */
    public static byte[] str2BCD(String str) {
        if (str == null)
            throw new IllegalArgumentException("输入转换参数为null!");
        int strLen = str.length();
        if (strLen % 2 != 0) {
            throw new IllegalArgumentException("参数不合法,长度必须为2的倍数!");
        }
        int len = strLen / 2;
        byte[] bcd = new byte[len];
        int offset = 0;
        for (int i = 0; i < len; i++) {
            String sub = str.substring(offset, offset + 2);
            bcd[i] = (byte) (Integer.parseInt(sub, 16) & 0xff);
            offset += 2;
        }

        return bcd;
    }

    /**
     * BCD码转String
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String s = Integer.toHexString(b);
            if (s.length() > 2)
                s = s.substring(s.length() - 2);
            s = s.length() == 1 ? "0" + s : s;
            sb.append(s);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * byte[] 转String - GBK + len
     */
    public static String byteArray2GBKString(byte[] b, int len) {
        return byteArray2GBKString(Arrays.copyOfRange(b, 0, len));
    }

    /**
     * byte[] 转String - GBK
     */
    public static String byteArray2GBKString(byte[] b) {
        String s = null;
        try {
            s = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * bytes 转换成 double
     */
    public static double byteArray2Double(byte[] arr) {
        if (arr == null || arr.length != 8) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是8位!");
        }

        long l = byte8ToLong(arr);
        return Double.longBitsToDouble(l);
    }

    /**
     * double 转换成 bytes
     */
    public static byte[] double2ByteArray(double i) {
        long j = Double.doubleToLongBits(i);
        return longToByte8(j);
    }

    public static long byte8ToLong(byte[] arr) {
        if (arr == null || arr.length != 8) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是8位!");
        }
        return (long) (((long) (arr[0] & 0xff) << 56) | ((long) (arr[1] & 0xff) << 48) | ((long) (arr[2] & 0xff) << 40)
                | ((long) (arr[3] & 0xff) << 32) | ((long) (arr[4] & 0xff) << 24)
                | ((long) (arr[5] & 0xff) << 16) | ((long) (arr[6] & 0xff) << 8) | ((long) (arr[7] & 0xff)));
    }

    /**
     * 将一个long数字转换为8个byte数组组成的数组.
     */
    public static byte[] longToByte8(long sum) {
        byte[] arr = new byte[8];
        arr[0] = (byte) (sum >> 56);
        arr[1] = (byte) (sum >> 48);
        arr[2] = (byte) (sum >> 40);
        arr[3] = (byte) (sum >> 32);
        arr[4] = (byte) (sum >> 24);
        arr[5] = (byte) (sum >> 16);
        arr[6] = (byte) (sum >> 8);
        arr[7] = (byte) (sum & 0xff);
        return arr;
    }

    /**
     * bytes 转成 short
     */
    public static short byte2ToShort(byte[] arr) {
        if (arr != null && arr.length != 2) {
            throw new IllegalArgumentException("byte数组必须不为空,并且是2位!");
        }
        return (short) (((short) arr[0] << 8) | ((short) arr[1] & 0xff));
    }

    /**
     * byte 数组与 int 的相互转换
     */
    public static int byteArrayToInt(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static byte[] convertByteArray(int n) {
        byte[] buf = new byte[4];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) (n >> i & 0xff);
        }

        return buf;
    }

    /**
     * 字节 转换成 16进制的 字符串
     */
    public static String byte2HexStr(byte b) {
        String hs = "";
        String stmp = "";
        stmp = (Integer.toHexString(b & 0XFF));
        if (stmp.length() == 1)
            hs = hs + "0" + stmp;
        else
            hs = hs + stmp;
        return hs.toUpperCase();
    }

    /**
     * 字节数组 转换成 16进制的 字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }


    /**
     * hex 16 进制字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }

    /**
     * Hex16进制字符串转 byte
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

}
