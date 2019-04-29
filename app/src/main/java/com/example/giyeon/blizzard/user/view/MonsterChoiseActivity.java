package com.example.giyeon.blizzard.user.view;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.custom.StoryHandler;

public class MonsterChoiseActivity extends AppCompatActivity {

    String word;

    int tTime;
    StoryHandler handler;
    TextView storyText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_choise);

        storyText = (TextView)findViewById(R.id.storyText);
        String layout = getIntent().getStringExtra("layout");

        if(layout.equals("login")) {
            word = "다시 돌아와 줬구나!   \n성장시킬 알을 골라줘!";
        } else {
           word = "성장 시킬 알을 변경하러 왔구나    \n"+
                    "자 너가 키울 알을 선택해줘! ";
        }

        handler = new StoryHandler(storyText, word);

        Thread stroyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ; i< word.length() ; i++) {
                    try {
                        Thread.sleep(100);
                        Message msg = handler.obtainMessage(i);
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        stroyThread.start();
    }
}

