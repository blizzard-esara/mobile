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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.OnBackPressedListener;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.dto.EggData;
import com.example.giyeon.blizzard.user.dto.UserData;

import java.util.Map;

@SuppressLint("ValidFragment")
public class EggStatusFragment extends Fragment {

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
    public EggStatusFragment(Map<String, Object> monsterData) {
        this.monsterData = monsterData;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_egg_status, container, false);

        setView();
        setContent();
        Glide.with(getParentFragment().getActivity()).load(monsterData.get("url").toString()).into(monsterEgg);
        CommonController.getInstance().setFont();

        return view;
    }

     private void setView() {
        monsterEgg = (ImageView)view.findViewById(R.id.fragfrag1_monsterEgg);
        userId = (TextView)view.findViewById(R.id.fragfrag1_userId);
        eggLevel = (TextView)view.findViewById(R.id.fragfrag1_eggLevelTxt);
        eggTypeTxt = (TextView)view.findViewById(R.id.fragfrag1_eggTypeTxt);
        expBar = (SeekBar) view.findViewById(R.id.fragfrag1_expBar);
        expBar.setEnabled(false);

        expTxt = (TextView)view.findViewById(R.id.fragfrag1_expTxt);

        explanationBtn = (Button)view.findViewById(R.id.fragfrag1_explanationBtn);
        adventureStartBtn = (Button)view.findViewById(R.id.fragfrag1_adventureStartBtn);
        produceInfoBtn = (Button)view.findViewById(R.id.fragfrag1_produceInfoBtn);


        monsterEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EggDetailsFragment eggDetailsFragment = new EggDetailsFragment(monsterData.get("monster").toString(),
                                                                                monsterData.get("exp").toString(),
                                                                                monsterData.get("level").toString(),
                                                                                monsterData.get("url").toString());
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, eggDetailsFragment).commit();
            }
        });
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
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, new ExplanationFragment()).commit();
            }
        });

        adventureStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EggData.getInstance().setMainEgg(monsterData.get("monster").toString());
                EggData.getInstance().setMainExp(Integer.parseInt(monsterData.get("exp").toString()));
                EggData.getInstance().setMainLevel(Integer.parseInt(monsterData.get("level").toString()));
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, new MainQuizeFragment()).commit();
            }
        });

        produceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, new ShopFragment()).commit();
            }
        });
    }


}
