package com.example.administrator.happyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.activity.NewsShowActivity;
import com.example.administrator.happyapplication.adapter.HomeAdapter;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.util.dbutil.DBTools;

import java.util.List;


public class FavoriteFragment extends Fragment implements View.OnClickListener{
    private ListView lv_favorite;
    private HomeAdapter adapter;
    private DBTools dbTools;

    private TextView tv_mine,tv_you;

    private NearFragment nearFragment;
    private MeFragment meFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);
        loadMyContianer();
        tv_mine= (TextView) view.findViewById(R.id.fafa);
        tv_you= (TextView) view.findViewById(R.id.near);
        tv_you.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
        lv_favorite= (ListView) view.findViewById(R.id.lv_favorite);
        adapter=new HomeAdapter(getActivity());
        lv_favorite.setAdapter(adapter);
        lv_favorite.setOnItemClickListener(onItemClickListener);
        dbTools=new DBTools(getContext());
        getData();
        return view;
    }

    private void getData(){
        List<Summarys> list=dbTools.getLocalFavorite();
        adapter.appendDataed(list,true);
        adapter.upDataAdapter();

    }

    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Bundle bundle=new Bundle();
            Summarys summarys=adapter.getItem(i);
            bundle.putSerializable("news",summarys);
            ((HomeActivity)getActivity()).openActivity(NewsShowActivity.class,bundle);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fafa:
                if(nearFragment==null){
                    nearFragment=new NearFragment();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.my_contianer,nearFragment).commit();
                break;
            case R.id.near:
                if(meFragment==null){
                    meFragment=new MeFragment();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.my_contianer,meFragment).commit();
                break;
        }
    }

    private void loadMyContianer(){
        if(nearFragment==null){
            nearFragment=new NearFragment();
        }
        getChildFragmentManager().beginTransaction().replace(R.id.my_contianer,nearFragment).commit();
    }
}
