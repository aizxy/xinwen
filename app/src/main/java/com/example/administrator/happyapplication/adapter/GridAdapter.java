package com.example.administrator.happyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MyBaseAdapter;
import com.example.administrator.happyapplication.bean.BitmapClass;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class GridAdapter extends MyBaseAdapter<BitmapClass> {
    LayoutInflater layoutInflater=null;

    public GridAdapter(Context context) {
        super(context);
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            vh=new ViewHolder();
            view=layoutInflater.inflate(R.layout.layout_grid_view,null);
            vh.imageView= (ImageView) view.findViewById(R.id.gridview_pic);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.imageView.setImageBitmap(getItem(i).getBitmap());
        return view;
    }

    class ViewHolder{
        ImageView imageView;
    }
}
