package com.example.giyeon.blizzard.user.custom;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;


public class TimerHandler extends Handler {
    TextView textView;
    public TimerHandler(TextView tv) {
        textView = tv;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //Log.e("asdadsad",msg.arg1+"이래");
        textView.setText(msg.arg1+"");
    }
}
