package com.example.giyeon.blizzard.user.custom;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class StoryHandler extends Handler {
    TextView textView;
    String w;
    String story;

    public StoryHandler(TextView textView, String w) {
        this.textView = textView;
        this.w = w;
        story = "";
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Log.e("핸들러","핸들러 실행실행"+msg.arg1);
        story += w.charAt(msg.arg1);
        textView.setText(story);
    }
}

