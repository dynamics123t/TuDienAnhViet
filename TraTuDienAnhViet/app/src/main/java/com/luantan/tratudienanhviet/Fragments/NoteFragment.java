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
import android.widget.TextView;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.Models.StarAdapter;
import com.luantan.tratudienanhviet.Models.Word;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class NoteFragment extends Fragment {
    RecyclerView recyclerView;
    StarAdapter starAdapter;
    ArrayList<Word> arrayList;
    DBHelper dbHelper;
    TextView textViewEmpty;
    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        arrayList = new ArrayList<>();
        arrayList.clear();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tLuuY));
        // tham chieu
        textViewEmpty = view.findViewById(R.id.tvEmpty);
        recyclerView = view.findViewById(R.id.ryView_Note);
        // data
        arrayList = dbHelper.getALLWordNote();
        // init
        starAdapter = new StarAdapter(getActivity(), arrayList, textViewEmpty);
        // duong phan cach
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        //anim
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_bottom_to_top);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        // thiet lap
        recyclerView.setAdapter(starAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        // data
        arrayList = dbHelper.getALLWordNote();
        // init
        starAdapter = new StarAdapter(getActivity(), arrayList, textViewEmpty);
        // thiet lap
        recyclerView.setAdapter(starAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(starAdapter != null)
        {
            starAdapter.release();
        }
    }
}