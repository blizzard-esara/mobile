package com.example.giyeon.blizzard.user.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;


public class CustomGameoutDialog extends Dialog {

    private Button btn;
    private Context context;

    private RelativeLayout relativeLayout;
    private TextView textView;
    private View.OnClickListener mClickListener;


    private Drawable drawable;
    private int exp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_gameout_dialog);

        btn = (Button)findViewById(R.id.quizout_btn);
        relativeLayout = (RelativeLayout)findViewById(R.id.quizout_relative);
        textView = (TextView)findViewById(R.id.quizout_exp);

        relativeLayout.setBackground(drawable);
        textView.setText(exp+"pt");
        btn.setOnClickListener(mClickListener);


    }

    public CustomGameoutDialog(Context context, int image, int exp, View.OnClickListener mClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.drawable = context.getResources().getDrawable(image);
        this.exp = exp;
        this.mClickListener = mClickListener;

    }
}

