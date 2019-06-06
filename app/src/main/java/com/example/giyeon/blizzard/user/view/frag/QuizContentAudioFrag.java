package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.CustomGameoutDialog;
import com.example.giyeon.blizzard.user.custom.TimerHandler;
import com.example.giyeon.blizzard.user.dto.Quiz;
import com.example.giyeon.blizzard.user.dto.SimpleData;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Map;


@SuppressLint("ValidFragment")
public class QuizContentAudioFrag extends Fragment implements MainActivity.OnBackPressedListener{

    private View view;
    private Quiz quiz;

    private TextView timer;
    private TextView contents;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button[] btnArr;

    private Button beforeBtn;
    private Button resumeBtn;
    private Button afterBtn;

    private LinearLayout answerBtn2Contatiner;
    private ExoPlayer player;

    private CustomGameoutDialog customDialog;
    private View.OnClickListener answerClickListener;
    private View.OnClickListener wrongClickListener;

    Animation animation;
    Thread thread;
    Thread timeThread;
    Message msg;
    TimerHandler h;
    Handler mHandler;
    private int limitTime = 10;

    @SuppressLint("ValidFragment")
    public QuizContentAudioFrag(Quiz quiz){
        this.quiz = quiz;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            setRemove();
            CommonController.backgroundSound = MediaPlayer.create(getContext(), R.raw.main_song);
            CommonController.backgroundSound.setLooping(true);
            CommonController.backgroundSound.start();
            ((MainActivity)getActivity()).setToggleTrue();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_quiz_content_audio, container, false);

        ((MainActivity)getActivity()).setToggleFalse();
        init_AudioSource();

        answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                CommonController.getInstance().expKeepAlgorithm(quiz.getCol());

                setRemove();
                ((MainActivity)getActivity()).showExplanation(NetworkController.getInstance().explanationShow(quiz.getExplanation_col()), quiz.getAnswer_path());

            }
        };

        wrongClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new CustomGameoutDialog(getContext(),R.drawable.out_wrong,SimpleData.getInstance().getQuizExp(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        setRemove();
                        Map<String, Object> map = SimpleData.getInstance().expReflection();
                        CommonController.getInstance().getGetCharacterVideoActivity(getContext(), map);
                        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit(); //여기 결과창으로 변경
                    }
                });

                if(timeThread != null)
                    timeThread.interrupt();


                setRemove();
                customDialog.show();
            }
        };


        setView();
        setContent();

        return view;
    }

    private void setView() {
        timer = (TextView)view.findViewById(R.id.quizConAudio_timer);
        contents = (TextView)view.findViewById(R.id.quizConAudio_Contents);
        btn1 = (Button)view.findViewById(R.id.quizConAudio_an1);
        btn2 = (Button)view.findViewById(R.id.quizConAudio_an2);
        btn3 = (Button)view.findViewById(R.id.quizConAudio_an3);
        btn4 = (Button)view.findViewById(R.id.quizConAudio_an4);

        answerBtn2Contatiner = (LinearLayout)view.findViewById(R.id.quizConAudio_an2Linear);

        beforeBtn = (Button)view.findViewById(R.id.quizCon_beforeBtn);
        resumeBtn = (Button)view.findViewById(R.id.quizCon_resumeBtn);
        afterBtn = (Button)view.findViewById(R.id.quizCon_afterBtn);



        h = new TimerHandler(timer);
        btnArr = new Button[]{btn1, btn2, btn3, btn4};
    }
    private void setContent() {

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);

        btn1.setText(quiz.getChoise_1());
        btn2.setText(quiz.getChoise_2());
        btn3.setText(quiz.getChoise_3());
        btn4.setText(quiz.getChoise_4());

        for(int i =0 ; i<quiz.getChoise_count() ; i++) {
            if(quiz.getAnswer() == i+1)
                btnArr[i].setOnClickListener(answerClickListener);
            else {
                btnArr[i].setOnClickListener(wrongClickListener);
            }
        }


        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(contents, quiz.getContents());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    CommonController.getInstance().getT().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0 ; i< quiz.getChoise_count() ; i++) {
                            btnArr[i].startAnimation(animation);
                            btnArr[i].setVisibility(View.VISIBLE);
                        }
                        if(quiz.getChoise_count() == 2) {
                            answerBtn2Contatiner.setVisibility(View.GONE);
                            btnArr[0].setTextSize(50);
                            btnArr[1].setTextSize(50);
                        }
                    }
                },0);
                try {
                    Thread.sleep(2200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread.join();

                    while (limitTime-- > 0) {
                        msg = h.obtainMessage(limitTime);
                        msg.arg1 = limitTime;
                        h.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                    mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            customDialog = new CustomGameoutDialog(getContext(), R.drawable.out_timeout,SimpleData.getInstance().getQuizExp(), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                    setRemove();
                                    Map<String, Object> map = SimpleData.getInstance().expReflection();
                                    CommonController.getInstance().getGetCharacterVideoActivity(getContext(), map);
                                    getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit(); //여기 결과창으로 변경

                                }
                            });

                            customDialog.show();
                        }
                    },0);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        });
        timeThread.start();

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getPlayWhenReady())
                    player.seekTo(player.getCurrentPosition() - 5000);
            }
        });
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getPlayWhenReady()) {
                    player.setPlayWhenReady(false);
                    resumeBtn.setBackground(getResources().getDrawable(R.drawable.music_start));
                } else {
                    player.setPlayWhenReady(true);
                    resumeBtn.setBackground(getResources().getDrawable(R.drawable.music_pause));
                }
            }
        });

        afterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getPlayWhenReady())
                    player.seekTo(player.getCurrentPosition()+5000);
            }
        });

    }

    private void setRemove() {
        player.setPlayWhenReady(false);
        if(h != null)
            h.removeCallbacksAndMessages(0);
        if(mHandler != null)
            mHandler.removeCallbacksAndMessages(0);
        if(timeThread != null)
            timeThread.interrupt();
        if(thread != null)
            thread.interrupt();
    }

    private void init_AudioSource() {

        CommonController.backgroundSound.stop();
        player = ExoPlayerFactory.newSimpleInstance(getContext());


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "ExoplayerDemo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Handler mainHandler = new Handler();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(SimpleData.getInstance().getImageUrl()+quiz.getUrl().get(0)),
                dataSourceFactory,
                extractorsFactory,
                mainHandler,
                null);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }


    @Override
    public void onBack() {
        if(SimpleData.getInstance().backPressedTime == 0) {
            Toast.makeText(getContext(), "한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            SimpleData.getInstance().backPressedTime = System.currentTimeMillis() - SimpleData.getInstance().backPressedTime;
        } else {
            int seconds = (int) (System.currentTimeMillis() - SimpleData.getInstance().backPressedTime);

            if(seconds > 2000) {
                Toast.makeText(getContext(), "한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                SimpleData.getInstance().backPressedTime = 0;
            } else {
                setRemove();
                MainActivity activity = (MainActivity)getActivity();
                activity.setOnBackPressedListener(null);
                getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
            }
        }

    }
}

