package com.example.giyeon.blizzard.user.dto;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {

    private int col;
    private String title;
    private String contents;
    private String choise_1;
    private String choise_2;
    private String choise_3;
    private String choise_4;
    private int choise_count;
    private int answer;
    private int answer_path;
    private int explanation_col;

    private int attrCnt;
    private String attrType;
    private List<String> url;

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoise_1() {
        return choise_1;
    }

    public void setChoise_1(String choise_1) {
        this.choise_1 = choise_1;
    }

    public String getChoise_2() {
        return choise_2;
    }

    public void setChoise_2(String choise_2) {
        this.choise_2 = choise_2;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getChoise_3() {
        return choise_3;
    }

    public void setChoise_3(String choise_3) {
        this.choise_3 = choise_3;
    }

    public String getChoise_4() {
        return choise_4;
    }

    public void setChoise_4(String choise_4) {
        this.choise_4 = choise_4;
    }

    public int getChoise_count() {
        return choise_count;
    }

    public void setChoise_count(int choise_count) {
        this.choise_count = choise_count;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAnswer_path() {
        return answer_path;
    }

    public void setAnswer_path(int answer_path) {
        this.answer_path = answer_path;
    }

    public int getExplanation_col() {
        return explanation_col;
    }

    public void setExplanation_col(int explanation_col) {
        this.explanation_col = explanation_col;
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