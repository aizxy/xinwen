package com.example.administrator.happyapplication.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.base.MybaseActivity;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.gloable.API;
import com.example.administrator.happyapplication.gloable.Contacts;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.example.administrator.happyapplication.util.CommonUtil;
import com.example.administrator.happyapplication.util.dbutil.DBTools;

import org.json.JSONObject;

public class NewsShowActivity extends MybaseActivity implements View.OnClickListener {
    private static final String TAG = "NewsShowActivity";
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView iv_back;
    private ImageView iv_favorite;
    private PopupWindow popupWindow;
    private DBTools dbTools;
    private Summarys summarys;

    private RequestQueue requestQueue;

    private TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CommonUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.no_network);
        } else {
            setContentView(R.layout.activity_news_show);
            requestQueue= Volley.newRequestQueue(this);
            tv_number= (TextView) findViewById(R.id.tv_newsshow_ties);
            tv_number.setOnClickListener(this);
            progressBar = (ProgressBar) findViewById(R.id.pb_newsshow);
            iv_back = (ImageView) findViewById(R.id.iv_newsshow_back);
            iv_back.setOnClickListener(this);
            webView = (WebView) findViewById(R.id.wv_newsshow);
            iv_favorite = (ImageView) findViewById(R.id.iv_newsshow_favorite);
            iv_favorite.setOnClickListener(this);
            dbTools=new DBTools(this);

            Bundle bundle = getIntent().getExtras();
            summarys = (Summarys) bundle.getSerializable("news");
            String url= API.USER_TIES+"ver="+ Contacts.VER+"&nid="+summarys.getNid();
            sendRequestData(url);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//关键点
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
            webSettings.setSupportZoom(true); // 支持缩放

            webSettings.setLoadWithOverviewMode(true);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mDensity = metrics.densityDpi;
            Log.d("maomao", "densityDpi = " + mDensity);
            if (mDensity == 240) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if (mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            }

            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            WebChromeClient client = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            };
            webView.setWebChromeClient(client);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webView.loadUrl(summarys.getLink());
            showPopupWindow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_newsshow_back:
                finish();
                break;
            case R.id.iv_newsshow_favorite:
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else if(popupWindow!=null){
                    popupWindow.showAsDropDown(iv_favorite,0,2);
                }
                break;
            case R.id.tv_newsshow_ties:
                Bundle bundle=new Bundle();
                bundle.putSerializable("ties",summarys);
                openActivity(TiesActivity.class, bundle);
                break;
        }
    }

    private void showPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_popup_favorite, null);
        final TextView tv = (TextView) view.findViewById(R.id.tv_pupop_favorite);
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                boolean isSaved=dbTools.saveLocalFavorite(summarys);
                if(isSaved){
                    tv.setAlpha(0.5f);
                    showToast("收藏成功！");
                }else {
                    showToast("您已收藏！！");
                }
            }
        });
    }

    private void sendRequestData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Integer> baseEntry = ParserNews.getTiesInfo(jsonObject.toString());
                        int number=baseEntry.getData();
                        tv_number.setText("跟帖数："+number);
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
}
