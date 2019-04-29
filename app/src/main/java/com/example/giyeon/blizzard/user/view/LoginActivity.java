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
import com.example.giyeon.blizzard.user.controller.UserController;
import com.example.giyeon.blizzard.user.dto.UserData;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    Button signUpBtn;
    EditText idTxt;
    EditText pwTxt;

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
                if(idTxt.getText().toString().equals("") || pwTxt.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if(UserController.getInstance().checkLogin(idTxt.getText().toString(), pwTxt.getText().toString())) {
                        int checkHasMonster = UserController.getInstance().initalCheck(UserData.getInstance().getId());
                        if(checkHasMonster == -1) {
                            //처음 사용자일때
                            startActivity(new Intent(LoginActivity.this ,InitalActivity.class));
                        } else if(checkHasMonster == 1) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Intent intent = new Intent(LoginActivity.this, MonsterChoiseActivity.class);


                            startActivity(new Intent(LoginActivity.this, MonsterChoiseActivity.class).putExtra("layout","login"));
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
