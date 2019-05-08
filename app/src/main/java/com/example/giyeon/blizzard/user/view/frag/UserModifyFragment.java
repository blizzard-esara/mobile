package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;

public class UserModifyFragment extends Fragment implements MainActivity.OnBackPressedListener {

    private View view;
    private EditText email;
    private EditText phone1;
    private EditText phone2;
    private EditText phone3;
    private EditText gender;
    private EditText age;

    private TextView eggCount;
    private TextView alias;
    private TextView quizCount;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_user_modify, container, false);

        return view;
    }

    private void setView() {
       email = (EditText)view.findViewById(R.id.modify_email);
       phone1 = (EditText)view.findViewById(R.id.modify_phone1);
       phone2 = (EditText)view.findViewById(R.id.modify_phone2);
       phone3 = (EditText)view.findViewById(R.id.modify_phone3);

       gender = (EditText)view.findViewById(R.id.modify_gender);
       age = (EditText)view.findViewById(R.id.modify_age);
       eggCount = (TextView)view.findViewById(R.id.modify_eggCount);
       alias = (TextView)view.findViewById(R.id.modify_alias);
       quizCount= (TextView)view.findViewById(R.id.modify_quizCount);

    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);

    }
}
