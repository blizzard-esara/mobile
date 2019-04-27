package com.example.giyeon.blizzard.user.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.UserController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.dto.UserData;

public class InitalActivity extends AppCompatActivity {

    private Context context;

    TextView storyText;
    ImageView initEgg;
    ImageView starEgg;
    ImageView overwatchEgg;
    ImageView diabloEgg;


    ImageView eggSpeachImageView;
    TextView eggSpeachTv;

    ImageView storyImageFrame;

    AnimatorSet eggAnimator1;
    AnimatorSet eggAnimator2;
    AnimatorSet eggAnimator3;
    AnimatorSet eggAnimatorReset;

    Button startGameBtn;

    Animation removeAnimation;
    private CustomDialog customDialog;

    StoryHandler handler;
    String story = "";
    char w;
    String eggChar = "";



    private boolean checkAnimator = false;

    final String word = "안녕하세요. YSERA TEAM에 온 걸 환영합니다. "+
    "이곳은 정보를 이용해 소통하고 성장하는 세계입니다. "+
    "알들에게 정보를 주고 알들을 성장시켜 주세요.          \n\n"+
    "자, 당신의 첫 번째 알을 선택할 차례입니다.";
    final String eggMent = "나를 선택해줘서 고마워!          \n 잘 키워줘!";

    Thread stroyThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inital_user);

        context = this;


        storyText = (TextView)findViewById(R.id.storyText);

        //Egg Mapping
        initEgg = (ImageView)findViewById(R.id.eggImage);
        starEgg = (ImageView)findViewById(R.id.starcraftEgg);
        overwatchEgg = (ImageView)findViewById(R.id.overWatchEgg);
        diabloEgg = (ImageView) findViewById(R.id.diabloEgg);

        eggSpeachImageView = (ImageView)findViewById(R.id.eggSpeachImageView);
        eggSpeachTv = (TextView)findViewById(R.id.eggSpeachTv);
        storyImageFrame = (ImageView)findViewById(R.id.storyImageFrame);

        startGameBtn = (Button)findViewById(R.id.startGameBtn);

        eggAnimator1 = setAnimator(starEgg,0, 250);
        eggAnimator2 = setAnimator(overwatchEgg,270, -250);
        eggAnimator3 = setAnimator(diabloEgg,-270, -250);




        removeAnimation = AnimationUtils.loadAnimation(context, R.anim.remove);



        /*
        initEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initEgg.setVisibility(v.GONE);
                starEgg.setVisibility(View.VISIBLE);
                overwatchEgg.setVisibility(View.VISIBLE);
                diabloEgg.setVisibility(View.VISIBLE);

                eggAnimator1.start();
                eggAnimator2.start();
                eggAnimator3.start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initAnimator(eggAnimator1);
                        initAnimator(eggAnimator2);
                        initAnimator(eggAnimator3);
                    }
                }, 3000);

            }
        });
*/

        handler = new StoryHandler(storyText, word);

        stroyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ; i< word.length() ; i++) {
                    try {
                        Thread.sleep(100);
                        Message msg = handler.obtainMessage(i);
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        stroyThread.start();

        Handler h = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               seperateEgg();
            }
        }, 13000);

    }

    public void eggClickEvent(View view) {

        if(checkAnimator) {
            initAnimator(eggAnimatorReset);
        }

        switch (view.getId()) {
            case R.id.starcraftEgg :
                eggChar = "starCraft";
                eggAnimatorReset = returnAnimator(starEgg);
                break;

            case R.id.overWatchEgg :
                eggChar = "overWatch";
                eggAnimatorReset = returnAnimator(overwatchEgg);
                break;
            case  R.id.diabloEgg :

                eggAnimatorReset = returnAnimator(diabloEgg);
                eggChar = "diablo";
                break;
        }

        customDialog = new CustomDialog(context, "! 한번 선택후 되돌릴 수 없습니다.", "정말로 이 알을 고르시겠습니까?",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        checkAnimator = true;
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        eggAnimatorReset.start();
                        switch (eggChar) {
                            case "starCraft" : removeEgg(overwatchEgg, diabloEgg); break;
                            case "overWatch" : removeEgg(starEgg, diabloEgg); break;
                            case "diablo" : removeEgg(starEgg, overwatchEgg); break;
                        }


                        handler = new StoryHandler(eggSpeachTv, eggMent);




                        storyText.setText("");
                        storyImageFrame.setVisibility(View.GONE);

                        Handler h = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                eggSpeachImageView.setVisibility(View.VISIBLE);
                                diabloEgg.setOnClickListener(null);
                                starEgg.setOnClickListener(null);
                                overwatchEgg.setOnClickListener(null);

                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for(int i =0 ; i< eggMent.length() ; i++) {
                                            try {
                                                Thread.sleep(100);
                                                Message msg = handler.obtainMessage(i);
                                                msg.arg1 = i;
                                                handler.sendMessage(msg);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        //Out Text
                                    }

                                });
                                t.start();
                            }
                        }, 3000);



                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startGameBtn.setVisibility(View.VISIBLE);
                                startGameBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(UserController.getInstance().eggChoise(UserData.getInstance().getId(), eggChar)) {
                                            startActivity(new Intent(InitalActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        }, 5000);




                    }
                });
        customDialog.show();



    }

    private void seperateEgg() {
        initEgg.setVisibility(View.GONE);
        starEgg.setVisibility(View.VISIBLE);
        overwatchEgg.setVisibility(View.VISIBLE);
        diabloEgg.setVisibility(View.VISIBLE);

        eggAnimator1.start();
        eggAnimator2.start();
        eggAnimator3.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initAnimator(eggAnimator1);
                initAnimator(eggAnimator2);
                initAnimator(eggAnimator3);
            }
        }, 3000);
    }
    private void removeEgg(ImageView obj1, ImageView obj2) {
        obj1.setAnimation(removeAnimation);
        obj2.setAnimation(removeAnimation);
        obj1.setVisibility(View.GONE);
        obj2.setVisibility(View.GONE);
    }

    private void initAnimator(AnimatorSet animatorSet) {
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
    }

    private AnimatorSet returnAnimator(ImageView obj) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(obj, "scaleX", 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(obj, "scaleY", 1.0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(obj, "alpha", 0.0f, 1f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(obj, "translationX", 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(obj, "translationY", 0);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(scaleX, scaleY, alpha, translationX, translationY);
        animatorSet.setDuration(2500);

        return animatorSet;
    }

    private AnimatorSet setAnimator(ImageView obj, int transX, int transY) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(obj, "scaleX", 0.7f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(obj, "scaleY", 0.7f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(obj, "alpha", 0.0f, 1f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(obj, "translationX", transX);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(obj, "translationY", transY);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(scaleX, scaleY, alpha, translationX, translationY);
        animatorSet.setDuration(2500);

        return animatorSet;
    }


    class StoryHandler extends Handler {
        TextView textView;
        String w;

        public StoryHandler(TextView textView, String w) {
            this.textView = textView;
            this.w = w;
            story = "";
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            story += w.charAt(msg.arg1);
            textView.setText(story);
        }
    }
}



