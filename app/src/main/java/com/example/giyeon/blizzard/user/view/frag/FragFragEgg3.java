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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.MonsterData;

public class FragFragEgg3 extends Fragment {
    private Context context;
    private View view;

    ImageView monsterEgg;
    @SuppressLint("ValidFragment")
    private FragFragEgg3() {

    }

    private static class LazyHolder {
        public static final FragFragEgg3 INSTANCE = new FragFragEgg3();
    }
    public static FragFragEgg3 getInstance() {
        return LazyHolder.INSTANCE;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_frag_egg3, container, false);
        monsterEgg = (ImageView)view.findViewById(R.id.monsterEgg3);
        if(MonsterData.getInstance().getMonsterList().size() >2) {
            Glide.with(getParentFragment().getActivity()).load(MonsterData.getInstance().getMonsterList().get(2).get("url").toString()).into(monsterEgg);
        } else {
            Glide.with(getParentFragment().getActivity()).load(R.drawable.egg_lock).into(monsterEgg);
        }


        return view;
    }
}
