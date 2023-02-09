package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.DetailWordActivity;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.ViewHoder>{
    Context context;
    ArrayList<Word> arrayList;
    DBHelper dbHelper;
    TextView tvEmpty;
    public StarAdapter(Context context, ArrayList<Word> arrayList, TextView tvEmpty) {
        this.context = context;
        this.arrayList = arrayList;
        this.tvEmpty = tvEmpty;
        dbHelper = new DBHelper(context);

    }

    @NonNull
    @Override
    public StarAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.note_verb_item, parent, false);
        return new StarAdapter.ViewHoder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull StarAdapter.ViewHoder holder, int position) {
        Word word = arrayList.get(position);
        holder.textViewVerb.setText(word.getTenTu());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.ClearWordNote(word.getMaTu());
                arrayList.remove(arrayList.get(position));
                //Toast.makeText(context, "Da xoa khoi ds", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailWordActivity.class);
                intent.putExtra("Word", arrayList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                Toast.makeText(context, "You Click " + arrayList.get(holder.getAdapterPosition()).getTenTu(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList.size() == 0)
        {
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else
        {
            tvEmpty.setVisibility(View.GONE);
        }
        return arrayList.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder
    {
        TextView textViewVerb;
        ImageView imageView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textViewVerb = itemView.findViewById(R.id.textViewVerbNote);
            imageView = itemView.findViewById(R.id.imgButtonNote);
        }
    }
    public void release()
    {
        context = null;
    }
}
