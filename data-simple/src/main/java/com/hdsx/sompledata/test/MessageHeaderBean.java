package com.hdsx.sompledata.test;


import com.hdsx.simpledata.XResolverData;
import com.hdsx.simpledata.annotation.ParamField;

public class MessageHeaderBean implements XResolverData {
    @ParamField(order = 0, bytes = 1)
    private int qsw;
    @ParamField(order = 1, bytes = 2)
    private long length;
    @ParamField(order = 2, bytes = 1)
    private int message;
    @ParamField(order = 3, bytes = 2)
    private long mbtxid;
    @ParamField(order = 4, bytes = 1)
    private int mbxd;
    @ParamField(order = 5, bytes = 2)
    private long sbtxid;
    @ParamField(order = 6, bytes = 1)
    private int year;
    @ParamField(order = 7, bytes = 1)
    private int month;
    @ParamField(order = 8, bytes = 1)
    private int day;
    @ParamField(order = 9, bytes = 1)
    private int hour;
    @ParamField(order = 10, bytes = 1)
    private int minute;
    @ParamField(order = 11, bytes = 1)
    private int second;

    public long getSbtxid() {
        return sbtxid;
    }

    public void setSbtxid(long sbtxid) {
        this.sbtxid = sbtxid;
    }

    public int getQsw() {
        return qsw;
    }

    public void setQsw(int qsw) {
        this.qsw = qsw;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public long getMbtxid() {
        return mbtxid;
    }

    public void setMbtxid(long mbtxid) {
        this.mbtxid = mbtxid;
    }

    public int getMbxd() {
        return mbxd;
    }

    public void setMbxd(int mbxd) {
        this.mbxd = mbxd;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
