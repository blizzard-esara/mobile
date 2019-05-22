package com.example.giyeon.blizzard.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.custom.CustomIndicator;
import com.example.giyeon.blizzard.user.custom.ImagePageAdapter;
import com.example.giyeon.blizzard.user.dto.Explanation;


public class ExplanationImageActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private ViewPager viewPager;
    private CustomIndicator customIndicator;
    private Explanation explanation;
    private TextView titleText;
    private TextView contentText;

    String layout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CommonController.getInstance().getCheckActivity().interrupt();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_image_activity);

        explanation = (Explanation) getIntent().getSerializableExtra("explanation");
        layout = getIntent().getStringExtra("layout");
        imageButton = (ImageButton) findViewById(R.id.answerImage_answerBtn);
        viewPager = (ViewPager) findViewById(R.id.answerImage_vpPager);
        titleText = (TextView)findViewById(R.id.answerTitle);
        contentText = (TextView)findViewById(R.id.answerContents);

        initCustomIndicator();

        titleText.setText(explanation.getTitle());
        contentText.setText(explanation.getContents());

        viewPager.setAdapter(new ImagePageAdapter(getSupportFragmentManager(), explanation.getUrl()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                customIndicator.selectDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCustomIndicator() {
        customIndicator = (CustomIndicator)findViewById(R.id.answerImage_customIndicator);
        customIndicator.setItemMargin(15);
        customIndicator.setAnimDuration(300);
        int imageCnt = explanation.getUrl().size();
        if(imageCnt >1) {
            customIndicator.createDotPanel(imageCnt, R.mipmap.icon_min_starcraft , R.mipmap.icon_min_overwatch);
        }
    }

}
