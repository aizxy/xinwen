package com.example.administrator.happyapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.happyapplication.activity.AnimationActivity;
import com.example.administrator.happyapplication.adapter.MyViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp_main;
    private MyViewPagerAdapter adapter;
    private ImageView images[]=new ImageView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp=getSharedPreferences("main", Context.MODE_PRIVATE);
        boolean isFirst=sp.getBoolean("isFirst",true);
        if(!isFirst){
            Intent intent =new Intent(MainActivity.this,AnimationActivity.class);
            startActivity(intent);
        }else{
            savePreferences();
            setContentView(R.layout.activity_main);
            initUI();
            initData();
            setAdapter();
        }
    }

    private void savePreferences(){
        SharedPreferences sp=getSharedPreferences("main",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isFirst",false);
        editor.commit();
    }

    private void initUI(){
        images[0]= (ImageView) findViewById(R.id.iv_main_a41);
        images[1]= (ImageView) findViewById(R.id.iv_main_a42);
        images[2]= (ImageView) findViewById(R.id.iv_main_a43);
        images[3]= (ImageView) findViewById(R.id.iv_main_a44);
        images[1].setAlpha(0.2f);
        images[2].setAlpha(0.2f);
        images[3].setAlpha(0.2f);
        vp_main= (ViewPager) findViewById(R.id.vp_mian);
        adapter=new MyViewPagerAdapter(this);
    }
    private void initData(){
        vp_main.setAdapter(adapter);
        vp_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<images.length;i++){
                    images[i].setAlpha(0.5f);
                }
                images[position].setAlpha(1f);
                if(position==3){
                    Intent intent=new Intent(MainActivity.this, AnimationActivity.class);
                    startActivity(intent);
//                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setAdapter(){
        ImageView imageView=null;
        imageView= (ImageView) getLayoutInflater().inflate(R.layout.layout_main_view,null);
        imageView.setBackgroundResource(R.drawable.bd);
        adapter.addViewToAdapter(imageView);
        imageView= (ImageView) getLayoutInflater().inflate(R.layout.layout_main_view,null);
        imageView.setBackgroundResource(R.drawable.wy);
        adapter.addViewToAdapter(imageView);
        imageView= (ImageView) getLayoutInflater().inflate(R.layout.layout_main_view,null);
        imageView.setBackgroundResource(R.drawable.small);
        adapter.addViewToAdapter(imageView);
        imageView= (ImageView) getLayoutInflater().inflate(R.layout.layout_main_view,null);
        imageView.setBackgroundResource(R.drawable.welcome);
        adapter.addViewToAdapter(imageView);
        adapter.notifyDataSetChanged();
    }
}
