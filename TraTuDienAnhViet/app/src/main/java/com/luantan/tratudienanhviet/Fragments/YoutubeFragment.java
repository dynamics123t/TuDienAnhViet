package com.luantan.tratudienanhviet.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luantan.tratudienanhviet.Models.VideoAdapter;
import com.luantan.tratudienanhviet.Models.VideoYoutube;
import com.luantan.tratudienanhviet.R;
import com.luantan.tratudienanhviet.Screens.PlayVideoYoutube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YoutubeFragment extends Fragment implements AdapterView.OnItemClickListener {

    //public static String API_KEY = "AIzaSyDu8ec-HNf0_hkj5lAslENOQFu-7Ql1ARo";
    public static String API_KEY = "AIzaSyBfQl9a5qXgfHZ1psq2NXZJGfm72iJkKBw";
    String ID_PLAYLIST = "PLHin-9Zx4sE0ol4hle5tzePQiH3_BYs5K";
    String URL_JSON = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" +
            ID_PLAYLIST +"&key=" + API_KEY + "&maxResults=50";

    ListView listView;
    ArrayList<VideoYoutube> arrayList;
    VideoAdapter videoAdapter;
    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_youtube, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tVideo));
        listView = getView().findViewById(R.id.lstView_VideoYoutube);
        arrayList = new ArrayList<>();
        getJsonYoutube(URL_JSON);
        videoAdapter = new VideoAdapter(getContext(), arrayList);
        listView.setAdapter(videoAdapter);

        listView.setOnItemClickListener(this);



    }

    private void getJsonYoutube(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray("items");
                            String title = ""; String url = ""; String idvideo = "";
                            for (int i = 0; i < jsonItems.length(); i++)
                            {
                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                                title = jsonSnippet.getString("title");
                                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                                url = jsonMedium.getString("url");
                                JSONObject jsonResourceID = jsonSnippet.getJSONObject("resourceId");
                                idvideo = jsonResourceID.getString("videoId");
                                arrayList.add(new VideoYoutube(title, url, idvideo));
                            }
                            videoAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error" , Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PlayVideoYoutube.class);
        intent.putExtra("idVideo", arrayList.get(position).getId());
        startActivity(intent);
    }
}