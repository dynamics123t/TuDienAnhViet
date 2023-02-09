package com.luantan.tratudienanhviet.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.luantan.tratudienanhviet.MainActivity;
import com.luantan.tratudienanhviet.Models.OnBoardingAdapter;
import com.luantan.tratudienanhviet.Models.OnBoardingItem;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnbroardingIndicator;
    private MaterialButton buttonOnboarding;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        setupOnboardingItem();

        layoutOnbroardingIndicator = findViewById(R.id.layoutOnboardingIndicators);
        ViewPager2 onboardingViewpager = findViewById(R.id.onboardingViewpager);
        buttonOnboarding = findViewById(R.id.buttonOnboaringAction);

        onboardingViewpager.setAdapter(onBoardingAdapter);
        setupOnboardingIndicator();
        setCurrentOnboarding(0);

        onboardingViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboarding(position);
            }
        });

        buttonOnboarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewpager.getCurrentItem() + 1 < onBoardingAdapter.getItemCount())
                {
                    onboardingViewpager.setCurrentItem(onboardingViewpager.getCurrentItem() + 1);
                }
                else
                {
                    //share
                    sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor edCatches = sharedPreferences.edit();

                    edCatches.putString("check", "check");
                    edCatches.commit();

                    startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItem() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();
        OnBoardingItem itemPayonine = new OnBoardingItem();
        itemPayonine.setTitle(getResources().getString(R.string.on1));
        itemPayonine.setDescription("");
        itemPayonine.setImage(R.drawable.book512);

        OnBoardingItem itemPayonine1 = new OnBoardingItem();
        itemPayonine1.setTitle(getResources().getString(R.string.on2));
        itemPayonine1.setDescription("");
        itemPayonine1.setImage(R.drawable.sale512);

        OnBoardingItem itemPayonine2 = new OnBoardingItem();
        itemPayonine2.setTitle(getResources().getString(R.string.on3));
        itemPayonine2.setDescription("");
        itemPayonine2.setImage(R.drawable.play512);

        OnBoardingItem itemPayonine3 = new OnBoardingItem();
        itemPayonine3.setTitle(getResources().getString(R.string.on4));
        itemPayonine3.setDescription("");
        itemPayonine3.setImage(R.drawable.search512);

        onBoardingItems.add(itemPayonine);
        onBoardingItems.add(itemPayonine1);
        onBoardingItems.add(itemPayonine2);
        onBoardingItems.add(itemPayonine3);
        //Toast.makeText(this, "sl" + onBoardingItems.size(), Toast.LENGTH_SHORT).show();
        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }
    private void setupOnboardingIndicator()
    {
        ImageView[] imageViews = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < imageViews.length; i++)
        {
            imageViews[i] = new ImageView(getApplicationContext());
            imageViews[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            imageViews[i].setLayoutParams(layoutParams);
            layoutOnbroardingIndicator.addView(imageViews[i]);
        }
    }

    private  void setCurrentOnboarding(int index)
    {
        int childCount = layoutOnbroardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            ImageView imageView = (ImageView) layoutOnbroardingIndicator.getChildAt(i);
            if(i == index)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            }else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
        if(index == onBoardingAdapter.getItemCount() - 1)
        {
            buttonOnboarding.setText(getResources().getString(R.string.btnBatDau));
        }
        else
        {
            buttonOnboarding.setText(getResources().getString(R.string.btnTiepTuc));
        }
    }
    }