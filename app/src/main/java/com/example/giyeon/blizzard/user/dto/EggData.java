package com.example.giyeon.blizzard.user.dto;


import java.util.List;
import java.util.Map;

public class EggData {

    private String mainEgg;
    private int mainExp;
    private int mainLevel;
    private List<Map<String, Object>> eggList;
    private String mainUrl;

    public List<Map<String, Object>> getMonsterList() {
        return eggList;
    }

    public void setMonsterList(List<Map<String, Object>> monsterList) {
        this.eggList = monsterList;
    }

    public String getMainEgg() {
        return mainEgg;
    }

    public void setMainEgg(String mainEgg) {
        this.mainEgg = mainEgg;
    }

    public int getMainExp() {
        return mainExp;
    }

    public void setMainExp(int mainExp) {
        this.mainExp = mainExp;
    }

    public int getMainLevel() {
        return mainLevel;
    }

    public void setMainLevel(int mainLevel) {
        this.mainLevel = mainLevel;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    private static class LazyHolder {
        public static final EggData INSTANCE = new EggData();
    }
    public static EggData getInstance() {
        return LazyHolder.INSTANCE;
    }
}
