package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.luantan.tratudienanhviet.DBHelper;
import com.luantan.tratudienanhviet.DetailWordActivity;
import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHoder> {

    Context context;
    ArrayList<Word> arrayList;
    TextView tvEmpty;
    HistoryViewModel historyViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<Word> arrSelected = new ArrayList<>();
    DBHelper dbHelper;

    public HistoryAdapter(Context context, ArrayList<Word> arrayList, TextView tvEmpty) {
        this.context = context;
        this.arrayList = arrayList;
        this.tvEmpty = tvEmpty;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_word_history, parent, false);
        historyViewModel = ViewModelProviders.of((FragmentActivity) context)
                .get(HistoryViewModel.class);
        return new ViewHoder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.textView.setText(arrayList.get(position).getTenTu());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isEnable)
                {
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            ((AppCompatActivity)context).getSupportActionBar().hide();
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate(R.menu.menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            isEnable = true;
                            ClickItem(holder);
                            historyViewModel.getText().observe((LifecycleOwner) context, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    mode.setTitle(String.format("%s Selected", s));
                                }
                            });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            int id = item.getItemId();
                            switch (id)
                            {
                                case R.id.menu_delete:
                                    for (Word w : arrSelected)
                                    {
                                        arrayList.remove(w);
                                        dbHelper.ClearWordHistory(w.getMaTu());
                                    }
                                    if (arrayList.size() == 0)
                                    {
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    mode.finish();
                                    break;
                                case  R.id.menu_select_multi:
                                    if(arrSelected.size() == arrayList.size())
                                    {
                                        isSelectAll = false;
                                        arrSelected.clear();
                                    }
                                    else
                                    {
                                        isSelectAll = true;
                                        arrSelected.clear();
                                        arrSelected.addAll(arrayList);
                                    }
                                    historyViewModel.setText(String.valueOf(arrSelected.size()));
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            ((AppCompatActivity)context).getSupportActionBar().show();
                            isEnable = isSelectAll = false;
                            arrSelected.clear();
                            notifyDataSetChanged();

                        }
                    };
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }
                else
                {
                    ClickItem(holder);
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnable)
                    ClickItem(holder);
                else
                {
                    Intent intent = new Intent(context, DetailWordActivity.class);
                    intent.putExtra("Word", arrayList.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                    //Toast.makeText(context, "You Click " + arrayList.get(holder.getAdapterPosition()).getTenTu(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(isSelectAll)
        {
            holder.ivCheckbox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else
        {
            holder.ivCheckbox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    // Khi click : neu chua chon thi -> chon, them vao list
    //           neu da chon -> huy chon, xoa khoi list
    private void ClickItem(ViewHoder holder) {
        Word word = arrayList.get(holder.getAdapterPosition());
        if(holder.ivCheckbox.getVisibility() == View.GONE)
        {
            holder.ivCheckbox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            arrSelected.add(word);
        }else
        {
            holder.ivCheckbox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            arrSelected.remove(word);
        }
        historyViewModel.setText(String.valueOf(arrSelected.size()));
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
        TextView textView;
        ImageView ivCheckbox;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            ivCheckbox = itemView.findViewById(R.id.ivCheckbox);
        }
    }
    public void release()
    {
        context = null;
    }

}
