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

public class ExplanationFragment extends Fragment {
    private Context context;
    private View view;
    private TextView speakTv;
    private final String word = "안녕! 여기는 연구소에요!     \n"+
            "당신이 수집한 알과 글들을 확인할수 있는 장소죠.     \n"+
            "보고싶은것을 골라보세요!";

    @SuppressLint("ValidFragment")
    private ExplanationFragment() {

    }

    private static class LazyHolder {
        public static final ExplanationFragment INSTANCE = new ExplanationFragment();
    }
    public static ExplanationFragment getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_explanation, container, false);
        speakTv = (TextView)view.findViewById(R.id.fragExplanation_story);
        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(speakTv,word);
        return view;
    }
}
