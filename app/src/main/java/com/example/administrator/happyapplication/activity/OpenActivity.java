package com.example.administrator.happyapplication.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MybaseActivity;

public class OpenActivity extends MybaseActivity {
    private static final String TAG = "OpenActivity";
    private TextView tv_help;
    private TextView tv_help_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        tv_help= (TextView) findViewById(R.id.tv_help);
//        Log.i(TAG, "onCreate: "+ CommonUtil.getIMEI(OpenActivity.this));
        click();
    }

    private void click(){
        tv_help.setOnClickListener(new View.OnClickListener()						 {
            @Override
            public void onClick(View view) {
                View contentView= LayoutInflater.from(OpenActivity.this).inflate(R.layout.layout_window,null);
                PopupWindow popupWindow=new PopupWindow(contentView,200,200);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(view,-100,10);
//                WindowManager.LayoutParams lp=getWindow().getAttributes();
//                lp.alpha=0.5f;
//                getWindow().setAttributes(lp);
                tv_help_in= (TextView)contentView.findViewById(R.id.tv_window);
                tv_help_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(OpenActivity.this,HelpActivity.class);
                        startActivity(intent);
                    }
                });
//                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        WindowManager.LayoutParams lp=getWindow().getAttributes();
//                        lp.alpha=1f;
//                        getWindow().setAttributes(lp);
//                    }
//                });
            }
        });
    }
}
