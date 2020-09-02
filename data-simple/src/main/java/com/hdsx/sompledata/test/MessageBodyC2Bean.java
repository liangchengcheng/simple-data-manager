package com.hdsx.sompledata.test;


import com.hdsx.simpledata.XResolverData;
import com.hdsx.simpledata.annotation.ParamField;

public class MessageBodyC2Bean implements XResolverData {
    @ParamField(order = 0, bytes = 1)
    private int cdlx;
    @ParamField(order = 1, bytes = 1)
    private int cdzt;
    @ParamField(order = 2, bytes = 2)
    private long dydy;
    @ParamField(order = 3, bytes = 2)
    private long dcdy;
    @ParamField(order = 4, bytes = 2)
    private long cddl;
    @ParamField(order = 5, bytes = 2)
    private long gzwd;
    @ParamField(order = 6, bytes = 2)
    private long wmid;
    @ParamField(order = 7, bytes = 2)
    private long xhqd;
    @ParamField(order = 8, bytes = 4)
    private long yxsj;
    @ParamField(order = 9, bytes = 1)
    private int yjbb;
    @ParamField(order = 10, bytes = 1)
    private int rjbb;
    @ParamField(order = 11, bytes = 1)
    private int vmbb;
    @ParamField(order = 12, bytes = 1)
    private int fwyy;
    @ParamField(order = 13, bytes = 1)
    private int jzzt;



    public int getCdlx() {
        return cdlx;
    }

    public void setCdlx(int cdlx) {
        this.cdlx = cdlx;
    }

    public int getCdzt() {
        return cdzt;
    }

    public void setCdzt(int cdzt) {
        this.cdzt = cdzt;
    }

    public long getDydy() {
        return dydy;
    }

    public void setDydy(long dydy) {
        this.dydy = dydy;
    }

    public long getDcdy() {
        return dcdy;
    }

    public void setDcdy(long dcdy) {
        this.dcdy = dcdy;
    }

    public long getCddl() {
        return cddl;
    }

    public void setCddl(long cddl) {
        this.cddl = cddl;
    }

    public long getGzwd() {
        return gzwd;
    }

    public void setGzwd(long gzwd) {
        this.gzwd = gzwd;
    }

    public long getWmid() {
        return wmid;
    }

    public void setWmid(long wmid) {
        this.wmid = wmid;
    }

    public long getXhqd() {
        return xhqd;
    }

    public void setXhqd(long xhqd) {
        this.xhqd = xhqd;
    }

    public long getYxsj() {
        return yxsj;
    }

    public void setYxsj(long yxsj) {
        this.yxsj = yxsj;
    }

    public int getYjbb() {
        return yjbb;
    }

    public void setYjbb(int yjbb) {
        this.yjbb = yjbb;
    }

    public int getRjbb() {
        return rjbb;
    }

    public void setRjbb(int rjbb) {
        this.rjbb = rjbb;
    }

    public int getVmbb() {
        return vmbb;
    }

    public void setVmbb(int vmbb) {
        this.vmbb = vmbb;
    }

    public int getFwyy() {
        return fwyy;
    }

    public void setFwyy(int fwyy) {
        this.fwyy = fwyy;
    }

    public int getJzzt() {
        return jzzt;
    }

    public void setJzzt(int jzzt) {
        this.jzzt = jzzt;
    }
}
