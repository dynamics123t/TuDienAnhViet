package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextToSpeedClass {
    Context context;
    TextToSpeech textToSpeech;

    public TextToSpeedClass(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int lang = textToSpeech.setLanguage(Locale.US); // gender
                    if(Mapping.accent == 2)
                    {
                       lang = textToSpeech.setLanguage(Locale.UK); // gender
                    }
                    textToSpeech.setSpeechRate(Mapping.speed); // speed
                }
            }
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }
}
