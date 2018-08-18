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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class playVideo extends AppCompatActivity {
    int k;    int currentPosition;    recipe Recipe;
    TextView details;    String videoURL;

    private SimpleExoPlayer player;
//    private PlayerView playerView;
//    Button prev_button, next_button;
    private long playbackPosition = 0;
    private int currentWindow = 0;    Point size;    private boolean playWhenReady = true;

    @BindView(R.id.video_view) PlayerView playerView;
    @BindView(R.id.prev_button) Button prev_button;
    @BindView(R.id.next_button) Button next_button;
    @BindView(R.id.thumbnail) ImageView img;
    Bundle savedInstanceState2;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedInstanceState2=savedInstanceState;

        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        java.util.Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        details = findViewById(R.id.details);
//        prev_button = findViewById(R.id.prev_button);
//        next_button = findViewById(R.id.next_button);
//        playerView = findViewById(R.id.video_view);

        Intent i = getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        k = args.getInt("KEY", 1);
        currentPosition = args.getInt("currentposition", 1);
        Log.v("currenPosition", "" + currentPosition);

        try {
            String resp=Utils.prefResponse(this);
            Recipe = Utils.returnRecipe(k,resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        videoURL = Recipe.rsteps.get(currentPosition - 1).videoURL;

        startVideo();

//      set thumbnail
        String url=Recipe.rsteps.get(currentPosition-1).thumbnailURL;
        if(!url.equals("") && !(" ".equals(url)))
        Picasso.get().load(url).placeholder(R.drawable.tea).into(img);
        else
            img.setImageResource(R.drawable.tea);




        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Clicked", "PREVBUTTON");
                if (currentPosition == 1)
                    Toast.makeText(playVideo.this, "This is the first video", Toast.LENGTH_SHORT).show();
                else {
                    releasePlayer();
                    videoURL = "";
                    currentPosition = currentPosition - 1;
                    videoURL = Recipe.rsteps.get(currentPosition).videoURL;
                    startVideo();
                    //reset thumbnail
                    String url=Recipe.rsteps.get(currentPosition).thumbnailURL;
                    if(!url.equals("") && !(" ".equals(url)))
                        Picasso.get().load(url).placeholder(R.drawable.tea).into(img);
                    else
                        img.setImageResource(R.drawable.tea);
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
                    releasePlayer();
                    videoURL = "";
                    currentPosition = currentPosition + 1;
                    videoURL = Recipe.rsteps.get(currentPosition).videoURL;
                    startVideo();
                    //reset thumbnail
                    String url=Recipe.rsteps.get(currentPosition).thumbnailURL;
                    if(!url.equals("") && !(" ".equals(url)))
                        Picasso.get().load(url).placeholder(R.drawable.tea).into(img);
                    else
                        img.setImageResource(R.drawable.tea);
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
        } else if (!URLUtil.isValidUrl(videoURL))
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
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
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
            Log.v("initializePlayer()--","player is Null");
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        else
        {
            Log.v("initializePlayer()--","player not Null");
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
