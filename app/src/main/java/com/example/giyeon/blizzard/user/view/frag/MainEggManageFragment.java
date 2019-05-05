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
import com.example.giyeon.blizzard.user.custom.CustomIndicator;
import com.example.giyeon.blizzard.user.custom.SessionPageAdapter;
import com.example.giyeon.blizzard.user.dto.MonsterData;

public class MainEggManageFragment extends Fragment {
    private View view;
    private CustomIndicator customIndicator;
    private ViewPager vpPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);
        initCustomIndicator();

        vpPager = (ViewPager)view.findViewById(R.id.vpPager);
        vpPager.setAdapter(new SessionPageAdapter(getChildFragmentManager()));
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                customIndicator.selectDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
   }

   private void initCustomIndicator() {
       customIndicator = (CustomIndicator)view.findViewById(R.id.customIndicator);
       customIndicator.setItemMargin(15);
       customIndicator.setAnimDuration(300);
       int monsterCnt = MonsterData.getInstance().getMonsterList().size();
       if(monsterCnt <3) {
           customIndicator.createDotPanel(monsterCnt+1, R.mipmap.icon_min_starcraft , R.mipmap.icon_min_overwatch);
       } else {
           customIndicator.createDotPanel(monsterCnt, R.mipmap.icon_min_starcraft , R.mipmap.icon_min_overwatch);
       }
   }


}
