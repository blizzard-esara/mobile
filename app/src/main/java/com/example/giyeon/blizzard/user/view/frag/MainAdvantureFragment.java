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
import android.widget.TextView;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.custom.CustomIndicator;

public class MainAdvantureFragment extends Fragment {
    private Context context;
    private View view;
    private TextView speakTv;
    private final String word =  "문제를 풀어서 그 경험치로 알을 성장시킬수 있어! 문제를 골라봐!";

    @SuppressLint("ValidFragment")
    private MainAdvantureFragment() {

    }

    private static class LazyHolder {
        public static final MainAdvantureFragment INSTANCE = new MainAdvantureFragment();
    }
    public static MainAdvantureFragment getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main_adventure, container, false);

        speakTv = (TextView)view.findViewById(R.id.mainAdvanture_storyTv);
        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(speakTv,word);
        return view;
    }
}
