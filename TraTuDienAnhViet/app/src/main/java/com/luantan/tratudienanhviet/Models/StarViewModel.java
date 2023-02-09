package com.luantan.tratudienanhviet.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StarViewModel extends ViewModel {
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    public void setText(String s)
    {
        mutableLiveData.setValue(s);
    }
    public MutableLiveData<String> getText()
    {
        return mutableLiveData;
    }
}
