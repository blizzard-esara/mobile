package com.example.giyeon.blizzard.user.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.giyeon.blizzard.R;

public class CustomDialog extends Dialog {

    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private Button mCenterButton;
    private String mTitle;
    private String mContent;


    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);


        mTitleView = (TextView) findViewById(R.id.txt_title);
        mContentView = (TextView) findViewById(R.id.txt_content);
        mLeftButton = (Button) findViewById(R.id.btn_left);
        mRightButton = (Button) findViewById(R.id.btn_right);
        mCenterButton = (Button) findViewById(R.id.btn_center);

        mTitleView.setText(mTitle);
        mContentView.setText(mContent);


        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
            this.mCenterButton.setVisibility(View.GONE);
            this.mLeftButton.setVisibility(View.VISIBLE);
            this.mRightButton.setVisibility(View.VISIBLE);
            this.mCenterButton.setVisibility(View.INVISIBLE);

        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mCenterButton.setOnClickListener(mLeftClickListener);

            this.mCenterButton.setVisibility(View.VISIBLE);
            this.mLeftButton.setVisibility(View.GONE);
            this.mRightButton.setVisibility(View.GONE);

        } else {

        }
    }


    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomDialog(Context context, String title, String content,
                        View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mTitle = title;
        this.mContent = content;
        this.mLeftClickListener = singleListener;
    }


    public CustomDialog(Context context, String title,
                        String content, View.OnClickListener leftListener,
                        View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mTitle = title;
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }


}
