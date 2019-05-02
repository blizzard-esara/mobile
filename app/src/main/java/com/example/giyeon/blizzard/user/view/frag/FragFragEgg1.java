package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
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
import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.UserData;

public class FragFragEgg1 extends Fragment {

    private Context context;
    private View view;

    ImageView monsterEgg;

    @SuppressLint("ValidFragment")
    private FragFragEgg1() {

    }

    private static class LazyHolder {
        public static final FragFragEgg1 INSTANCE = new FragFragEgg1();
    }
    public static FragFragEgg1 getInstance() {
        return LazyHolder.INSTANCE;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_frag_egg1, container, false);
        monsterEgg = (ImageView)view.findViewById(R.id.monsterEgg1);
        Glide.with(getParentFragment().getActivity()).load(MonsterData.getInstance().getMonsterList().get(0).get("url").toString()).into(monsterEgg);


        return view;
    }


}
