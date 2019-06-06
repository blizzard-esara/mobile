package com.example.giyeon.blizzard.user.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;

public class GetCharacterActivity extends AppCompatActivity {


    ImageView imageView;
    TextView textView;

    Button homeBtn;
    String monsterName;
    String imageUrl;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_character);

        monsterName = getIntent().getStringExtra("monsterName");
        imageUrl = getIntent().getStringExtra("monsterUrl");

        imageView = findViewById(R.id.getCharacter_image);
        textView = findViewById(R.id.getCharacter_name);

        homeBtn = findViewById(R.id.getCharacter_mainBtn);


        Log.e("getCharacter",imageUrl);
        Glide.with(this).load(imageUrl).into(imageView);
        textView.setText(monsterName+"를");

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
