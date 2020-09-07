package com.hdsx.sompledata.test;

import com.hdsx.simpledata.XResolverData;
import com.hdsx.simpledata.annotation.ParamField;

import java.io.Serializable;

/**
 * 3.拆解
 * 7E                  --头标识
 * 02 00               --数据头->消息ID
 * 00 26               --数据头->消息体属性
 * 12 34 56 78 90 12   --数据头->终端手机号
 * 00 7E               --数据头->消息流水号
 * 00 00 00 01         --消息体->报警标志
 * 00 00 00 02         --消息体->状态位标志
 * 00 BA 7F 0E         --消息体->纬度
 * 07 E4 F1 1C         --消息体->经度
 * 00 28               --消息体->海拔高度
 * 00 3C               --消息体->速度
 * 00 00               --消息体->方向
 * 18 10 15 10 10 10   --消息体->GPS时间
 * 01                  --消息体->附加信息->里程
 * 04                  --消息体->附加信息->长度
 * 00 00 00 64         --消息体->附加信息->数据
 * 02                  --消息体->附加信息->油量
 * 02                  --消息体->附加信息->长度
 * 00 7D               --消息体->附加信息->数据
 * 13                  --检验码
 * 7E                  --尾标识
 */
public class JT808Bean implements XResolverData {
    @ParamField(order = 0, bytes = 1)
    private int headflag;
    @ParamField(order = 1, bytes = 2)
    private int xxid;
    @ParamField(order = 2, bytes = 2)
    private int xxtsx;
    @ParamField(order = 3, bytes = 6)
    private Long phone;
    @ParamField(order = 4, bytes = 2)
    private int lsh;
    @ParamField(order = 5, bytes = 4)
    private int bjbz;
    @ParamField(order = 6, bytes = 4)
    private int ztwbz;
    @ParamField(order = 7, bytes = 4)
    private Long wd;
    @ParamField(order = 8, bytes = 4)
    private Long jd;

    @ParamField(order = 9, bytes = 2)
    private int gd;
    @ParamField(order = 10, bytes = 2)
    private int sd;
    @ParamField(order = 11, bytes = 2)
    private int fx;
    @ParamField(order = 12, bytes = 6)
    private Long time;
    @ParamField(order = 13, bytes = 1)
    private int lc;
    @ParamField(order = 14, bytes = 1)
    private int cd;
    @ParamField(order = 15, bytes = 4)
    private int sj;
    @ParamField(order = 16, bytes = 1)
    private int yl;
    @ParamField(order = 17, bytes = 1)
    private int cd2;
    @ParamField(order = 18, bytes = 2)
    private int sj2;
    @ParamField(order = 19, bytes = 1)
    private int jym;
    @ParamField(order = 20, bytes = 1)
    private int wbs;

    public int getHeadflag() {
        return headflag;
    }

    public void setHeadflag(int headflag) {
        this.headflag = headflag;
    }

    public int getXxid() {
        return xxid;
    }

    public void setXxid(int xxid) {
        this.xxid = xxid;
    }

    public int getXxtsx() {
        return xxtsx;
    }

    public void setXxtsx(int xxtsx) {
        this.xxtsx = xxtsx;
    }



    public int getLsh() {
        return lsh;
    }

    public void setLsh(int lsh) {
        this.lsh = lsh;
    }

    public int getBjbz() {
        return bjbz;
    }

    public void setBjbz(int bjbz) {
        this.bjbz = bjbz;
    }

    public int getZtwbz() {
        return ztwbz;
    }

    public void setZtwbz(int ztwbz) {
        this.ztwbz = ztwbz;
    }

    public Long getWd() {
        return wd;
    }

    public void setWd(Long wd) {
        this.wd = wd;
    }

    public Long getJd() {
        return jd;
    }

    public void setJd(Long jd) {
        this.jd = jd;
    }

    public int getGd() {
        return gd;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setGd(int gd) {
        this.gd = gd;
    }

    public int getSd() {
        return sd;
    }

    public void setSd(int sd) {
        this.sd = sd;
    }

    public int getFx() {
        return fx;
    }

    public void setFx(int fx) {
        this.fx = fx;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getLc() {
        return lc;
    }

    public void setLc(int lc) {
        this.lc = lc;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }

    public int getYl() {
        return yl;
    }

    public void setYl(int yl) {
        this.yl = yl;
    }

    public int getCd2() {
        return cd2;
    }

    public void setCd2(int cd2) {
        this.cd2 = cd2;
    }

    public int getSj2() {
        return sj2;
    }

    public void setSj2(int sj2) {
        this.sj2 = sj2;
    }

    public int getJym() {
        return jym;
    }

    public void setJym(int jym) {
        this.jym = jym;
    }

    public int getWbs() {
        return wbs;
    }

    public void setWbs(int wbs) {
        this.wbs = wbs;
    }
}
