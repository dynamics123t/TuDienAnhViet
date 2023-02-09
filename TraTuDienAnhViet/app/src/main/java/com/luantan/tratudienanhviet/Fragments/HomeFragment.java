package com.luantan.tratudienanhviet.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.luantan.tratudienanhviet.MainActivity;
import com.luantan.tratudienanhviet.R;

public class HomeFragment extends Fragment{

    RelativeLayout rly_dichvb, rly_tuluuy, rly_lichsu, rly_bqt, rly_video;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tTC));
        rly_dichvb = getView().findViewById(R.id.layout_dichvb);
        rly_dichvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_tratudien);
//                MainActivity.CURRENT_FRAGMENT = 2;
                replace_fragment(new TranslatorFragment());
            }
        });
        rly_tuluuy = getView().findViewById(R.id.layout_tuluuy);
        rly_tuluuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_tuyeuthich);
//                MainActivity.CURRENT_FRAGMENT = 3;
                replace_fragment(new NoteFragment());
            }
        });

        rly_lichsu = getView().findViewById(R.id.layout_lichsu);
        rly_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_lichsu);
//                MainActivity.CURRENT_FRAGMENT = 4;
                replace_fragment(new HistoryFragment());
            }
        });

        rly_bqt = getView().findViewById(R.id.layout_bqt);
        rly_bqt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_bqt);
//                MainActivity.CURRENT_FRAGMENT = 5;
                replace_fragment(new IrregularVerbsFragment());
            }
        });

        rly_video = getView().findViewById(R.id.layout_video);
        rly_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigationView.setCheckedItem(R.id.nav_dsvideo);
//                MainActivity.CURRENT_FRAGMENT = 6;
                replace_fragment(new YoutubeFragment());
            }
        });

    }
    private void replace_fragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}