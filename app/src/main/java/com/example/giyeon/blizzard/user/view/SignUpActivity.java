package com.example.giyeon.blizzard.user.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.dto.SimpleData;

public class SignUpActivity extends AppCompatActivity {

    EditText idTxt, pwTxt, pwCheckTxt, emailTxt, phone1, phone2, phone3, ageTxt;
    RadioGroup genderRadio;
    RadioButton checkedRadioBtn;
    CheckBox subscription;
    Button checkIdBtn, submitBtn, cancelBtn;
    Context context;
    AlertDialog.Builder builder;
    TextView pwCheckTv, emailCheckTv, idCheckTv;


    private boolean boolId = false, boolPw = false, boolEmail = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        this.context = this;
        setAlertDialog();


        mappingLayout();
    }



    private void mappingLayout() {
        idTxt = (EditText)findViewById(R.id.idTxt);
        pwTxt = (EditText)findViewById(R.id.pwTxt);
        pwTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { checkPassword(); }
        });

        pwCheckTxt = (EditText)findViewById(R.id.pwCheckTxt);
        pwCheckTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) { checkPassword(); }
        });

        emailTxt = (EditText)findViewById(R.id.emailTxt);
        emailTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(emailTxt.getText().toString().equals("")) {
                    emailCheckTv.setText("올바른 email을 입력해주세요.");
                    emailCheckTv.setTextColor(Color.parseColor("#df0000"));
                    boolEmail = false;
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt.getText().toString()).matches()) {
                    emailCheckTv.setText("올바른 email을 입력해주세요.");
                    emailCheckTv.setTextColor(Color.parseColor("#df0000"));
                    boolEmail = false;
                } else {
                    emailCheckTv.setText("올바른 email입니다.");
                    emailCheckTv.setTextColor(Color.parseColor("#00a721"));
                    boolEmail = true;
                }
            }
        });

        phone1 = (EditText)findViewById(R.id.phone_1);
        phone2 = (EditText)findViewById(R.id.phone_2);
        phone3 = (EditText)findViewById(R.id.phone_3);
        ageTxt = (EditText)findViewById(R.id.ageTxt);


        idCheckTv = (TextView)findViewById(R.id.idCheckTv);
        pwCheckTv = (TextView)findViewById(R.id.pwCheckTv);
        emailCheckTv = (TextView) findViewById(R.id.emailCheckTv);

        genderRadio = (RadioGroup)findViewById(R.id.genderRadioBtn);
        subscription = (CheckBox)findViewById(R.id.subscriptionCheckBox);

        checkIdBtn = (Button)findViewById(R.id.checkIdBtn);

        checkIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idTxt.getText().toString().equals("")) {
                    Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if(NetworkController.getInstance().checkOverlapId(idTxt.getText().toString())) {
                        idCheckTv.setText("사용가능한 아이디 입니다.");
                        idCheckTv.setTextColor(Color.parseColor("#00a721"));
                        boolId = true;
                        //builder.show();
                    } else {
                        Toast.makeText(context, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        idCheckTv.setText("이미 존재하는 아이디입니다.");
                        idCheckTv.setTextColor(Color.parseColor("#df0000"));
                        boolId = false;
                    }
                }
            }
        });


        submitBtn = (Button)findViewById(R.id.signUpSubmitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!boolId) Toast.makeText(context, "ID 중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    if(!boolPw) Toast.makeText(context, "PW를 일치시켜주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        if(!boolEmail) Toast.makeText(context, "Email 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        else {
                            if(ageTxt.getText().toString().equals(""))
                                Toast.makeText(context, "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            else {
                                if(genderRadio.getCheckedRadioButtonId() == -1)
                                    Toast.makeText(context, "성별은 선택해주세요.", Toast.LENGTH_SHORT).show();
                                else {

                                    checkedRadioBtn = (RadioButton)findViewById(genderRadio.getCheckedRadioButtonId());

                                   if(NetworkController.getInstance().signUp(idTxt.getText().toString(),
                                                                        pwTxt.getText().toString(),
                                                                        emailTxt.getText().toString(),
                                                                        phone1.getText().toString()+"-"+phone2.getText().toString()+"-"+phone3.getText().toString(),
                                                                        ageTxt.getText().toString(),
                                                                        checkedRadioBtn.getText().toString(),
                                                                        subscription.isChecked())) {

                                       Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                       finish();
                                   }
                                }
                            }
                        }
                    }
                }
            }
        });



        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    private void checkPassword() {
        if(pwTxt.getText().toString().equals("") || pwTxt.getText().toString().equals("")) {
            pwCheckTv.setText("비밀번호를 입력해주세요.");
            pwCheckTv.setTextColor(Color.parseColor("#df0000"));
            boolPw = false;
        } else {
            if(pwTxt.getText().toString().equals(pwCheckTxt.getText().toString())) {
                pwCheckTv.setText("비밀번호가 일치합니다.");
                pwCheckTv.setTextColor(Color.parseColor("#00a721"));
                boolPw = true;
            } else {
                pwCheckTv.setText("비밀번호가 일치하지 않습니다.");
                pwCheckTv.setTextColor(Color.parseColor("#df0000"));
                boolPw = false;
            }
        }
    }

    private void setAlertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("사용가능한 아이디 입니다.");
        builder.setMessage("사용하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idTxt.setEnabled(false);
                        checkIdBtn.setEnabled(false);
                        idCheckTv.setText("사용가능한 아이디 입니다.");
                        idCheckTv.setTextColor(Color.parseColor("#00a721"));
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idTxt.setText("");
                        idCheckTv.setText("아이디를 입력해주세요.");
                        idCheckTv.setTextColor(Color.parseColor("#df0000"));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - SimpleData.getInstance().backPressedTime;

        if (0 <= intervalTime && SimpleData.getInstance().FINISH_INTERVAL_TIME >= intervalTime) {
            finish();
        } else {
            SimpleData.getInstance().backPressedTime = tempTime;
            Toast.makeText(context, "회원가입을 종료하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
