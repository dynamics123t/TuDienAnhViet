package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.R;

import java.util.List;
// Khong su dung
public class NoteAdapter extends ArrayAdapter<Word> {

    DBHelper dbHelper;
    public NoteAdapter(@NonNull Context context, @NonNull List<Word> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        dbHelper = new DBHelper(getContext());
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_verb_item, parent, false);
        Word word = getItem(position);
        TextView textViewVerb = convertView.findViewById(R.id.textViewVerbNote);
        ImageView imageView = convertView.findViewById(R.id.imgButtonNote);
        textViewVerb.setText(word.getTenTu());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.ClearWordNote(word.getMaTu());
                Toast.makeText(getContext(), "Da xoa khoi ds", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
