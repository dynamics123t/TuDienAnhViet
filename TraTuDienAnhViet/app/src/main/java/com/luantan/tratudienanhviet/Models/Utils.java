package com.luantan.tratudienanhviet.Models;

import android.content.Context;

import java.util.ArrayList;

public class Utils {
    Context context;
    public Utils(Context context) {
        this.context = context;
    }


    public static  ArrayList<Word> lstLichSu = new ArrayList<>();
    public static  ArrayList<Word> lstTuVung = new ArrayList<>();
    public static  ArrayList<Irregular> lstIrregular = new ArrayList<>();
}
