package com.example.giyeon.blizzard.user.view.frag;

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
import com.example.giyeon.blizzard.user.custom.CustomDialog;

public class MainQuizeFragment extends Fragment implements MainActivity.OnBackPressedListener {
    private View view;
    private TextView speakTv;
    private final String word =  "문제를 풀어서 그 경험치로 알을 성장시킬수 있어! 문제를 골라봐!";
    private RelativeLayout overwatch;
    private RelativeLayout starcraft;
    private RelativeLayout diablo;
    private CustomDialog customDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_quiz_main, container, false);
        setView();
        setContent();

        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(speakTv,word);
        return view;
    }

    public void setView() {
        speakTv = (TextView)view.findViewById(R.id.quizmain_storyTv);
        overwatch = (RelativeLayout)view.findViewById(R.id.quizmain_overwatchRelative);
        starcraft = (RelativeLayout)view.findViewById(R.id.quizmain_starCraftRelative);
        diablo = (RelativeLayout)view.findViewById(R.id.quizmain_diabloRelative);
    }


    public void setContent() {
        overwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizLevelFrag("overWatch")).commit();
            }
        });

        starcraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.content_main, new QuizLevelFrag("starCraft")).commit();
            }
        });

        diablo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new CustomDialog(getContext(), "미구현", "미구현된 컨텐츠 입니다.\n업데이트 노트를 확인하세요.", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });

    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);
    }
}
