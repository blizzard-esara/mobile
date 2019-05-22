package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.custom.CustomIndicator;
import com.example.giyeon.blizzard.user.custom.ImagePageAdapter;
import com.example.giyeon.blizzard.user.custom.TimerHandler;
import com.example.giyeon.blizzard.user.dto.Quiz;

@SuppressLint("ValidFragment")
public class QuizContentFrag extends Fragment {

    private View view;
    private Quiz quiz;

    private TextView timer;
    private TextView contents;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button[] btnArr;

    private ViewPager viewPager;
    private CustomIndicator customIndicator;


    private CustomDialog customDialog;
    private View.OnClickListener answerClickListener;
    private View.OnClickListener wrongClickListener;

    Animation animation;
    Thread thread;
    Thread timeThread;
    Message msg;
    TimerHandler h;
    Handler mHandler;
    Context context;
    private int limitTime = 10;

    @SuppressLint("ValidFragment")
    public QuizContentFrag(Quiz quiz){
        this.quiz = quiz;
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            setRemove();
            ((MainActivity)getActivity()).setToggleTrue();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_quiz_content, container, false);
        context = getContext();

        ((MainActivity)getActivity()).setToggleFalse();

        answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "정답입니다.!", Toast.LENGTH_SHORT).show();
                setRemove();
                ((MainActivity)getActivity()).showExplanation(NetworkController.getInstance().explanationShow(quiz.getExplanation_col()),"quiz", quiz.getAnswer_path());

            }
        };

        wrongClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new CustomDialog(getContext(), "!오답", "오답입니다.\n 더 공부해보고 오세요!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        viewPager.setAdapter(null);
                        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit(); //여기 결과창으로 변경
                    }
                });
                customDialog.show();
            }
        };


        setView();
        setContent();

        return view;
    }

    private void setView() {
        timer = (TextView)view.findViewById(R.id.quizCon_timer);
        contents = (TextView)view.findViewById(R.id.quizCon_Contents);
        btn1 = (Button)view.findViewById(R.id.quizCon_an1);
        btn2 = (Button)view.findViewById(R.id.quizCon_an2);
        btn3 = (Button)view.findViewById(R.id.quizCon_an3);
        btn4 = (Button)view.findViewById(R.id.quizCon_an4);

        viewPager = (ViewPager)view.findViewById(R.id.quizCon_vpPager);

        viewPager.setAdapter(new ImagePageAdapter(getFragmentManager(), quiz.getUrl()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                customIndicator.selectDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        h = new TimerHandler(timer);
        btnArr = new Button[]{btn1, btn2, btn3, btn4};
    }
    private void setContent() {

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);

        btn1.setText(quiz.getChoise_1());
        btn2.setText(quiz.getChoise_2());
        btn3.setText(quiz.getChoise_3());
        btn4.setText(quiz.getChoise_4());

        for(int i =0 ; i<btnArr.length ;i++) {
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
                        for(int i = 0 ; i< btnArr.length ; i++) {
                            btnArr[i].startAnimation(animation);
                            btnArr[i].setVisibility(View.VISIBLE);
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
                            customDialog = new CustomDialog(getContext(), "!Time out", "시간초과로 결과 화면으로 돌아갑니다!", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                    viewPager.setAdapter(null);
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

    }

    private void setRemove() {
        h.removeCallbacksAndMessages(0);
        mHandler.removeCallbacksAndMessages(0);
        timeThread.interrupt();
        thread.interrupt();
    }

    private void initCustomIndicator() {
        customIndicator = (CustomIndicator)view.findViewById(R.id.answerImage_customIndicator);
        customIndicator.setItemMargin(15);
        customIndicator.setAnimDuration(300);
        int imageCnt = quiz.getUrl().size();
        if(imageCnt >1) {
            customIndicator.createDotPanel(imageCnt, R.mipmap.icon_min_starcraft , R.mipmap.icon_min_overwatch);
        }
    }


}
