package com.example.administrator.happyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;


public class LeftFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rl_left_news,rl_left_pic,rl_left_favorit,rl_left_talk,rl_left_local;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_left, container, false);
        rl_left_favorit= (RelativeLayout) view.findViewById(R.id.rl_left_favorit);
        rl_left_news= (RelativeLayout) view.findViewById(R.id.rl_left_news);
        rl_left_pic= (RelativeLayout) view.findViewById(R.id.rl_left_pic);
        rl_left_talk= (RelativeLayout) view.findViewById(R.id.rl_left_talk);
        rl_left_local= (RelativeLayout) view.findViewById(R.id.rl_left_local);

        rl_left_favorit.setOnClickListener(this);
        rl_left_news.setOnClickListener(this);
        rl_left_pic.setOnClickListener(this);
        rl_left_talk.setOnClickListener(this);
        rl_left_local.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_left_news:
                ((HomeActivity)getActivity()).showDifHomeFragment();
                setBgColor(rl_left_news);
                break;
            case R.id.rl_left_favorit:
                ((HomeActivity)getActivity()).showFavoriteFragment();
                setBgColor(rl_left_favorit);
                break;
            case R.id.rl_left_talk:
                setBgColor(rl_left_talk);
                break;
            case R.id.rl_left_local:
                setBgColor(rl_left_local);
                break;
            case R.id.rl_left_pic:
                ((HomeActivity)getActivity()).showPicFragment();
                setBgColor(rl_left_pic);
                break;
        }
    }

    private void setBgColor(RelativeLayout r){
        rl_left_news.setBackgroundColor(0xc3f3fd);
        rl_left_pic.setBackgroundColor(0xc3f3fd);
        rl_left_talk.setBackgroundColor(0xc3f3fd);
        rl_left_local.setBackgroundColor(0xc3f3fd);
        rl_left_favorit.setBackgroundColor(0xc3f3fd);
        r.setBackgroundColor(0x33c85555);
    }
}
