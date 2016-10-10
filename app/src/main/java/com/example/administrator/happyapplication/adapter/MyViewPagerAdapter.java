package com.example.administrator.happyapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<View> arrList=new ArrayList<>();

    public MyViewPagerAdapter(Context context) {
        super();
        this.context = context;
    }

    public void addViewToAdapter(View view){
        arrList.add(view);
    }
    @Override
    public int getCount() {
        return arrList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(arrList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrList.get(position));
        return arrList.get(position);
    }
}
