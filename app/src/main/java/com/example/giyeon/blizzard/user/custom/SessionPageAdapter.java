package com.example.giyeon.blizzard.user.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.view.frag.FragFragEgg1;
import com.example.giyeon.blizzard.user.view.frag.FragFragEgg2;
import com.example.giyeon.blizzard.user.view.frag.FragFragEgg3;

public class SessionPageAdapter extends FragmentPagerAdapter {

    Context context;
    public SessionPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {


        return super.getItemPosition(object);
    }

    @Override // Fragment 분할 및 실행 OK
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return FragFragEgg1.getInstance();
            case 1:
                return FragFragEgg2.getInstance();
            case 2:
                return FragFragEgg3.getInstance();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override // 위에 숫자 생성 OK
    public int getCount() {
        // Show 4 total pages.
        int cnt = MonsterData.getInstance().getMonsterList().size();
        if(cnt <3) cnt++;
        return cnt;
    }
}
