package com.example.administrator.happyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.adapter.TiesAdapter;
import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.gloable.API;
import com.example.administrator.happyapplication.gloable.Contacts;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.Register;
import com.example.administrator.happyapplication.model.Ties;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.example.administrator.happyapplication.util.CommonUtil;
import com.example.administrator.happyapplication.util.SharedPreUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class TiesActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TiesActivity";
    private ListView lv_ties;
    private ImageView iv_ties_back;
    private ImageView iv_ties_commite;
    private EditText et_ties_content;

    private TiesAdapter adapter;

    private RequestQueue requestQueue;

    Summarys summarys;
    String explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ties);
        iv_ties_commite = (ImageView) findViewById(R.id.iv_ties_commite);
        iv_ties_commite.setOnClickListener(this);
        et_ties_content = (EditText) findViewById(R.id.et_ties);
        lv_ties = (ListView) findViewById(R.id.lv_ties);
        iv_ties_back = (ImageView) findViewById(R.id.iv_ties_back);
        iv_ties_back.setOnClickListener(this);
        adapter = new TiesAdapter(this);
        lv_ties.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        summarys = (Summarys) bundle.getSerializable("ties");
        requestQueue = Volley.newRequestQueue(this);
        sendTiesData();
    }

    private void sendTiesData() {
        String url = API.TIES_NAME + "ver=" + Contacts.VER + "&nid=" + summarys.getNid() + "&type=1" + "&stamp=" + CommonUtil.getSysTime() + "&cid=1" + "&dir=1" + "&cnt=20";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<List<Ties>> baseEntry = ParserNews.getTiesSuccInfo(jsonObject.toString());
                        List<Ties> list = baseEntry.getData();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ties_back:
                finish();
                break;
            case R.id.iv_ties_commite:
                String token = SharedPreUtil.getToken(this, "token");
                if(token==null){
                    Toast.makeText(TiesActivity.this, "请先登陆....", Toast.LENGTH_SHORT).show();
                }else{
                    commiteTies(token);
                }
                break;
        }
    }

    private void commiteTies(String token) {
        String content = et_ties_content.getText().toString();
        if(content.length()<=0){return;}
        try {
            content= URLEncoder.encode(content,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = API.COMMITE_TIES + "ver=" + Contacts.VER + "&nid=" + summarys.getNid() + "&token=" + token + "&imei=" + CommonUtil.getIMEI(this) + "&ctx=" + content;
        Log.i(TAG, "commiteTies: ===================================="+url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Register register = ParserNews.getRegisterInfo(jsonObject.toString());
                        explain = register.getExplain();
                        BaseEntry<Register> baseEntry=ParserNews.getBaseEntryRegister(jsonObject.toString());
                        int status=baseEntry.getStatus();
                        Toast.makeText(TiesActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        if(status==0){
                            Toast.makeText(TiesActivity.this, explain, Toast.LENGTH_SHORT).show();
                            sendTiesData();
                            et_ties_content.setText("");
                        }
                        if(status==-3){
                            Toast.makeText(TiesActivity.this, "请先登陆....", Toast.LENGTH_SHORT).show();
                        }
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
