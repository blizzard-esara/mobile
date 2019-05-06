package com.example.giyeon.blizzard.user.view.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.giyeon.blizzard.R;

public class EggLockFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_egg_lock, container, false);
        Button button = (Button)view.findViewById(R.id.fragfragLock_eggShop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragment().getFragmentManager().beginTransaction().replace(R.id.content_main, new ShopFragment()).commit();
            }
        });
        return view;
    }
}
