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

public class ExplanationFragment extends Fragment {
    private Context context;
    private View view;

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

        return view;
    }
}
