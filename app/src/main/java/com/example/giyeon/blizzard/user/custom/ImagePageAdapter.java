package com.example.giyeon.blizzard.user.custom;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.giyeon.blizzard.user.view.frag.ImageFragment;

import java.util.List;

public class ImagePageAdapter extends FragmentPagerAdapter {

    List<String> urlList;

    public ImagePageAdapter(FragmentManager fm, List<String> urlList) {
        super(fm);
        this.urlList = urlList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return new ImageFragment(urlList.get(position));
    }

    @Override
    public int getCount() {
        return urlList.size();
    }


}
