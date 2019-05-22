package com.example.giyeon.blizzard.user.view.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.dto.SimpleData;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    View view;
    String imageUrl;
    ImageView imageView;

    @SuppressLint("ValidFragment")
    public ImageFragment (String imageUrl) {
        this.imageUrl = imageUrl;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_image, container, false);
        imageView = (ImageView)view.findViewById(R.id.fraglist_imageView);
        Glide.with(view).load(SimpleData.getInstance().getImageUrl()+imageUrl).into(imageView);

        return view;
    }
}
