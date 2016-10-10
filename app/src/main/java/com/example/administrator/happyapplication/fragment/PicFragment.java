package com.example.administrator.happyapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.adapter.GridAdapter;
import com.example.administrator.happyapplication.adapter.MyViewPagerAdapter;
import com.example.administrator.happyapplication.bean.BitmapClass;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.util.ImageLoader;
import com.example.administrator.happyapplication.util.dbutil.DBTools;

import java.util.ArrayList;
import java.util.List;


public class PicFragment extends Fragment {
    private ViewPager vp_pic;
    private MyViewPagerAdapter adapter;
    private TextView tv_picNum,tv_picsum;
    ImageLoader imageLoader;

    private GridAdapter adapter1;
    private GridView gridView;
    List<BitmapClass> gridList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pic, container, false);
        tv_picsum= (TextView) view.findViewById(R.id.tv_picsum);
        tv_picNum= (TextView) view.findViewById(R.id.tv_picNum);
        vp_pic= (ViewPager) view.findViewById(R.id.vp_pic);
        adapter=new MyViewPagerAdapter(getActivity());
        vp_pic.setAdapter(adapter);
        imageLoader=new ImageLoader(getActivity());
        setAdapter();

        gridView= (GridView) view.findViewById(R.id.gv_pic);
        adapter1=new GridAdapter(getActivity());
        setgridAdapter();
        gridView.setAdapter(adapter1);
        vp_pic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_picNum.setText("00"+(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setAdapter(){

        DBTools dbTools=new DBTools(getActivity());
        List<Summarys> list= dbTools.getLocalNews();
        int sum=list.size();
        tv_picsum.setText(sum+"张");
        for(int i=0;i<list.size();i++){
            Summarys summarys=list.get(i);
            String path=summarys.getIcon();
            ImageView imageView;
            imageView= (ImageView) getLayoutInflater(getArguments()).inflate(R.layout.layout_main_view,null);
            Bitmap bitmap=imageLoader.display(path,imageView);
            imageView.setImageBitmap(bitmap);
            adapter.addViewToAdapter(imageView);
        }
        adapter.notifyDataSetChanged();
    }

    private void setgridAdapter(){
        DBTools dbTools=new DBTools(getActivity());
        List<Summarys> list= dbTools.getLocalNews();
        for(int i=0;i<list.size();i++){
            Summarys summarys=list.get(i);
            String path=summarys.getIcon();
            ImageView imageView;
            imageView= (ImageView) getLayoutInflater(getArguments()).inflate(R.layout.layout_main_view,null);
            Bitmap bitmap=imageLoader.display(path,imageView);
            BitmapClass bitmapClass =new BitmapClass(bitmap);
            gridList.add(bitmapClass);
        }
        adapter1.appendDataed(gridList,true);
        adapter1.upDataAdapter();
    }
}
