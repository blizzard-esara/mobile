package com.example.giyeon.blizzard.user.dto;

public class SimpleData {

     private String url = "http://10.0.2.2:8080";
    public final long FINISH_INTERVAL_TIME = 2000;
    public long backPressedTime = 0;

    private SimpleData() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private static class LazyHolder {
        public static final SimpleData INSTANCE = new SimpleData();
    }
    public static SimpleData getInstance() {
        return LazyHolder.INSTANCE;
    }

}
