package com.luantan.tratudienanhviet.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class FStyleTextView extends AppCompatTextView {
    public FStyleTextView(@NonNull Context context) {
        super(context);
        setFontTextView();
    }

    public FStyleTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontTextView();
    }

    public FStyleTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontTextView();
    }

    private void setFontTextView()
    {
        Typeface typeface = FStyle.getTypeface(getContext());
        setTypeface(typeface);
    }
}
