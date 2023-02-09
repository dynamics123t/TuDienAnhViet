package com.luantan.tratudienanhviet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.luantan.tratudienanhviet.Fragments.HistoryFragment;
import com.luantan.tratudienanhviet.Fragments.HomeFragment;
import com.luantan.tratudienanhviet.Fragments.IrregularVerbsFragment;
import com.luantan.tratudienanhviet.Fragments.NoteFragment;
import com.luantan.tratudienanhviet.Fragments.TranslatorFragment;
import com.luantan.tratudienanhviet.Fragments.YoutubeFragment;
import com.luantan.tratudienanhviet.Models.Mapping;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    public static NavigationView navigationView;
    SharedPreferences sharedPreferences;
    private long backPressTime;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        load_setting();
        // khoi tao mang danh sach
//        dbHelper.SaoChepDuLieuTuAssets();
//        utils.lstTuVung = dbHelper.getALLWord();
//        utils.lstLichSu = dbHelper.getALLWordHistory();
//        utils.lstIrregular = dbHelper.getIrregulars();
       // Toast.makeText(MainActivity.this, "" +   Utils.lstIrregular.size(), Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_main));


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_drawer);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_trangchu);
        replace_fragment(new HomeFragment());
    }

    private void load_setting() {
        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        int accent = sharedPreferences.getInt("accent", 0);
        int speed = sharedPreferences.getInt("speed", 0);
        int isNotification = sharedPreferences.getInt("isNotification", 0);

        //Toast.makeText(this, "isNo:" + isNotification, Toast.LENGTH_SHORT).show();
        if(accent != 0 && speed != 0 && isNotification != 0)
        {
            Mapping.accent = accent;
            Mapping.speed = speed;
            Mapping.isNotification = isNotification;
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(backPressTime + 2000 > System.currentTimeMillis())
            {
                mToast.cancel();
                super.onBackPressed();
                super.onBackPressed();
                return;
            }
            else
            {
                mToast = Toast.makeText(MainActivity.this, "Ấn 2 lần để thoát ứng dụng", Toast.LENGTH_SHORT);
                mToast.show();
            }
            backPressTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_trangchu:
            {
                    replace_fragment(new HomeFragment());
                    navigationView.setCheckedItem(R.id.nav_trangchu);
                break;
            }
            case R.id.nav_tratudien:
            {
                    replace_fragment(new TranslatorFragment());
                    navigationView.setCheckedItem(R.id.nav_tratudien);
                break;
            }
            case R.id.nav_lichsu:
            {
                    replace_fragment(new HistoryFragment());
                    navigationView.setCheckedItem(R.id.nav_lichsu);
                break;
            }
            case R.id.nav_tuyeuthich:
            {
                    replace_fragment(new NoteFragment());
                    navigationView.setCheckedItem(R.id.nav_tuyeuthich);
                break;
            }
            case R.id.nav_bqt:
            {
                    replace_fragment(new IrregularVerbsFragment());
                    navigationView.setCheckedItem(R.id.nav_bqt);
                break;
            }
            case R.id.nav_dsvideo:
            {
                    replace_fragment(new YoutubeFragment());
                    navigationView.setCheckedItem(R.id.nav_dsvideo);
                break;
            }
            case R.id.nav_caidat:
            {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                navigationView.setCheckedItem(R.id.nav_caidat);
                break;
            }
            case R.id.nav_vechungtoi:
            {
                startActivity(new Intent(MainActivity.this, IdenActivity.class));
                navigationView.setCheckedItem(R.id.nav_vechungtoi);
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void replace_fragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search_)
        {
            startActivity(new Intent(MainActivity.this, SearchWordActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}