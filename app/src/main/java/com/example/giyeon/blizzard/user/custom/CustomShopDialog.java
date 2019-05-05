package com.example.giyeon.blizzard.user.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;

import org.w3c.dom.Text;

public class CustomShopDialog extends Dialog {

    private ImageView eggImage;
    private TextView eggName;
    private TextView eggPrice;
    private Button buyOkBtn;
    private Button buyNoBtn;
    private Context context;

    private View.OnClickListener buyOkClickListener;
    private View.OnClickListener buyNoClickListener;

    private int imageResource;
    private String eggNameResource;
    private int eggPriceResource;

    public CustomShopDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_shop_dialog);

        eggImage = (ImageView)findViewById(R.id.shopdialog_eggImage);
        eggName = (TextView) findViewById(R.id.shopdialog_eggName);
        eggPrice = (TextView) findViewById(R.id.shopdialog_point);

        buyOkBtn = (Button)findViewById(R.id.btn_buyYes);
        buyNoBtn = (Button)findViewById(R.id.btn_buyNo);

        if(buyOkClickListener != null && buyNoClickListener != null) {
            Glide.with(context).load(imageResource).into(eggImage);
            eggName.setText(eggNameResource);
            eggPrice.setText(eggPriceResource+"");
            buyOkBtn.setOnClickListener(buyOkClickListener);
            buyNoBtn.setOnClickListener(buyNoClickListener);
        }


    }

    public CustomShopDialog(Context context, int image, String name, int price, View.OnClickListener buyOkClickListener, View.OnClickListener buyNoClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.imageResource = image;
        this.eggNameResource = name;
        this.eggPriceResource = price;

        this.buyOkClickListener = buyOkClickListener;
        this.buyNoClickListener = buyNoClickListener;
    }
}
