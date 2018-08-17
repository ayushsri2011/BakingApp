package com.nightcrawler.bakingapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoFragment extends Fragment {
    private String videoURL;

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;
    private Bundle savedInstanceState2;

    public VideoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        savedInstanceState2=savedInstanceState;
        final View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        playerView = rootView.findViewById(R.id.video_view);
        Bundle bundle = getArguments();
        if (bundle != null)
            videoURL = bundle.getString("videoURL");

        startVideo();

        return rootView;

    }


    private void startVideo() {


        Log.v("videoURL", videoURL);

        if (URLUtil.isValidUrl(videoURL))
            initializePlayer();
        else
            Toast.makeText(getContext(), "No video available", Toast.LENGTH_SHORT).show();


    }





    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        else
        {

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }

        if (savedInstanceState2 != null) {
            playbackPosition = savedInstanceState2.getLong("playbackPosition",0);
            currentWindow = savedInstanceState2.getInt("currentWindow",0);
            playWhenReady = savedInstanceState2.getBoolean("playWhenReady",true);
            Log.v("RestoreInstance","data retrieved");
            Log.v("playbackPosition--",""+playbackPosition);
            Log.v("currentWindow--",""+currentWindow);
            Log.v("playWhenReady--",""+playWhenReady);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }


        MediaSource mediaSource = buildMediaSource(Uri.parse(videoURL));
        player.prepare(mediaSource, false, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        int currentState=player.getPlaybackState();
        Log.v("Ishi in vidFrag"," currentState--"+currentState);

        playbackPosition = player.getCurrentPosition();
        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();
        Log.v("Ishi playbackPosition--",""+playbackPosition);
        Log.v("Ishi currentWindow--",""+currentWindow);
        Log.v("Ishi playWhenReady--",""+playWhenReady);

        //player.setPlayWhenReady();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }




    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Ishi SaveInstance","data saved");
        playbackPosition = player.getCurrentPosition();
        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();
        outState.putLong("playbackPosition",playbackPosition);
        outState.putInt("currentWindow",currentWindow);
        outState.putBoolean("playWhenReady",playWhenReady);
        Log.v("onSaveInstanceState","");
        Log.v("playbackPosition--",""+playbackPosition);
        Log.v("currentWindow--",""+currentWindow);
        Log.v("playWhenReady--",""+playWhenReady);
    }


}
