package com.example.giyeon.blizzard.user.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.dto.EggData;
import com.example.giyeon.blizzard.user.dto.UserData;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button signUpBtn;
    private EditText idTxt;
    private EditText pwTxt;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;

        loginBtn = (Button)findViewById(R.id.login);
        signUpBtn = (Button)findViewById(R.id.signUp);
        idTxt = (EditText) findViewById(R.id.id);
        pwTxt = (EditText) findViewById(R.id.pw);

        CommonController.backgroundSound = MediaPlayer.create(context, R.raw.init_song);
        CommonController.backgroundSound.setLooping(true);
        CommonController.backgroundSound.start();

        loginBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idTxt.getText().toString().equals("") || pwTxt.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (NetworkController.getInstance().checkLogin(idTxt.getText().toString(), pwTxt.getText().toString())) {
                        int checkHasMonster = NetworkController.getInstance().initalCheck(UserData.getInstance().getId());
                        if (checkHasMonster == -1) {
                            //처음 사용자일때
                            startActivity(new Intent(LoginActivity.this, InitalActivity.class));
                        } else if (checkHasMonster > 0) {
                            EggData.getInstance().setMainExp(Integer.parseInt(EggData.getInstance().getMonsterList().get(0).get("exp").toString()));
                            EggData.getInstance().setMainLevel(Integer.parseInt(EggData.getInstance().getMonsterList().get(0).get("level").toString()));
                            EggData.getInstance().setMainEgg(EggData.getInstance().getMonsterList().get(0).get("monster").toString());
                            EggData.getInstance().setMainUrl(EggData.getInstance().getMonsterList().get(0).get("url").toString());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "ID 혹은 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        signUpBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


    }
}
