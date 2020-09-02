package com.hdsx.sompledata.test;


import com.hdsx.simpledata.XResolverData;
import com.hdsx.simpledata.annotation.ParamField;

public class MessageBodyC1Bean implements XResolverData {
    @ParamField(order = 0, bytes = 2)
    private long lmwd;
    @ParamField(order = 1, bytes = 2)
    private long kqwd;
    @ParamField(order = 2, bytes = 1)
    private int kqsd;
    @ParamField(order = 3, bytes = 2)
    private long gzd;
    @ParamField(order = 4, bytes = 1)
    private int jblx;
    @ParamField(order = 5, bytes = 4)
    private int jbhd;
    @ParamField(order = 6, bytes = 2)
    private long njd;
    @ParamField(order = 7, bytes = 2)
    private int pm10;
    @ParamField(order = 8, bytes = 2)
    private int pm25;
    @ParamField(order = 9, bytes = 2)
    private int pm100;
    @ParamField(order = 10, bytes = 2)
    private int um03;
    @ParamField(order = 11, bytes = 2)
    private int um05;
    @ParamField(order = 12, bytes = 2)
    private int um10;
    @ParamField(order = 13, bytes = 2)
    private int um25;
    @ParamField(order = 14, bytes = 2)
    private int umdqy;

    public long getLmwd() {
        return lmwd;
    }

    public void setLmwd(long lmwd) {
        this.lmwd = lmwd;
    }

    public long getKqwd() {
        return kqwd;
    }

    public void setKqwd(long kqwd) {
        this.kqwd = kqwd;
    }

    public int getKqsd() {
        return kqsd;
    }

    public void setKqsd(int kqsd) {
        this.kqsd = kqsd;
    }

    public long getGzd() {
        return gzd;
    }

    public void setGzd(long gzd) {
        this.gzd = gzd;
    }

    public int getJblx() {
        return jblx;
    }

    public void setJblx(int jblx) {
        this.jblx = jblx;
    }

    public int getJbhd() {
        return jbhd;
    }

    public void setJbhd(int jbhd) {
        this.jbhd = jbhd;
    }

    public long getNjd() {
        return njd;
    }

    public void setNjd(long njd) {
        this.njd = njd;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getPm100() {
        return pm100;
    }

    public void setPm100(int pm100) {
        this.pm100 = pm100;
    }

    public int getUm03() {
        return um03;
    }

    public void setUm03(int um03) {
        this.um03 = um03;
    }

    public int getUm05() {
        return um05;
    }

    public void setUm05(int um05) {
        this.um05 = um05;
    }

    public int getUm10() {
        return um10;
    }

    public void setUm10(int um10) {
        this.um10 = um10;
    }

    public int getUm25() {
        return um25;
    }

    public void setUm25(int um25) {
        this.um25 = um25;
    }

    public int getUmdqy() {
        return umdqy;
    }

    public void setUmdqy(int umdqy) {
        this.umdqy = umdqy;
    }
}
