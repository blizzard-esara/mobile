package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;

@SuppressLint("ValidFragment")
public class QuizLevelFrag extends Fragment implements MainActivity.OnBackPressedListener {

    private String kind;
    private View view;
    private TextView speakTv;
    private String word;
    private RelativeLayout easy;
    private RelativeLayout hard;
    private CustomDialog customDialog;


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

        customDialog = new CustomDialog(getContext(), "! 알림","문제를 다푸셨습니다.\n다음 업데이트를 기대해주세요.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonController cc = CommonController.getInstance();

                cc.setQuizList(NetworkController.getInstance().quizList(kind, "easy"));


                if(cc.getQuizList().isEmpty()) {
                    customDialog.show();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizContentFrag(cc.getQuizList().get(0))).commit();
                }
                //문제 시작 경험치 문제당 20
                //getFragmentManager().beginTransaction().replace(R.id.content_main, new ExplanationFragment()).commit();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonController cc = CommonController.getInstance();

                cc.setQuizList(NetworkController.getInstance().quizList(kind, "hard"));


                if(cc.getQuizList().isEmpty()) {
                    customDialog.show();
                } else {
                    if(cc.getQuizList().get(0).getAttrType() != null) {
                        if (cc.getQuizList().get(0).getAttrType().equals("audio")) {
                            getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizContentAudioFrag(cc.getQuizList().get(0))).commit();
                        } else {
                            getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizContentFrag(cc.getQuizList().get(0))).commit();
                        }
                    } else {
                        getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizContentFrag(cc.getQuizList().get(0))).commit();
                    }
                }
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
