package com.luantan.tratudienanhviet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IdenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iden);
        getSupportActionBar().setTitle(getResources().getString(R.string.info));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}