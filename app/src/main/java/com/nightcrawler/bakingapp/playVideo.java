package com.nightcrawler.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONException;

public class playVideo extends AppCompatActivity {
    int k;
    int currentPosition;
    recipe Recipe;
    TextView details;
    String videoURL;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    Point size;
    private boolean playWhenReady = true;
    Button prev_button, next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        java.util.Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        details = findViewById(R.id.details);
        prev_button = findViewById(R.id.prev_button);
        next_button = findViewById(R.id.prev_button);
        playerView = findViewById(R.id.video_view);

        Intent i = getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        k = args.getInt("KEY", 1);
        currentPosition = args.getInt("currentposition", 1);
        Log.v("currenPosition", "" + currentPosition);

        try {
            Recipe = Utils.returnRecipe(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        videoURL = Recipe.rsteps.get(currentPosition - 1).videoURL;

        startVideo();


        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Clicked", "PREVBUTTON");
                if (currentPosition == 1)
                    Toast.makeText(playVideo.this, "This is the first video", Toast.LENGTH_SHORT).show();
                else {
                    videoURL = "";
                    --currentPosition;
                    videoURL = Recipe.rsteps.get(currentPosition).videoURL;
                    startVideo();
                }
            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Clicked", "NEXTBUTTON");
                if ((currentPosition + 1) == Recipe.rsteps.size())
                    Toast.makeText(playVideo.this, "This is the last video", Toast.LENGTH_SHORT).show();
                else {
                    videoURL = "";
                    ++currentPosition;
                    videoURL = Recipe.rsteps.get(currentPosition).videoURL;
                    startVideo();
                }
            }
        });
    }


    public void startVideo() {
        Display getOrient = getWindowManager().getDefaultDisplay();
        size = new Point();
        getOrient.getSize(size);
        if (size.x < size.y)
            details.setText(Recipe.rsteps.get(currentPosition - 1).description);

        Log.v("videoURL", videoURL);


        if (!Utils.checkConnectivity(playVideo.this)) {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }
        else if (!URLUtil.isValidUrl(videoURL))
            Toast.makeText(this, "No video available", Toast.LENGTH_SHORT).show();
        else
            initializePlayer();



    }

    @Override
    public void onStart() {
        super.onStart();
//        if (Util.SDK_INT > 23) {
//            initializePlayer();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
//        if ((Util.SDK_INT <= 23 || player == null)) {
//            initializePlayer();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
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

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }


        MediaSource mediaSource = buildMediaSource(Uri.parse(videoURL));
//    MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_mp4)));
        player.prepare(mediaSource, true, false);
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

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {

        if (size.x > size.y) {
            //landscape mode
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
