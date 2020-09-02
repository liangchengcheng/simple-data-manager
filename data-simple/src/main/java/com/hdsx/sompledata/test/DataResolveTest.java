package com.hdsx.sompledata.test;

import com.hdsx.simpledata.XResolver;
import com.hdsx.simpledata.utils.DataTypeUtil;

import java.nio.ByteOrder;

public class DataResolveTest {
    private static byte charToByte(char c) {
        return (byte) "FE2800C2422E01254114080D11063A".indexOf(c);
    }

    /**
     * hex字符串转byte数组
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
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

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

    public static void main(String[] args) {
        header();
        c2();
        c1();
        c3();
        c11();
    }

    public static void header() {
        byte[] fe2800C2s = hexToByteArray("FE2800C2422E01254114080D11063A");
        String s = byte2HexStr(fe2800C2s);
        String s2 = DataTypeUtil.byte2HexStr(fe2800C2s[3]);
        System.out.println("1");
        XResolver deserializeXResolver = new XResolver(ByteOrder.BIG_ENDIAN);

        MessageHeaderBean foo = null;
        foo = deserializeXResolver.fromBytes(fe2800C2s, MessageHeaderBean.class);

        System.out.println("Taira deserialize result: " + String.valueOf(foo));

    }

    public static void c3() {
        byte[] fe2800C2s = hexToByteArray("2F042F04");
        int i = DataTypeUtil.byteArray2Int32(fe2800C2s);
        long i1 = DataTypeUtil.byteArray2UInt32(fe2800C2s);
        System.out.println(" deserialize result: " + String.valueOf("1"));
    }

    public static void c2() {
        byte[] fe2800C2s = hexToByteArray("2000000722000001C0C264500000E710D00010A020101");
        String s = byte2HexStr(fe2800C2s);
        System.out.println("s1: " + String.valueOf(s));
        System.out.println("1");
        XResolver deserializeXResolver = new XResolver(ByteOrder.LITTLE_ENDIAN);

        MessageBodyC2Bean foo = null;
        foo = deserializeXResolver.fromBytes(fe2800C2s, MessageBodyC2Bean.class);

        System.out.println(" deserialize result: " + String.valueOf(foo));

        byte[] fooBytes = new byte[0];
        fooBytes = deserializeXResolver.toBytes(foo);
        String s2 = byte2HexStr(fooBytes);
        System.out.println("s2: " + String.valueOf(s2));
    }

    public static void c1() {
        byte[] fe2800C2s = hexToByteArray("2F044A064505000114000000881305000A000B00D50331014C00070000000000");
        String s = byte2HexStr(fe2800C2s);
        System.out.println("1");
        XResolver deserializeXResolver = new XResolver(ByteOrder.LITTLE_ENDIAN);

        MessageBodyC1Bean foo = null;
        foo = deserializeXResolver.fromBytes(fe2800C2s, MessageBodyC1Bean.class);

        System.out.println("Taira deserialize result: " + String.valueOf(foo));

    }

    public static void c11() {
        byte[] fe2800C2s = hexToByteArray("FE3100C15030012645140814110419290444072C0E0000000000001027030007000700CA02C800360002000000000002BE");
        String s = byte2HexStr(fe2800C2s);
        System.out.println("1");
        XResolver deserializeXResolver = new XResolver(ByteOrder.LITTLE_ENDIAN);

        C1BeanInfo foo = null;
        foo = deserializeXResolver.fromBytes(fe2800C2s, C1BeanInfo.class);

        System.out.println("deserialize result: " + String.valueOf(foo));
    }
}
