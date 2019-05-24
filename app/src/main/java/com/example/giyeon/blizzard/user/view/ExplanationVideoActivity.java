package com.example.giyeon.blizzard.user.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.custom.OnSwipeTouchListener;
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

public class ExplanationVideoActivity extends AppCompatActivity {


    Explanation explanation;
    ExoPlayer player;

    private TextView titleText;
    private TextView contentText;
    private int pageNum;

    private String layout;
    @Override
    public void onBackPressed() {

            player.setPlayWhenReady(false);
            CommonController.backgroundSound = MediaPlayer.create(this, R.raw.main_song);
            CommonController.backgroundSound.setLooping(true);
            CommonController.backgroundSound.start();

            if(CommonController.getInstance().getCheckActivity() != null) {
                if (CommonController.getInstance().getCheckActivity().isAlive())
                    CommonController.getInstance().getCheckActivity().interrupt();
            }
            finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_video_activity);

        explanation = (Explanation) getIntent().getSerializableExtra("explanation");
        layout = getIntent().getStringExtra("layout");
        titleText = findViewById(R.id.answerTitle);
        contentText = findViewById(R.id.answerContents);

        CommonController.backgroundSound.stop();
        titleText.setText(explanation.getTitle());
        contentText.setText(explanation.getContents());

        init_Vedio();

    }

    private void init_Vedio() {

        final PlayerView playerView = (PlayerView)findViewById(R.id.video_exoPlayer);

        playerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                player.seekTo(player.getCurrentPosition() - 5000);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                player.seekTo(player.getCurrentPosition() + 5000);
            }

            @Override
            public void onClick() {
                super.onClick();

            }

            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                if(player.getPlayWhenReady()) {
                    player.setPlayWhenReady(false);
                    Toast.makeText(ExplanationVideoActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                } else {
                    player.setPlayWhenReady(true);
                    Toast.makeText(ExplanationVideoActivity.this, "Start", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                        Log.e("state!!!","END");
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


            Uri uri = Uri.parse(SimpleData.getInstance().getImageUrl()+explanation.getUrl().get(0));
            MediaSource video = new ExtractorMediaSource.Factory(dataSourseFactory).createMediaSource(uri);
            clippingMediaSource.addMediaSource(video);


        player.prepare(clippingMediaSource);
        player.setPlayWhenReady(true);

    }

}
