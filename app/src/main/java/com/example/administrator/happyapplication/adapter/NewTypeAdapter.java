package com.example.administrator.happyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MyBaseAdapter;
import com.example.administrator.happyapplication.model.SubType;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class NewTypeAdapter extends MyBaseAdapter<SubType> {

    public NewTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            view=layoutInflater.inflate(R.layout.layout_newtype_item,null);
            vh=new ViewHolder();
            vh.tv_newstype_item= (TextView) view.findViewById(R.id.tv_newstype_item);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        String subGroup=getItem(i).getSubgroup();
        vh.tv_newstype_item.setText(subGroup);
        return view;
    }

    class ViewHolder{
        TextView tv_newstype_item;
    }
}
