package com.example.administrator.happyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MyBaseAdapter;
import com.example.administrator.happyapplication.model.Ties;
import com.example.administrator.happyapplication.util.ImageLoader;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class TiesAdapter extends MyBaseAdapter<Ties> {
    private ImageLoader imageLoader;

    public TiesAdapter(Context context) {
        super(context);
        imageLoader=new ImageLoader(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_ties_item, null);
            vh = new ViewHolder();
            vh.iv_ties_pic = (ImageView) view.findViewById(R.id.iv_ties_pic);
            vh.tv_ties_content= (TextView) view.findViewById(R.id.tv_ties_content);
            vh.tv_ties_name= (TextView) view.findViewById(R.id.tv_ties_name);
            vh.tv_ties_time= (TextView) view.findViewById(R.id.tv_ties_time);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_ties_time.setText(getItem(i).getStamp());
        vh.tv_ties_name.setText(getItem(i).getUid());
        vh.tv_ties_content.setText(getItem(i).getContent());
        imageLoader.display(getItem(i).getPortrait(),vh.iv_ties_pic);
        return view;
    }

    class ViewHolder{
        TextView tv_ties_name,tv_ties_time,tv_ties_content;
        ImageView iv_ties_pic;
    }

}
