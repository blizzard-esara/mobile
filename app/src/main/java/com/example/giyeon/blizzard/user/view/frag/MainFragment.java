package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.custom.SessionPageAdapter;

public class MainFragment extends Fragment {
    private Context context;
    private View view;

    private ViewPager vpPager;


    @SuppressLint("ValidFragment")
    private MainFragment() {

    }
    private static class LazyHolder {
        public static final MainFragment INSTANCE = new MainFragment();
    }
    public static MainFragment getInstance() {
        return LazyHolder.INSTANCE;
    }
    public void setContext(Context context) {
        this.context = context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        vpPager.setAdapter(new SessionPageAdapter(getChildFragmentManager()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);
        vpPager = (ViewPager)view.findViewById(R.id.vpPager);


        return view;
    }

}
