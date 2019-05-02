package com.example.giyeon.blizzard.user.controller;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.custom.CustomTypefaceSpan;
import com.example.giyeon.blizzard.user.custom.StoryHandler;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class CommonController {


    public static MediaPlayer backgroundSound;
    private StoryHandler handler;
    private Animation removeAnimation;
    private Thread t;
    private Message msg;



    private CommonController() {

    }

    private static class LazyHolder {
        public static final CommonController INSTANCE = new CommonController();
    }
    public static CommonController getInstance() {
        return LazyHolder.INSTANCE;
    }


    public void threadStart(TextView storyText, String word) {
        final String finalWord = word;

        handler = new StoryHandler(storyText, word);

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ; i< finalWord.length() ; i++) {
                    try {
                        Thread.sleep(100);
                        msg = handler.obtainMessage(i);
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                //Out Text
            }

        });
        t.start();

    }


    public void threadStop() {
        try {
            handler.removeCallbacksAndMessages(0);
            t.interrupt();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void setRemoveAnimation(Context context, int animation) {
        removeAnimation = AnimationUtils.loadAnimation(context, animation);
    }

    public void removeEgg(ImageView obj1, ImageView obj2) {
        obj1.setAnimation(removeAnimation);
        obj2.setAnimation(removeAnimation);
        obj1.setVisibility(View.GONE);
        obj2.setVisibility(View.GONE);
    }

    public AnimatorSet setAnimator(ImageView obj, float sizeXY, int transX, int transY, int duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(obj, "scaleX", sizeXY);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(obj, "scaleY", sizeXY);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(obj, "alpha", 0.0f, 1f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(obj, "translationX", transX);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(obj, "translationY", transY);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(scaleX, scaleY, alpha, translationX, translationY);
        animatorSet.setDuration(duration);

        return animatorSet;
    }

    public void setFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/shylock_nbp.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }






}
