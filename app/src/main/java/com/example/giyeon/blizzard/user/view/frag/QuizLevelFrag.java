package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;

@SuppressLint("ValidFragment")
public class QuizLevelFrag extends Fragment implements MainActivity.OnBackPressedListener {

    private String kind;
    private View view;
    private TextView speakTv;
    private String word;
    private RelativeLayout easy;
    private RelativeLayout hard;


    @SuppressLint("ValidFragment")
    public QuizLevelFrag (String kind) {
        this.kind = kind;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_quiz_level, container, false);
        setWord();
        setView();
        setContent();

        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(speakTv, word);

        return view;
    }

    private void setWord() {
        switch (kind) {
            case "overWatch":
                word = "오버워치 문제를 골랐네! 좋아 난이도를 골라보자구!";
                break;
            case "starCraft":
                word = "스타크래프트 문제를 골랐네! 좋아 난이도를 골라보자구!";
                break;
        }
    }

    public void setView() {
        speakTv = (TextView)view.findViewById(R.id.quizlevel_storyTv);
        easy = (RelativeLayout)view.findViewById(R.id.quizlevel_easy);
        hard = (RelativeLayout)view.findViewById(R.id.quizlevel_hard);

    }

    public void setContent() {
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //문제 시작 경험치 문제당 20
                //getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, new ExplanationFragment()).commit();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainQuizeFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);
    }
}
