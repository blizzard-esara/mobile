package com.example.giyeon.blizzard.user.view.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.CustomCollectionGridAdapter;


import java.util.List;
import java.util.Map;

public class CollectionFragment extends Fragment {

    View view;
    private String word = "자네가 지금까지 수집한 몬스터를 볼수있다네.\n 더 열심히 하시게";
    private GridView gridView;
    private CustomCollectionGridAdapter adapter;
    private TextView storyTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_collection, container, false);


        storyTv = (TextView)view.findViewById(R.id.fragcollection_storyTv);
        gridView = (GridView)view.findViewById(R.id.fragcollection_gridView);
        List<Map<String, Object>> listMap = NetworkController.getInstance().monsterCollection();

        adapter = new CustomCollectionGridAdapter(getActivity().getApplication(), listMap);

        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        CommonController.getInstance().threadStart(storyTv, word);

        return view;
    }
}
