package com.luantan.tratudienanhviet.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.luantan.tratudienanhviet.Fragments.YoutubeFragment;
import com.luantan.tratudienanhviet.R;

public class PlayVideoYoutube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    String id = "";
    int REQUEST_VIDEO = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_youtube);
        youTubePlayerView = findViewById(R.id.myVideo);


        Intent intent = getIntent();
        id = intent.getStringExtra("idVideo");
        youTubePlayerView.initialize(YoutubeFragment.API_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
        //youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(PlayVideoYoutube.this, REQUEST_VIDEO);
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO)
        {
            youTubePlayerView.initialize(YoutubeFragment.API_KEY, PlayVideoYoutube.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}