package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.OnBackPressedListener;
import com.example.giyeon.blizzard.user.controller.CommonController;

public class ExplanationFragment extends Fragment  implements MainActivity.OnBackPressedListener {
    private View view;
    private TextView speakTv;
    private ImageView collectionIv;

    private final String word = "안녕! 여기는 연구소에요!     \n"+
            "당신이 수집한 알과 글들을 확인할수 있는 장소죠.     \n"+
            "보고싶은것을 골라보세요!";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_explanation, container, false);

        setView();
        setContent();

        CommonController.getInstance().threadStop();
        CommonController.getInstance().threadStart(speakTv,word);
        return view;
    }


    public void setView() {
        speakTv = (TextView)view.findViewById(R.id.fragExplanation_story);
        collectionIv = (ImageView)view.findViewById(R.id.fragExplanation_collection);
    }


    public void setContent() {
        ImageView speechBubble = (ImageView)view.findViewById(R.id.fragexplanation_speechBubbleIv);
        Glide.with(view).load(R.drawable.lab_speech_bubble).into(speechBubble);

        ImageView lab_doctor = (ImageView)view.findViewById(R.id.fragExplanation_lab_doctor);
        Glide.with(view).load(R.drawable.lab_doctor).into(lab_doctor);

        ImageView book = (ImageView)view.findViewById(R.id.fragExplanation_collection);
        Glide.with(view).load(R.drawable.book).into(book);

        ImageView book_dictionary = (ImageView)view.findViewById(R.id.fragExplanation_dictionary);
        Glide.with(view).load(R.drawable.book_dictionary).into(book_dictionary);

        collectionIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonController.getInstance().threadStop();
                getFragmentManager().beginTransaction().replace(R.id.content_main, new CollectionFragment()).commit();
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
