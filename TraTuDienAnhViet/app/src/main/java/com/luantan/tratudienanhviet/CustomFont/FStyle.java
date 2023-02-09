package com.luantan.tratudienanhviet.CustomFont;

import android.content.Context;
import android.graphics.Typeface;

public class FStyle {
    private  static Typeface typeface;

    public static Typeface getTypeface(Context context) {
        if(typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/CormorantGaramond-BoldItalic.ttf");
        return typeface;
    }
}
