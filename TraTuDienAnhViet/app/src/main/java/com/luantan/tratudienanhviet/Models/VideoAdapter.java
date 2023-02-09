package com.luantan.tratudienanhviet.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luantan.tratudienanhviet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<VideoYoutube> {

    public VideoAdapter(@NonNull Context context, @NonNull List<VideoYoutube> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_youtube, parent, false);
        VideoYoutube videoYoutube = getItem(position);
        TextView textView = convertView.findViewById(R.id.textView_Title);
        ImageView imageView = convertView.findViewById(R.id.imgView_Thumbnail);

        textView.setText(videoYoutube.getTitle());
        Picasso.with(getContext()).load(videoYoutube.getThumbnail()).into(imageView);
        return  convertView;
    }
}
