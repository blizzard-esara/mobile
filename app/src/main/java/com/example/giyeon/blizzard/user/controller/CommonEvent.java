package com.example.giyeon.blizzard.user.controller;

import android.support.v4.app.FragmentManager;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.view.frag.ExplanationFragment;
import com.example.giyeon.blizzard.user.view.frag.MainAdvantureFragment;

import java.util.Map;

public class CommonEvent {

    private CommonEvent() {

    }

    private static class LazyHolder {
        public static final CommonEvent INSTANCE = new CommonEvent();
    }
    public static CommonEvent getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void mainThreeBtnEvent(String kind, Map<String, Object> map, FragmentManager fragmentManager) {

        MonsterData.getInstance().setMainMonster(map.get("monster").toString());
        MonsterData.getInstance().setExp(Integer.parseInt(map.get("exp").toString()));
        MonsterData.getInstance().setLevel(Integer.parseInt(map.get("level").toString()));
        switch (kind) {
            case "explanation":
                fragmentManager.beginTransaction().replace(R.id.content_main, ExplanationFragment.getInstance()).commit();
                break;
            case "adventure":
                fragmentManager.beginTransaction().replace(R.id.content_main, MainAdvantureFragment.getInstance()).commit();
                break;
            case "produce":
                //fragmentManager.beginTransaction().replace(R.id.content_main, ExplanationFragment.getInstance()).commit();
                break;

        }

    }
}
