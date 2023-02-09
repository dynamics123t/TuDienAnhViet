package com.luantan.tratudienanhviet.Models;

import java.io.Serializable;

public class Word implements Serializable {
    private int maTu;
    private String tenTu;
    private String nghia;
    private int luuY;
    private int lichSu;

    public Word() {
    }

    public Word(int maTu, String tenTu, String nghia, int luuY, int lichSu) {
        this.maTu = maTu;
        this.tenTu = tenTu;
        this.nghia = nghia;
        this.luuY = luuY;
        this.lichSu = lichSu;
    }

    public int getMaTu() {
        return maTu;
    }

    public void setMaTu(int maTu) {
        this.maTu = maTu;
    }

    public String getTenTu() {
        return tenTu;
    }

    public void setTenTu(String tenTu) {
        this.tenTu = tenTu;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public int getLuuY() {
        return luuY;
    }

    public void setLuuY(int luuY) {
        this.luuY = luuY;
    }

    public int getLichSu() {
        return lichSu;
    }

    public void setLichSu(int lichSu) {
        this.lichSu = lichSu;
    }
}
