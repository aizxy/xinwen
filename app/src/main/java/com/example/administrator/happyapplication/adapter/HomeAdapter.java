package com.example.administrator.happyapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MyBaseAdapter;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.util.ImageLoader;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class HomeAdapter extends MyBaseAdapter<Summarys> {
    private ImageLoader loader;

    public HomeAdapter(Context context) {
        super(context);
        loader=new ImageLoader(context);
    }

    @Override
    public View getMyView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            view=layoutInflater.inflate(R.layout.layout_home_item,null);
            vh=new ViewHolder();
            vh.iv_home_icon= (ImageView) view.findViewById(R.id.iv_home_icon);
            vh.tv_home_stamp= (TextView) view.findViewById(R.id.tv_home_stamp);
            vh.tv_home_summary= (TextView) view.findViewById(R.id.tv_home_summary);
            vh.tv_home_title= (TextView) view.findViewById(R.id.tv_home_title);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
//        setIcon(getItem(i).getIcon(),vh.iv_home_icon);
        loader.display(getItem(i).getIcon(),vh.iv_home_icon);
        vh.tv_home_title.setText(getItem(i).getTitle());
        vh.tv_home_summary.setText(getItem(i).getSummary());
        vh.tv_home_stamp.setText(getItem(i).getStamp());
        return view;
    }

    class ViewHolder{
        ImageView iv_home_icon;
        TextView tv_home_stamp,tv_home_summary,tv_home_title;
    }

    private void setIcon(final String path,final ImageView im){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==2){
                    Bitmap bm= (Bitmap) msg.obj;
                    im.setImageBitmap(bm);
                }
            }
        };
        new Thread(){
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    BufferedInputStream in=new BufferedInputStream(urlConnection.getInputStream());
                    Bitmap bitmap= BitmapFactory.decodeStream(in);
                    Message msg=new Message();
                    msg.what=2;
                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
