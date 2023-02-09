package com.luantan.tratudienanhviet.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.MainActivity;
import com.luantan.tratudienanhviet.Models.Mapping;
import com.luantan.tratudienanhviet.Models.Utils;
import com.luantan.tratudienanhviet.R;

import java.util.Locale;

public class IntroduceActivity extends AppCompatActivity {
    ImageView logo, sPlash;
    LottieAnimationView lottieAnimationView;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        DBHelper dbHelper = new DBHelper(this);
        Utils utils = new Utils(this);

//        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appname);
        sPlash = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);


        //intro sach roi
        sPlash.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
//        logo.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        // Luồng load dữ liệu
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                dbHelper.SaoChepDuLieuTuAssets();
                utils.lstTuVung = dbHelper.getALLWord();
                utils.lstLichSu = dbHelper.getALLWordHistory();
                utils.lstIrregular = dbHelper.getIrregulars();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String chk = sharedPreferences.getString("check", "");
                sharedPreferences2 = getSharedPreferences("setting", MODE_PRIVATE);
                String language = sharedPreferences2.getString("language", "VN");
                //Toast.makeText(IntroduceActivity.this, ""+ language, Toast.LENGTH_SHORT).show();
                if(language.equals("VN"))
                {
                    setLocale(IntroduceActivity.this, "vi");
                    Mapping.language = "VN";
                }
                else
                {
                    setLocale(IntroduceActivity.this, "en");
                    Mapping.language = "EN";
                }
                finish();
                if(!chk.isEmpty() )
                {
                    startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                }
                else
                {
                    startActivity(new Intent(IntroduceActivity.this, OnBoardingActivity.class));
                    finish();
                }
            }
        }, 5000);
    }
    public void setLocale(Activity activity, String lg)
    {
        Locale locale = new Locale(lg);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}