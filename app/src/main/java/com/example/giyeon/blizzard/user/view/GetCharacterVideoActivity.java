package com.example.giyeon.blizzard.user.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.dto.Explanation;
import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class GetCharacterVideoActivity extends AppCompatActivity {


    String videoUrl[] ={"/hatchVideo/overWatchEggHatch.mp4","/hatchVideo/starCraftEggHatch.mp4","/hatchVideo/diabloEggHatch.mp4"};
    int videoNum;
    String monsterName;
    String monsterUrl;
    Context context;

    ExoPlayer player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_character_video);

        context = this;
        String kind = getIntent().getStringExtra("kind");
        monsterName = getIntent().getStringExtra("monsterName");
        monsterUrl = getIntent().getStringExtra("monsterUrl");
        switch (kind) {
            case "overWatch": videoNum = 0; break;
            case "starCraft": videoNum = 1; break;
            case "diablo": videoNum = 2; break;
        }



        CommonController.backgroundSound.stop();
        //titleText.setText(explanation.getTitle());
        //contentText.setText(explanation.getContents());

        init_Vedio();
    }

    private void init_Vedio() {

        PlayerView playerView = (PlayerView)findViewById(R.id.getCharacter_exoPlayer);


        player = ExoPlayerFactory.newSimpleInstance(this);

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e("error", "Error Reason : "+error );
            }

            @Override
            public void onPositionDiscontinuity(int reason) { // reason :0 = change window / 1 = 15sec preview or before reason / 2 = change Button window

            }
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch(playbackState) {
                    case Player.STATE_BUFFERING:
                        Log.e("state!!!","BUFFERING");
                        break;
                    case Player.STATE_ENDED:

                        Intent intent1 = new Intent(context, GetCharacterActivity.class);
                        intent1.putExtra("monsterName",monsterName);
                        intent1.putExtra("monsterUrl",monsterUrl);
                        startActivity(intent1);
                        player.setPlayWhenReady(false);
                        CommonController.backgroundSound = MediaPlayer.create(context, R.raw.main_song);
                        CommonController.backgroundSound.setLooping(true);
                        CommonController.backgroundSound.start();
                        finish();
                        break;
                    case Player.STATE_IDLE:
                        Log.e("state!!!","IDLE");
                        break;
                    case Player.STATE_READY:
                        Log.e("state!!!","READY");
                        break;

                }

            }
        });


        playerView.setUseController(false);
        playerView.setPlayer(player);

        String userAgent = Util.getUserAgent(this,"TestBar");
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent,
                null,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                true
        );
        DataSource.Factory dataSourseFactory = new DefaultDataSourceFactory(this,null,httpDataSourceFactory);

        ConcatenatingMediaSource clippingMediaSource = new ConcatenatingMediaSource();


        Uri uri = Uri.parse(SimpleData.getInstance().getImageUrl()+videoUrl[videoNum]);
        MediaSource video = new ExtractorMediaSource.Factory(dataSourseFactory).createMediaSource(uri);
        clippingMediaSource.addMediaSource(video);


        player.prepare(clippingMediaSource);
        player.setPlayWhenReady(true);

    }
}
