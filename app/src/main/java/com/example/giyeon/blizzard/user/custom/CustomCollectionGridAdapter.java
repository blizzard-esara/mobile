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
import com.example.giyeon.blizzard.user.dto.SimpleData;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class CustomCollectionGridAdapter extends BaseAdapter {

    Context context;
    List<Map<String, Object>> mapList;

    public CustomCollectionGridAdapter (Context context, List<Map<String, Object>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }
    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater;
        if(view == null) {

            inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.gridview_item_collection, parent, false);
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.collection_item_imageView);
        TextView textView = (TextView)view.findViewById(R.id.collection_item_text);

        Glide.with(view).load(SimpleData.getInstance().getImageUrl() + mapList.get(position).get("url")).into(imageView);

        textView.setText(mapList.get(position).get("monster_name").toString());


        return view;
    }
}
