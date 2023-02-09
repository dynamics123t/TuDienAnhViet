package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luantan.tratudienanhviet.R;

import java.util.List;

public class ItemSearchAdapter extends ArrayAdapter<Word> {

    public ItemSearchAdapter(@NonNull Context context, @NonNull List<Word> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_serach, parent, false);
        Word word = getItem(position);
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(word.getTenTu());
        return convertView;
    }
}
