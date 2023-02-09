package com.luantan.tratudienanhviet.Models;

public class Irregular {
    private int ma;
    private String v1, v2, v3, means;

    public Irregular() {
    }

    public Irregular(int ma, String v1, String v2, String v3, String means) {
        this.ma = ma;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.means = means;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }
}
