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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class FragFragEgg1 extends Fragment {

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

        monsterData = MonsterData.getInstance().getMonsterList().get(0);
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
                Toast.makeText(getParentFragment().getActivity(), "미구현 컨텐츠", Toast.LENGTH_SHORT).show();
                //CommonEvent.getInstance().mainThreeBtnEvent("produce", monsterData, getParentFragment().getFragmentManager());
            }
        });
    }


}
