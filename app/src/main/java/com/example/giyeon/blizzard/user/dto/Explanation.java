package com.example.giyeon.blizzard.user.dto;

import java.io.Serializable;
import java.util.List;

public class Explanation implements Serializable {
    private int col;
    private String theme;
    private String level;
    private String title;
    private String contents;
    private boolean solve;
    private int attrCnt;
    private String attrType;
    private List<String> url;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSolve() {
        return solve;
    }

    public void setSolve(boolean solve) {
        this.solve = solve;
    }

    public int getAttrCnt() {
        return attrCnt;
    }

    public void setAttrCnt(int attrCnt) {
        this.attrCnt = attrCnt;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
