package com.example.giyeon.blizzard.user.controller;

import android.media.MediaPlayer;


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
