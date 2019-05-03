package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.UserController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.custom.CustomGridAdapter;
import com.example.giyeon.blizzard.user.custom.CustomShopDialog;
import com.example.giyeon.blizzard.user.dto.MonsterData;
import com.example.giyeon.blizzard.user.dto.UserData;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopFragment extends Fragment {

    private View view;
    private TextView stroyTv;
    private TextView pointTxt;
    private GridView gridView;
    private final String word = "상점에 어서오시게!\n여기는 새로운 알이나 알에게 필요한 아이템을 구매할수 있는곳이지.\n마음껏 둘러봐요!";
    private CustomShopDialog customShopDialog;
    private CustomDialog customDialog;

    int [] priceArr = {5, 5, 5, 5, 5, 5};
    int[] imArr = {
                R.drawable.starcraft_egg, R.drawable.overwatch_egg,
                R.drawable.diablo_egg, R.drawable.starcraft_egg,
                R.drawable.overwatch_egg, R.drawable.diablo_egg};

        String[] titleArr = {"스타크래프트 알", "오버워치 알",
                "디아블로 알", "스타알2",
                "옵치알2", "디아알2"
        };
    private boolean [] hasItem = {false, false, false, false, false, false};

    private static class LazyHolder {
        public static final ShopFragment INSTANCE = new ShopFragment();
    }

    public static ShopFragment getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_shop, container, false);

        setView();
        setContent();
        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(stroyTv, word);
        return view;
    }

    private void setView() {
        stroyTv = (TextView)view.findViewById(R.id.fragshop_storyTv);
        pointTxt = (TextView)view.findViewById(R.id.fragshop_pointTxt);
        gridView = (GridView)view.findViewById(R.id.fragshop_gridView);

    }

    public void setContent() {
        pointTxt.setText("내 포인트 : "+UserData.getInstance().getMoney());


        for(int i = 0 ; i< MonsterData.getInstance().getMonsterList().size() ; i++) {
            if(MonsterData.getInstance().getMonsterList().get(i).get("monster").equals("starCraft")) hasItem[0] = true;
            if(MonsterData.getInstance().getMonsterList().get(i).get("monster").equals("overWatch")) hasItem[1] = true;
            if(MonsterData.getInstance().getMonsterList().get(i).get("monster").equals("diablo")) hasItem[2] = true;

        }

        gridView.setAdapter(new CustomGridAdapter(getActivity().getApplication(),imArr, titleArr, priceArr, hasItem));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(getActivity().getApplicationContext(), position+"click", Toast.LENGTH_SHORT).show();
                if(!hasItem[position] && UserData.getInstance().getMoney() >= priceArr[position]) {
                    if(position < 4) {


                        customShopDialog = new CustomShopDialog(parent.getContext(), imArr[position], titleArr[position], priceArr[position], new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserController.getInstance().buyItem(titleArr[position], priceArr[position]);
                                customShopDialog.dismiss();

                                customDialog = new CustomDialog(parent.getContext(), "획득!", titleArr[position] + "을 획득하셨습니다!", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        customDialog.dismiss();
                                        setContent();

                                    }
                                });
                                customDialog.show();
                                setContent();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customShopDialog.dismiss();
                            }
                        });
                        customShopDialog.show();

                    } else {
                        customDialog = new CustomDialog(parent.getContext(), "경고!", "미구현 ㅋ",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }

                } else {
                    if(hasItem[position]) {
                        customDialog = new CustomDialog(parent.getContext(), "경고!", "가지고 있는 알의 종류입니다.",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    } else {
                        customDialog = new CustomDialog(parent.getContext(), "경고!", "포인트가 부족합니다. 모험을 하면서 포인트를 획득하세요!",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }

                }
            }
        });

    }






}
