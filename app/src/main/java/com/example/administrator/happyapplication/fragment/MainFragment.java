package com.example.administrator.happyapplication.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;

public class MainFragment extends Fragment {
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showLoadingDialog(getContext(),"加载数据。。。。",true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void showLoadingDialog(Context context, String msg, boolean cancel){
        View view= LayoutInflater.from(context).inflate(R.layout.layout_loading_item,null);
        LinearLayout layout= (LinearLayout) view.findViewById(R.id.rl_main_f);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_main_dialog);
        TextView tv= (TextView) view.findViewById(R.id.tv_main_wel);
        Animation roateAnim= AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        iv.setAnimation(roateAnim);
        if(null!=msg){
            tv.setText(msg);
        }
        dialog =new Dialog(context,R.style.loading_dialog);
        dialog.setContentView(layout,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(cancel);
        dialog.show();
    }
    public void cancelDialog(){
        if(null!=dialog){
            dialog.dismiss();
        }
    }


}
