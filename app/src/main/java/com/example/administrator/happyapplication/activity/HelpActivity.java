package com.example.administrator.happyapplication.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.fragment.MainFragment;
import com.example.administrator.happyapplication.fragment.MeFragment;
import com.example.administrator.happyapplication.fragment.NearFragment;

public class HelpActivity extends FragmentActivity implements View.OnClickListener{
    private RadioButton btnMain,btnMe,btnNear;
    private MainFragment mainFragment;
    private MeFragment meFragment;
    private NearFragment nearFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        btnMain= (RadioButton) findViewById(R.id.radio1);
        btnMe= (RadioButton) findViewById(R.id.radio2);
        btnNear= (RadioButton) findViewById(R.id.radio3);
        loadFragment();
        btnMain.setOnClickListener(this);
        btnMe.setOnClickListener(this);
        btnNear.setOnClickListener(this);
    }

    private void loadFragment(){
        mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.rl_radio,mainFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radio1:
                if(mainFragment==null) {
                    mainFragment = new MainFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_radio,mainFragment).commit();
                btnMain.setBackgroundResource(R.color.colorGreen);
                btnMe.setBackgroundResource(R.color.colorWhite);
                btnNear.setBackgroundResource(R.color.colorWhite);
                break;
            case R.id.radio2:
                if(meFragment==null) {
                    meFragment = new MeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_radio,meFragment).commit();
                btnMain.setBackgroundResource(R.color.colorWhite);
                btnMe.setBackgroundResource(R.color.colorGreen);
                btnNear.setBackgroundResource(R.color.colorWhite);
                break;
            case R.id.radio3:
                if(nearFragment==null) {
                    nearFragment = new NearFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_radio,nearFragment).commit();
                btnNear.setBackgroundResource(R.color.colorGreen);
                btnMe.setBackgroundResource(R.color.colorWhite);
                btnMain.setBackgroundResource(R.color.colorWhite);
                break;
        }
    }
}
