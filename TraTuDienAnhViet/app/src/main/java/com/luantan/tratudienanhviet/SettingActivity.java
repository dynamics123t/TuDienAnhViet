package com.luantan.tratudienanhviet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.luantan.tratudienanhviet.Models.EventHandler;
import com.luantan.tratudienanhviet.Models.Mapping;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    RadioButton rdAnh, rdMy;
    SeekBar seekBarSpeed;
    TextView textView;
    SwitchMaterial aSwitch;
    Spinner spinner;
    SharedPreferences sharedPreferences;
    public  static final  String[] lg = {"Language", "VN", "EN"};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        load_setting();
        getSupportActionBar().setTitle(getResources().getString(R.string.tCaiDat));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rdAnh = findViewById(R.id.rdAnh);
        rdMy = findViewById(R.id.rdMy);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        textView = findViewById(R.id.tvX);
        aSwitch = findViewById(R.id.swThongBao);
        spinner = findViewById(R.id.spin);
        // khoi tao
        init();

        // event
        rdAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdAnh.setChecked(true);
                Mapping.accent = 1;
            }
        });
        rdMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMy.setChecked(true);
                Mapping.accent = 2;
            }
        });
        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Mapping.speed = seekBarSpeed.getProgress();
                textView.setText("x" + seekBarSpeed.getProgress());
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    // notification
                    Mapping.isNotification = 1;
                    EventHandler.periodicWorkRequest();
                }
                else
                {
                    Mapping.isNotification = 0;
                    EventHandler.CancelWork();
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("VN"))
                {
                    setLocale(SettingActivity.this, "vi");
                    Mapping.language = "VN";
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                }
                else if(selected.equals("EN")) {
                    setLocale(SettingActivity.this, "en");
                    Mapping.language = "EN";
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    public void init()
    {
        if(Mapping.accent == 1)
        {
            rdAnh.setChecked(true);
        }
        else
        {
            rdMy.setChecked(true);
        }
        seekBarSpeed.setProgress(Mapping.speed);
        textView.setText("x" + Mapping.speed);
        if(Mapping.isNotification == 1)
            aSwitch.setChecked(true);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private void load_setting() {
        int accent = sharedPreferences.getInt("accent", 1);
        int speed = sharedPreferences.getInt("speed", 1);
        int isNotification = sharedPreferences.getInt("isNotification", 0);

        //Toast.makeText(this, "isNo:" + isNotification, Toast.LENGTH_SHORT).show();
        Mapping.accent = accent;
        Mapping.speed = speed;
        Mapping.isNotification = isNotification;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor edCatches = sharedPreferences.edit();
        edCatches.putInt("accent", Mapping.accent);
        edCatches.putInt("speed", Mapping.speed);
        edCatches.putInt("isNotification", Mapping.isNotification);
        edCatches.putString("language", Mapping.language);
        edCatches.apply();
    }

}