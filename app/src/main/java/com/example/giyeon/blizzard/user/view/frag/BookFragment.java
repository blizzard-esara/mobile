package com.example.giyeon.blizzard.user.view.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giyeon.blizzard.MainActivity;
import com.example.giyeon.blizzard.R;
import com.example.giyeon.blizzard.user.controller.CommonController;
import com.example.giyeon.blizzard.user.controller.NetworkController;
import com.example.giyeon.blizzard.user.custom.BookListAdapter;
import com.example.giyeon.blizzard.user.custom.CustomDialog;
import com.example.giyeon.blizzard.user.dto.Explanation;

import java.util.List;

public class BookFragment extends Fragment implements MainActivity.OnBackPressedListener {

    private View view;
    private TextView storyTv;
    private String word = "여긴 지금까지 자네가 모은 수집글들을 볼수있는 수집글 사전이네!";
    private List<Explanation> explanations;
    BookListAdapter adapter;
    CustomDialog customDialog;

    private ListView listView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_book, container, false);

        setView();

        explanations = NetworkController.getInstance().explanationList();
        CommonController.getInstance().threadStart(storyTv, word);

        setContent();

        customDialog = new CustomDialog(getContext(), "Lock", "모험을 먼저 완수해주세요.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });



        adapter = new BookListAdapter(getContext(), explanations, getFragmentManager());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Explanation item = explanations.get(position);
                if(item.isSolve()) {
                    ((MainActivity)getContext()).showExplanation(item);
                } else {
                    customDialog.show();
                }
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }

    private void setView() {
        storyTv = (TextView) view.findViewById(R.id.book_storyTv);
        listView = (ListView)view.findViewById(R.id.book_listView);


    }
    private void setContent() {




    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.content_main, new ExplanationFragment()).commit();
        MainActivity activity = (MainActivity)getActivity();
        activity.setOnBackPressedListener(null);
    }

}
