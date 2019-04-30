package com.example.giyeon.blizzard.user.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.UserController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.UserData;

public class InitalActivity extends AppCompatActivity {

    private Context context;
    private CustomDialog customDialog;


    private TextView storyText;
    private ImageView initEgg;
    private ImageView starEgg;
    private ImageView overwatchEgg;
    private ImageView diabloEgg;


    private ImageView eggSpeachImageView;
    private TextView eggSpeachTv;

    private ImageView storyImageFrame;

    private AnimatorSet eggAnimator1;
    private AnimatorSet eggAnimator2;
    private AnimatorSet eggAnimator3;
    private AnimatorSet eggAnimatorReset;

    private Button startGameBtn;

    private Animation removeAnimation;


    Handler h;
    final int delay = 2500;

    String eggChar = "";

    char w;

    private boolean checkAnimator = false;

    final String word = "안녕하세요. YSERA TEAM에 온 걸 환영합니다. "+
                        "이곳은 정보를 이용해 소통하고 성장하는 세계입니다. "+
                        "알들에게 정보를 주고 알들을 성장시켜 주세요.          \n\n"+
                        "자, 당신의 첫 번째 알을 선택할 차례입니다.";

    final String eggMent = "나를 선택해줘서 고마워!          \n 잘 키워줘!";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inital_user);

        context = this;
        h = new Handler();


        storyText = (TextView)findViewById(R.id.storyText);

        //Egg Mapping
        initEgg = (ImageView)findViewById(R.id.eggImage);
        starEgg = (ImageView)findViewById(R.id.starcraftEgg);
        overwatchEgg = (ImageView)findViewById(R.id.overWatchEgg);
        diabloEgg = (ImageView) findViewById(R.id.diabloEgg);

        eggSpeachImageView = (ImageView)findViewById(R.id.eggSpeachImageView);
        storyImageFrame = (ImageView)findViewById(R.id.storyImageFrame);


        eggSpeachTv = (TextView)findViewById(R.id.eggSpeachTv);

        startGameBtn = (Button)findViewById(R.id.startGameBtn);

        eggAnimator1 = CommonController.getInstance().setAnimator(starEgg,0.7f,0, 250, delay);
        eggAnimator2 = CommonController.getInstance().setAnimator(overwatchEgg,0.7f,270, -250, delay);
        eggAnimator3 = CommonController.getInstance().setAnimator(diabloEgg,0.7f,-270, -250, delay);




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

        CommonController.getInstance().threadStart(storyText, word);

        h.postDelayed(new Runnable() {
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
                eggAnimatorReset = CommonController.getInstance().setAnimator(starEgg, 1.0f, 0, 0, delay);
                break;

            case R.id.overWatchEgg :
                eggChar = "overWatch";
                eggAnimatorReset = CommonController.getInstance().setAnimator(overwatchEgg, 1.0f, 0, 0, delay);
                break;
            case  R.id.diabloEgg :
                eggChar = "diablo";
                eggAnimatorReset = CommonController.getInstance().setAnimator(diabloEgg, 1.0f, 0, 0, delay);
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
                        CommonController.getInstance().setRemoveAnimation(context, R.anim.remove);
                        switch (eggChar) {
                            case "starCraft" : CommonController.getInstance().removeEgg(overwatchEgg, diabloEgg); break;
                            case "overWatch" : CommonController.getInstance().removeEgg(starEgg, diabloEgg); break;
                            case "diablo" : CommonController.getInstance().removeEgg(starEgg, overwatchEgg); break;
                        }

                        MonsterData.getInstance().setExp(0);
                        MonsterData.getInstance().setLevel(1);
                        MonsterData.getInstance().setMainMonster(eggChar);



                        storyText.setText("");
                        storyImageFrame.setVisibility(View.GONE);


                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                eggSpeachImageView.setVisibility(View.VISIBLE);
                                diabloEgg.setOnClickListener(null);
                                starEgg.setOnClickListener(null);
                                overwatchEgg.setOnClickListener(null);

                                CommonController.getInstance().threadStart(eggSpeachTv, eggMent);
                            }
                        }, 3000);



                        h.postDelayed(new Runnable() {
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

    private void initAnimator(AnimatorSet animatorSet) {
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
    }




}



