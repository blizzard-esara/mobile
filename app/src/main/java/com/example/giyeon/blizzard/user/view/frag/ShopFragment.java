package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giyeon.blizzard.R;

public class ShopFragment extends Fragment {

    private View view;

    private static class LazyHolder {
        public static final ShopFragment INSTANCE = new ShopFragment();
    }

    public static ShopFragment getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_shop, container, false);
        return view;
    }




}
