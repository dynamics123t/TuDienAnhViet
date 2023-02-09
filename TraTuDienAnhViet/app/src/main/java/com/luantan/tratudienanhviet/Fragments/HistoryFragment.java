package com.luantan.tratudienanhviet.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.Models.HistoryAdapter;
import com.luantan.tratudienanhviet.Models.Word;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    TextView textViewEmpty;
    HistoryAdapter historyAdapter;
    ArrayList<Word> arrayList;
    DBHelper dbHelper;
    Button button;
    public HistoryFragment() {
        // Required empty public constructor
    }
    // khoi tao
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        arrayList = new ArrayList<>();
        arrayList.clear();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    // anh xa, code
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tLS));

        recyclerView = view.findViewById(R.id.rcyView_History);
        textViewEmpty = view.findViewById(R.id.tvEmpty);
        arrayList = dbHelper.getALLWordHistory();
        //Toast.makeText(getContext(), "" + arrayList.size(), Toast.LENGTH_SHORT).show();
        historyAdapter = new HistoryAdapter(getActivity(), arrayList, textViewEmpty);
        // Duong phan cach theo chieu doc
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "resum", Toast.LENGTH_SHORT).show();

        // Cap nhat lai
        arrayList = dbHelper.getALLWordHistory();
        historyAdapter = new HistoryAdapter(getActivity(), arrayList, textViewEmpty);

        //anim
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_bottom_to_top);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(historyAdapter != null)
        {
            historyAdapter.release();
        }
    }
}