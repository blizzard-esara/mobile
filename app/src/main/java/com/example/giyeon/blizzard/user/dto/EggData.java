package com.example.giyeon.blizzard.user.dto;


import java.util.List;
import java.util.Map;

public class MonsterData {

    private String mainMonster;
    private int exp;
    private int level;
    private List<Map<String, Object>> monsterList;
    private String monsterUrl;

    public List<Map<String, Object>> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Map<String, Object>> monsterList) {
        this.monsterList = monsterList;
    }

    public String getMainMonster() {
        return mainMonster;
    }

    public void setMainMonster(String mainMonster) {
        this.mainMonster = mainMonster;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getMonsterUrl() {
        return monsterUrl;
    }

    public void setMonsterUrl(String monsterUrl) {
        this.monsterUrl = monsterUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private static class LazyHolder {
        public static final MonsterData INSTANCE = new MonsterData();
    }
    public static MonsterData getInstance() {
        return LazyHolder.INSTANCE;
    }
}
