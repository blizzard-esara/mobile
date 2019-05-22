package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.dto.UserData;

public class UserModifyFragment extends Fragment implements MainActivity.OnBackPressedListener {

    private View view;
    private EditText id;
    private EditText email;
    private EditText phone1;
    private EditText phone2;
    private EditText phone3;
    private EditText gender;
    private EditText age;

    private Button modifyBtn;

    private TextView date;
    private TextView eggCount;
    private TextView alias;
    private TextView quizCount;

    private CustomDialog customDialog;

    private String arr[];

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_user_modify, container, false);
        setView();
        setContent();
        return view;
    }

    private void setView() {
        id = (EditText)view.findViewById(R.id.modify_userId);
       email = (EditText)view.findViewById(R.id.modify_email);
       phone1 = (EditText)view.findViewById(R.id.modify_phone1);
       phone2 = (EditText)view.findViewById(R.id.modify_phone2);
       phone3 = (EditText)view.findViewById(R.id.modify_phone3);

       modifyBtn = (Button)view.findViewById(R.id.modify_button);

       gender = (EditText)view.findViewById(R.id.modify_gender);
       age = (EditText)view.findViewById(R.id.modify_age);

       date = (TextView)view.findViewById(R.id.modify_date);
       eggCount = (TextView)view.findViewById(R.id.modify_eggCount);
       alias = (TextView)view.findViewById(R.id.modify_alias);
       quizCount= (TextView)view.findViewById(R.id.modify_quizCount);

    }

    private void setContent() {
        email.setText(UserData.getInstance().getEmail());
        arr = UserData.getInstance().getPhone().split("-");
        if(arr.length == 3) {
            phone1.setText(arr[0]);
            phone2.setText(arr[1]);
            phone3.setText(arr[2]);
        }

        id.setText(UserData.getInstance().getId());
        gender.setText(UserData.getInstance().getGender());
        age.setText(UserData.getInstance().getAge());
        eggCount.setText(NetworkController.getInstance().monsterCount()+"");
        date.setText(UserData.getInstance().getDate()+"");

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(modifyBtn.getText().toString().equals("수정하기")) {
                    phone2.setEnabled(true);
                    phone3.setEnabled(true);
                    email.setEnabled(true);
                    email.setText("");
                    email.setHint(UserData.getInstance().getEmail());
                    phone2.setText("");
                    phone2.setHint(arr[1]);
                    phone3.setText("");
                    phone3.setHint(arr[2]);
                    modifyBtn.setText("저장하기");
                }
                else if(modifyBtn.getText().toString().equals("저장하기")) {
                    if(email.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        customDialog = new CustomDialog(getContext(), "! 오류", "올바른 이메일을 입력해주세요", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    } else {
                        if(phone2.getText().toString().equals("") || phone3.getText().toString().equals("")) {
                            customDialog = new CustomDialog(getContext(), "! 오류", "번호를 다시 입력해주세요.", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        } else {
                            NetworkController.getInstance().modifyUser(email.getText().toString(), phone1.getText().toString()+"-"+phone2.getText().toString()+"-"+phone3.getText().toString());
                            phone2.setEnabled(false);
                            phone3.setEnabled(false);
                            email.setEnabled(false);
                            customDialog = new CustomDialog(getContext(), "! 성공", "수정이 완료되었습니다.", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        }
                    }
                }

            }
        });
    }


    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new MainEggManageFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);

    }
}
