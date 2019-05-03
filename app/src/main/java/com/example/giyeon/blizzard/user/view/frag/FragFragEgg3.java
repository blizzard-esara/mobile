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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.CommonEvent;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.UserData;

import java.util.Map;

public class FragFragEgg3 extends Fragment {

    private Context context;
    private Map<String, Object> monsterData;
    private View view;

    private ImageView monsterEgg;
    private TextView userId;
    private TextView eggLevel;
    private TextView eggTypeTxt;
    private SeekBar expBar;
    private TextView expTxt;

    private Button explanationBtn;
    private Button adventureStartBtn;
    private Button produceInfoBtn;


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
        monsterEgg = (ImageView)view.findViewById(R.id.fragfrag3_monsterEgg);
        if(MonsterData.getInstance().getMonsterList().size() >2) {
            view = inflater.inflate(R.layout.frag_frag_egg3, container, false);
            monsterData = MonsterData.getInstance().getMonsterList().get(2);
            setView();
            setContent();
            Glide.with(getParentFragment().getActivity()).load(monsterData.get("url").toString()).into(monsterEgg);
            CommonController.getInstance().setFont();
        } else {
            view = inflater.inflate(R.layout.frag_frag_egg_lock, container, false);
            Button button = (Button)view.findViewById(R.id.fragfragLock_eggShop);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonEvent.getInstance().mainThreeBtnEvent(getParentFragment().getFragmentManager());
                }
            });
        }


        return view;
    }

    private void setView() {
        monsterEgg = (ImageView)view.findViewById(R.id.fragfrag3_monsterEgg);
        userId = (TextView)view.findViewById(R.id.fragfrag3_userId);
        eggLevel = (TextView)view.findViewById(R.id.fragfrag3_eggLevelTxt);
        eggTypeTxt = (TextView)view.findViewById(R.id.fragfrag3_eggTypeTxt);
        expBar = (SeekBar) view.findViewById(R.id.fragfrag3_expBar);
        expBar.setEnabled(false);

        expTxt = (TextView)view.findViewById(R.id.fragfrag3_expTxt);

        explanationBtn = (Button)view.findViewById(R.id.fragfrag3_explanationBtn);
        adventureStartBtn = (Button)view.findViewById(R.id.fragfrag3_adventureStartBtn);
        produceInfoBtn = (Button)view.findViewById(R.id.fragfrag3_produceInfoBtn);
    }

    public void setContent() {
        userId.setText(UserData.getInstance().getId());
        eggLevel.setText("알단계 : "+monsterData.get("level")+"단계");
        eggTypeTxt.setText("알타입 : "+monsterData.get("monster"));
        expBar.setProgress(Integer.parseInt(monsterData.get("exp").toString()));
        expTxt.setText(monsterData.get("exp")+"%");

        explanationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonEvent.getInstance().mainThreeBtnEvent("explanation", monsterData, getParentFragment().getFragmentManager());
            }
        });

        adventureStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonEvent.getInstance().mainThreeBtnEvent("adventure", monsterData, getParentFragment().getFragmentManager());
            }
        });

        produceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonEvent.getInstance().mainThreeBtnEvent("shop", monsterData, getParentFragment().getFragmentManager());
            }
        });
    }
}
