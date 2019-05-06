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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.UserData;

@SuppressLint("ValidFragment")
public class EggDetailsFragment extends Fragment implements MainActivity.OnBackPressedListener {

    private View view;

    private String type;
    private int exp;
    private String level;
    private String url;
    private String eggAge = "3"; //need update
    private String eggStatus = "양호"; //need update
    private String eggWeight = "2kg"; //need update


    private SeekBar seekBar;
    private ImageView eggImageIv;
    private TextView eggExpTv;

    private TextView userIdTv;
    private TextView levelTv;
    private TextView typeTv;
    private TextView ageTv;
    private TextView statusTv;
    private TextView weightTv;


    @SuppressLint("ValidFragment")
    public EggDetailsFragment(String eggType, String eggExp, String eggLevel, String eggImageURL) {
        this.type = eggType;
        this.exp = Integer.parseInt(eggExp);
        this.level = eggLevel;
        this.url = eggImageURL;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_egg_detail, container, false);

        setView();
        setContent();
        return view;
    }
    public void setView() {
        seekBar = (SeekBar)view.findViewById(R.id.eggdetail_expBar);
        seekBar.setEnabled(false);
        eggImageIv = (ImageView)view.findViewById(R.id.eggdetail_eggImage);
        eggExpTv = (TextView)view.findViewById(R.id.eggdetail_expTxt);

        userIdTv = (TextView)view.findViewById(R.id.eggdetail_userId);
        levelTv = (TextView)view.findViewById(R.id.eggdetail_level);
        typeTv = (TextView)view.findViewById(R.id.eggdetail_type);
        ageTv = (TextView)view.findViewById(R.id.eggdetail_age);
        statusTv = (TextView)view.findViewById(R.id.eggdetail_status);
        weightTv = (TextView)view.findViewById(R.id.eggdetail_weight);
    }

    public void setContent() {
        Glide.with(view).load(url).into(eggImageIv);
        seekBar.setProgress(exp);
        eggExpTv.setText(exp+"%");

        userIdTv.setText(UserData.getInstance().getId());
        levelTv.setText(level);
        typeTv.setText(type);
        ageTv.setText(eggAge);
        statusTv.setText(eggStatus);
        weightTv.setText(eggWeight);
    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);
    }

}
