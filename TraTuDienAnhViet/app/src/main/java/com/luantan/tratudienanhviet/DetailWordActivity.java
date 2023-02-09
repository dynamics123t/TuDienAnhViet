package com.luantan.tratudienanhviet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.luantan.tratudienanhviet.Models.Mapping;
import com.luantan.tratudienanhviet.Models.TextToSpeedClass;
import com.luantan.tratudienanhviet.Models.Word;

import java.util.Locale;

public class DetailWordActivity extends AppCompatActivity {
    TextView textViewTen, textViewNghia;
    ImageButton buttonSpeak, buttonNote;
    TextToSpeedClass textToSpeedClass;
    DBHelper dbHelper;
    boolean isCheck;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_word);
        setUpLanguage();
        getSupportActionBar().setTitle(getResources().getString(R.string.tChiTiet));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(getApplication());

        textToSpeedClass = new TextToSpeedClass(getApplication());
        textViewTen = findViewById(R.id.tvTenTu);
        textViewNghia = findViewById(R.id.tvNghiaTu);
        buttonSpeak = findViewById(R.id.buttonSpeak);
        buttonNote = findViewById(R.id.buttonNote);
        textViewNghia.setMovementMethod(new ScrollingMovementMethod());
        Word word = (Word) getIntent().getSerializableExtra("Word");
        textViewTen.setText(word.getTenTu());
        textViewNghia.setText(replaceString(word.getNghia()));

        // neu tim thay => true
        isCheck = dbHelper.timTuLuuYTheoMa(word.getMaTu()); // true or false
        if(isCheck)
        {
            buttonNote.setImageResource(R.drawable.star_yellow);
        }
        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speech = textToSpeedClass.getTextToSpeech().speak(textViewTen.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        buttonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu tồn tại: xóa else
                if(isCheck)
                {
                    dbHelper.ClearWordNote(word.getMaTu());
                    buttonNote.setImageResource(R.drawable.star_white);
                    isCheck = false;
                    Toast.makeText(DetailWordActivity.this, "Đã xóa khỏi danh sách lưu ý", Toast.LENGTH_SHORT).show();
                }
               else
                {
                    dbHelper.UpdateNote_Click(word.getMaTu());
                    buttonNote.setImageResource(R.drawable.star_yellow);
                    isCheck = true;
                    Toast.makeText(DetailWordActivity.this, "Đã thêm vào danh sách lưu ý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(DetailWordActivity.this, SearchWordActivity.class));
        finish();
        return super.onSupportNavigateUp();
    }

    public String replaceString(String str)
    {
        String strPr = str.replace("@", "Cách viết: ")
                .replace("*  thán từ", "\n\nThán từ")
                .replace("*  nội động từ", "\n\nNội động từ")
                .replace("*  tính từ", "\n\nTính từ")
                .replace("* tính từ", "\n\nTính từ")
                .replace("*  danh từ", "\n\nDanh từ")
                .replace("* danh từ", "\n\nDanh từ")
                .replace("*  ngoại động từ", "\n\nNgoại động từ")
                .replaceAll("- ", "\n\t- ");
        return strPr;
    }
    void setUpLanguage()
    {
        // setting language
        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "VN");
        if(language.equals("VN"))
        {
            setLocale(DetailWordActivity.this, "vi");
            Mapping.language = "VN";
        }
        else
        {
            setLocale(DetailWordActivity.this, "en");
            Mapping.language = "EN";
        }
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