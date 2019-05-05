package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
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
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.UserData;

import org.w3c.dom.Text;

@SuppressLint("ValidFragment")
public class EggDetailsFragment extends Fragment {



    private View view;

    String type;
    int exp;
    String level;
    String url;
    String eggAge = "3"; //need update
    String eggStatus = "양호"; //need update
    String eggWeight = "2kg"; //need update


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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.egg_detail_frag, container, false);

        setView();
        setContent();
        return view;
    }
    private void setView() {
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

    private void setContent() {
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
}
