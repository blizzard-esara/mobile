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

import com.example.giyeon.blizzard.R;

public class OverwatchQuesFragment extends Fragment {
    private Context context;
    private View view;

    @SuppressLint("ValidFragment")
    private OverwatchQuesFragment() {

    }

    private static class LazyHolder {
        public static final OverwatchQuesFragment INSTANCE = new OverwatchQuesFragment();
    }
    public static OverwatchQuesFragment getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_overwatch_question, container, false);

        return view;
    }
}
