package com.luantan.tratudienanhviet.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.Models.Irregular;
import com.luantan.tratudienanhviet.Models.IrregularAdapter;
import com.luantan.tratudienanhviet.Models.Utils;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class IrregularVerbsFragment extends Fragment {

    DBHelper dbHelper;
    Utils utils;
    RecyclerView recyclerView;
    IrregularAdapter adapter;
    ArrayList<Irregular> arrayList;
    public IrregularVerbsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        utils = new Utils(getContext());
        arrayList = new ArrayList<>();
        arrayList.clear();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_irregular_verbs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tBQT));
        recyclerView = view.findViewById(R.id.ryView_Irr);
        arrayList = dbHelper.getIrregulars();
        adapter = new IrregularAdapter(getActivity(), arrayList);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_bottom_to_top);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_bottom_to_top);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter != null)
        {
            adapter.release();
        }
    }
}