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

public class FragFragEgg2 extends Fragment {

    private Context context;
    private View view;
    private ImageView monsterEgg;
    @SuppressLint("ValidFragment")
    private FragFragEgg2() {

    }

    private static class LazyHolder {
        public static final FragFragEgg2 INSTANCE = new FragFragEgg2();
    }


    public static FragFragEgg2 getInstance() {
        return LazyHolder.INSTANCE;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_frag_egg2, container, false);
        monsterEgg = (ImageView)view.findViewById(R.id.monsterEgg2);
        if(MonsterData.getInstance().getMonsterList().size() >1) {
            Glide.with(getParentFragment().getActivity()).load(MonsterData.getInstance().getMonsterList().get(1).get("url").toString()).into(monsterEgg);
        } else {
            Glide.with(getParentFragment().getActivity()).load(R.drawable.egg_lock).into(monsterEgg);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
