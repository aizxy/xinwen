package com.example.administrator.happyapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> myList=new ArrayList<T>();

    public MyBaseAdapter(Context context) {
        this.context = context;
        this.layoutInflater= LayoutInflater.from(context);
    }

    public List<T> getAdapterData(){
        return myList;
    }

    public void appendDataed(T t,boolean isClearOld){
        if(t==null){
            return;
        }
        if(isClearOld){
            myList.clear();
        }
        myList.add(t);
    }

    public void appendDataed(List<T> t,boolean isClearOld){
        if(t==null){
            return;
        }
        if(isClearOld){
            myList.clear();
        }
        myList.addAll(t);
    }

    public void appendDataTop(T t,boolean isClearOld){
        if(t==null){
            return;
        }
        if(isClearOld){
            myList.clear();
        }
        myList.add(0,t);
    }
    public void appendDataTop(List<T> t,boolean isClearOld){
        if(t==null){
            return;
        }
        if(isClearOld){
            myList.clear();
        }
        myList.addAll(0,t);
    }
    public void isClear(){
        myList.clear();
    }
    public void upDataAdapter(){
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(myList!=null){
            return myList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int i) {
        if(myList==null||myList.size()<0){
            return null;
        }
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getMyView(i,view,viewGroup);
    }
    public abstract View getMyView(int i, View view, ViewGroup viewGroup);
}
