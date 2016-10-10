package com.example.administrator.happyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.activity.NewsShowActivity;
import com.example.administrator.happyapplication.adapter.HomeAdapter;
import com.example.administrator.happyapplication.adapter.NewTypeAdapter;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.gloable.API;
import com.example.administrator.happyapplication.gloable.Contacts;
import com.example.administrator.happyapplication.model.SubType;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.example.administrator.happyapplication.util.CommonUtil;
import com.example.administrator.happyapplication.util.dbutil.DBTools;
import com.example.administrator.happyapplication.view.HorizontalListView;

import org.json.JSONObject;

import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HorizontalListView horizontalListView;
    private RequestQueue requestQueue;
    private NewTypeAdapter adapter;
    private HomeAdapter adapter1;
    private ListView listView;
    private RequestQueue requestQueue1;

    private DBTools dbTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue1 = Volley.newRequestQueue(getContext());
            adapter = new NewTypeAdapter(getContext());
            dbTools=new DBTools(getActivity());
            horizontalListView = (HorizontalListView) view.findViewById(R.id.horizontalView);
            horizontalListView.setAdapter(adapter);
            horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String subgroup = adapter.getItem(i).getSubgroup();
                    if (subgroup.equals("军事")) {
                        ((HomeActivity) getActivity()).showJunshiFragment();
                    }
                }
            });
            listView = (ListView) view.findViewById(R.id.lv_fragmenthome);
            adapter1 = new HomeAdapter(getContext());
            listView.setAdapter(adapter1);
            listView.setOnItemClickListener(onItemClickListener);
            loadTitleData();
            loadNewsData();
            return view;
    }

    private void sendRequestData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<SubType> list = ParserNews.getNewsType(jsonObject.toString());
                        for(int i=0;i<list.size();i++){
                            dbTools.saveLocalSubType(list.get(i));
                        }
                        adapter.appendDataed(list, true);
                        adapter.upDataAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private void loadNewsData(){
        boolean isNetwork=CommonUtil.isNetworkAvailable(getActivity());
        if(!isNetwork){
            List<Summarys> list=dbTools.getLocalNews();
            if(list!=null&&list.size()>0){
                adapter1.appendDataed(list, true);
                adapter1.upDataAdapter();
            }else{
                Toast.makeText(getActivity(),"没有网络，请检查...",Toast.LENGTH_SHORT).show();
            }
        }else{
            String url1 = "http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=0&stamp=20160828&cnt=20";
            sendRequestDataListView(url1);
        }
    }
    private void loadTitleData(){
        boolean isNetwork=CommonUtil.isNetworkAvailable(getActivity());
        if(!isNetwork){
            List<SubType> list=dbTools.getLocalSubType();
            if(list!=null&&list.size()>0){
                adapter.appendDataed(list, true);
                adapter.upDataAdapter();
            }else{
                Toast.makeText(getActivity(),"没有网络，请检查...",Toast.LENGTH_SHORT).show();
            }
        }else{
            String url = API.NEWS_SORT + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(getContext());
            sendRequestData(url);
        }
    }
    private void sendRequestDataListView(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<Summarys> list = ParserNews.getSummarys(jsonObject.toString());
                        for(int i=0;i<list.size();i++){
                            dbTools.saveLocalNews(list.get(i));
                        }
                        adapter1.appendDataed(list, true);
                        adapter1.upDataAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        requestQueue1.add(jsonObjectRequest);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Bundle bundle = new Bundle();
            Summarys summarys = adapter1.getItem(i);
            bundle.putSerializable("news", summarys);
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class, bundle);
        }
    };
}
