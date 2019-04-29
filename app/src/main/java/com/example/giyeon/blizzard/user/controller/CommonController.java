package com.example.giyeon.blizzard.user.controller;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.giyeon.blizzard.user.custom.CustomTypefaceSpan;


public class CommonController {


    public static MediaPlayer backgroundSound;

    private CommonController() {

    }

    private static class LazyHolder {
        public static final CommonController INSTANCE = new CommonController();
    }
    public static CommonController getInstance() {
        return LazyHolder.INSTANCE;
    }




}
