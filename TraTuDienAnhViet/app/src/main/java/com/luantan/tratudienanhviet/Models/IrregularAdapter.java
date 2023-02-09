package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luantan.tratudienanhviet.R;

import java.util.ArrayList;

public class IrregularAdapter extends RecyclerView.Adapter<IrregularAdapter.ViewHoder>{
    Context context;
    ArrayList<Irregular> arrayList;
    TextToSpeedClass textToSpeedClass;

    public IrregularAdapter(Context context, ArrayList<Irregular> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.irregular_verb_item, parent, false);
        return new ViewHoder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        textToSpeedClass = new TextToSpeedClass(context);
        Irregular irregular = arrayList.get(position);
        holder.textViewVerb.setText(irregular.getV1() + " - " + irregular.getV2() + " - " + irregular.getV3());
        holder.textViewMeans.setText(irregular.getMeans());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "" + irregular.getV1(), Toast.LENGTH_SHORT).show();
                String s = irregular.getV1() + "," + irregular.getV2() + "," + irregular.getV3();
                int speech = textToSpeedClass.textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder
    {
        TextView textViewVerb;
        TextView textViewMeans;
        ImageView imageView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textViewVerb = itemView.findViewById(R.id.textViewVerb);
            textViewMeans = itemView.findViewById(R.id.textViewMean);
            imageView = itemView.findViewById(R.id.imgButtonSpeak);
        }
    }
    public void release()
    {
        context = null;
    }
}
