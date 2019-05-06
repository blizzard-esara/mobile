package com.example.giyeon.blizzard.user.custom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.giyeon.blizzard.user.dto.EggData;
import com.example.giyeon.blizzard.user.view.frag.EggLockFragment;
import com.example.giyeon.blizzard.user.view.frag.EggStatusFragment;

public class SessionPageAdapter extends FragmentPagerAdapter {

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
                return new EggStatusFragment(EggData.getInstance().getMonsterList().get(position));
            case 1:
                if(getCount() == 2) return new EggLockFragment();
                else return new EggStatusFragment(EggData.getInstance().getMonsterList().get(position));
            case 2:
                if(EggData.getInstance().getMonsterList().size() == 2) return new EggLockFragment();
                else return new EggStatusFragment(EggData.getInstance().getMonsterList().get(position));
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
        int cnt = EggData.getInstance().getMonsterList().size();
        if(cnt <3) cnt++;
        return cnt;
    }
}
