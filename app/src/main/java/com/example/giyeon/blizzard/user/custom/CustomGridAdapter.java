package com.example.giyeon.blizzard.user.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;


public class CustomGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private int [] imArr;
    private String [] titleArr;
    private int [] priceArr;
    private boolean [] hasItem;

    public CustomGridAdapter(Context context, int[] imArr, String[] titleArr, int[] priceArr, boolean [] hasItem) {
        this.imArr = imArr;
        this.titleArr = titleArr;
        this.priceArr = priceArr;
        this.context = context;
        this.hasItem = hasItem;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imArr.length;
    }

    @Override
    public Object getItem(int position) {
        return imArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater;
        if(view == null) {

           inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.girdview_item_shop, parent, false);
        }

        ImageView imageView =(ImageView)view.findViewById(R.id.item_imageView);
        TextView textView = (TextView)view.findViewById(R.id.grid_item_textView);
        TextView priceText = (TextView)view.findViewById(R.id.grid_item_priceTextView);


        Glide.with(view).load(imArr[position]).into(imageView);
        textView.setText(titleArr[position]);
        priceText.setText(priceArr[position]+"pt");




        return view;
    }
}
