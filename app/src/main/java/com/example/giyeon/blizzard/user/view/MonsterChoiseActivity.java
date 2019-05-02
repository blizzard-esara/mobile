package com.example.giyeon.blizzard.user.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.UserController;
import com.example.giyeon.blizzard.user.custom.StoryHandler;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.UserData;

import java.util.ArrayList;

public class MonsterChoiseActivity extends AppCompatActivity {

    String word;

    Handler h;
    TextView storyText;

    String layout;
    int delay = 2500;

    private Context context;
    private ImageView starEgg;
    private ImageView overwatchEgg;
    private ImageView diabloEgg;
    private AnimatorSet eggAnimator1;
    private AnimatorSet eggAnimator2;
    private AnimatorSet eggAnimator3;
    private AnimatorSet eggAnimatorReset;

    private ImageView eggSpeachImageView;
    private TextView eggSpeachTv;

    private ArrayList<String> listArr;
    private String eggMent = "하이잇!";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_choise);

        context = this;
        h = new Handler();
        storyText = (TextView)findViewById(R.id.storyText);

        layout = getIntent().getStringExtra("layout");
        setStoryText(layout);
        listArr = new ArrayList<String>();

        mappingImageView();


        CommonController.getInstance().threadStart(storyText, word);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                showEggList(MonsterData.getInstance().getMonsterList().size());

            }
        },3000);

    }

    public void eggChoise(View view) {
        String eggChar = "";

        switch (view.getId()) {
            case R.id.starcraftEgg :
                eggChar = "starCraft";
                eggAnimatorReset = CommonController.getInstance().setAnimator(starEgg, 1.0f, 0, 0, delay-1500);
                break;

            case R.id.overWatchEgg :
                eggChar = "overWatch";
                eggAnimatorReset = CommonController.getInstance().setAnimator(overwatchEgg, 1.0f, 0, 0, delay-1500);
                break;
            case  R.id.diabloEgg :
                eggChar = "diablo";
                eggAnimatorReset = CommonController.getInstance().setAnimator(diabloEgg, 1.0f, 0, 0, delay-1500);
                break;
        }
        CommonController.getInstance().setRemoveAnimation(context, R.anim.remove_fast);
        switch (eggChar) {
            case "starCraft" : CommonController.getInstance().removeEgg(overwatchEgg, diabloEgg); break;
            case "overWatch" : CommonController.getInstance().removeEgg(starEgg, diabloEgg); break;
            case "diablo" : CommonController.getInstance().removeEgg(starEgg, overwatchEgg); break;
        }
        MonsterData.getInstance().setMonsterUrl(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), eggChar));

        for(int i = 0 ; i< listArr.size() ; i++) {
            if (MonsterData.getInstance().getMonsterList().get(i).containsValue(eggChar)) {
                MonsterData.getInstance().setExp(Integer.parseInt(MonsterData.getInstance().getMonsterList().get(i).get("exp").toString()));
                MonsterData.getInstance().setLevel(Integer.parseInt(MonsterData.getInstance().getMonsterList().get(i).get("level").toString()));
                MonsterData.getInstance().setMainMonster(eggChar);
                break;
            }
        }
        diabloEgg.setOnClickListener(null);
        starEgg.setOnClickListener(null);
        overwatchEgg.setOnClickListener(null);
        eggAnimatorReset.start();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                eggSpeachImageView.setVisibility(View.VISIBLE);


                CommonController.getInstance().threadStart(eggSpeachTv, eggMent);
            }
        }, 1100);


            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(layout.equals("login")) {
                        startActivity(new Intent(MonsterChoiseActivity.this, MainActivity.class));
                        finish();
                    } else {
                        finish();
                    }
                }
            },2400);

        }



    private void showEggList(int cnt) {
        for(int i = 0 ; i < cnt ; i++) {
            listArr.add(String.valueOf(MonsterData.getInstance().getMonsterList().get(i).get("monster")));
        }

        switch (cnt) {
            case 2:
                if(!listArr.contains("diablo")) { // diablo가 없을때

                    Glide.with(context).load(R.drawable.egg_lock2).into(diabloEgg);
                    diabloEgg.setOnClickListener(null);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "starCraft")).into(starEgg);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "overWatch")).into(overwatchEgg);

                } else if(!listArr.contains("overWatch")) {
                    Glide.with(context).load(R.drawable.egg_lock2).into(overwatchEgg);
                    overwatchEgg.setOnClickListener(null);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "starCraft")).into(starEgg);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "diablo")).into(diabloEgg);

                } else if(!listArr.contains("starCraft")) {
                    Glide.with(context).load(R.drawable.egg_lock2).into(starEgg);
                    starEgg.setOnClickListener(null);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "overWatch")).into(overwatchEgg);
                    Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "diablo")).into(diabloEgg);
                }
                break;

            case 3:
                Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "starCraft")).into(starEgg);
                Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "overWatch")).into(overwatchEgg);
                Glide.with(context).load(UserController.getInstance().mainMonsterImageURL(UserData.getInstance().getId(), "diablo")).into(diabloEgg);
                break;
        }

        diabloEgg.setVisibility(View.VISIBLE);
        starEgg.setVisibility(View.VISIBLE);
        overwatchEgg.setVisibility(View.VISIBLE);


        eggAnimator1.start();
        eggAnimator2.start();
        eggAnimator3.start();
    }

    private void mappingImageView() {
        starEgg = (ImageView)findViewById(R.id.starcraftEgg);
        overwatchEgg = (ImageView)findViewById(R.id.overWatchEgg);
        diabloEgg = (ImageView) findViewById(R.id.diabloEgg);

        eggSpeachImageView = (ImageView)findViewById(R.id.eggSpeachImageView);
        eggSpeachTv = (TextView)findViewById(R.id.eggSpeachTv);


        eggAnimator1 = CommonController.getInstance().setAnimator(starEgg,0.7f,0, 250, delay-1000);
        eggAnimator2 = CommonController.getInstance().setAnimator(overwatchEgg,0.7f,270, -250, delay-1000);
        eggAnimator3 = CommonController.getInstance().setAnimator(diabloEgg,0.7f,-270, -250, delay-1000);


    }

    private void setStoryText(String layout) {
        if(layout.equals("login")) {
            word = "다시 돌아와 줬구나!   \n성장시킬 알을 골라줘!";
        } else {
            word = "성장 시킬 알을 변경하러 왔구나    \n"+
                    "자 너가 키울 알을 선택해줘! ";
        }
    }


}

