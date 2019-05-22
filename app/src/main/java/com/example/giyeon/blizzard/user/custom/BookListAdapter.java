package com.example.giyeon.blizzard.user.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.dto.Explanation;

import java.util.List;


public class BookListAdapter extends BaseAdapter {

    List<Explanation> explanations;
    Context context;
    CustomDialog customDialog;
    FragmentManager fm;

    public BookListAdapter(Context context, List<Explanation> explanations, FragmentManager fm) {
        this.context = context;
        this.explanations = explanations;
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return explanations.size();
    }

    @Override
    public Object getItem(int position) {
        return explanations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ContentsViewHolder viewHolder;

        Explanation item = explanations.get(position);

        if(view == null) {
            viewHolder = new ContentsViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listview_item_book, parent, false);

            viewHolder.relativeLayout = (RelativeLayout)view.findViewById(R.id.list_relative);
            viewHolder.levelText = (TextView)view.findViewById(R.id.list_item_level);
            viewHolder.titleText = (TextView)view.findViewById(R.id.list_item_title);
            viewHolder.lockIcon = (ImageView)view.findViewById(R.id.list_lockIcon);
            viewHolder.icon = (ImageView)view.findViewById(R.id.list_item_icon);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ContentsViewHolder) view.getTag();
        }

        if(item.isSolve()) {
            viewHolder.titleText.setVisibility(View.VISIBLE);
            viewHolder.titleText.setText(item.getTitle());
            viewHolder.levelText.setVisibility(View.VISIBLE);
            String qLv = item.getLevel();

            if(qLv.equals("easy"))
                viewHolder.levelText.setTextColor(Color.parseColor("#127218"));
            else if(qLv.equals("hard"))
                viewHolder.levelText.setTextColor(Color.parseColor("#384EC5"));

            viewHolder.levelText.setText(item.getLevel());
            viewHolder.lockIcon.setVisibility(View.GONE);
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));

            switch (item.getTheme()) {
                case "overWatch" :
                    Glide.with(view).load(R.drawable.icon_overwatch).into(viewHolder.icon);
                    break;

                case "starCraft" :
                    Glide.with(view).load(R.drawable.icon_starcraft).into(viewHolder.icon);
                    break;

                case "diablo" :
                    Glide.with(view).load(R.drawable.icon_diablo).into(viewHolder.icon);
                    break;
            }


            //viewHolder.relativeLayout.setOnClickListener(null);

        } else {
            viewHolder.titleText.setVisibility(View.GONE);
            viewHolder.levelText.setVisibility(View.GONE);
            viewHolder.lockIcon.setVisibility(View.VISIBLE);
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#CEDDE3"));
            Glide.with(view).load(R.drawable.book_content_lock).into(viewHolder.lockIcon);
            viewHolder.icon.setVisibility(View.GONE);

            /*
            customDialog = new CustomDialog(context, "Lock", "모험을 먼저 완수해주세요.", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });

            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.show();
            }
            });
            */

        }

        return view;
    }

    private class ContentsViewHolder {

        RelativeLayout relativeLayout;
        TextView titleText;
        TextView levelText;
        ImageView lockIcon;
        ImageView icon;
    }


}
