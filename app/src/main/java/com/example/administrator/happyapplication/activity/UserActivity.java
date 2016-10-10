package com.example.administrator.happyapplication.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.adapter.LoginAdapter;
import com.example.administrator.happyapplication.base.MybaseActivity;
import com.example.administrator.happyapplication.gloable.API;
import com.example.administrator.happyapplication.gloable.Contacts;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.User;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.example.administrator.happyapplication.util.CommonUtil;
import com.example.administrator.happyapplication.util.ImageLoader;
import com.example.administrator.happyapplication.util.SharedPreUtil;

import org.json.JSONObject;

public class UserActivity extends MybaseActivity implements View.OnClickListener {
    private static final String TAG = "UserActivity";
    private TextView name, integral, number;
    private ListView listView;
    private LoginAdapter adapter;
    private RequestQueue requestQueue;
    private Button btn_user_exit;
    private ImageView iv_back_home,iv_user_pic;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        iv_user_pic= (ImageView) findViewById(R.id.iv_user_pic);
        String username=SharedPreUtil.getString(this,"name");
        if(username!=null){
            String icon=SharedPreUtil.getString(this,"icon");
            ImageLoader imageLoader=new ImageLoader(this);
            imageLoader.display(icon,iv_user_pic);
        }

        dialog=new Dialog(this);
        requestQueue = Volley.newRequestQueue(this);
        btn_user_exit = (Button) findViewById(R.id.btn_user_exit);
        btn_user_exit.setOnClickListener(this);
        name = (TextView) findViewById(R.id.tv_user_name);
        name.setText(username);
        integral = (TextView) findViewById(R.id.tv_user_integral);
        number = (TextView) findViewById(R.id.tv_user_number);
        listView = (ListView) findViewById(R.id.lv_user_rizhi);
        adapter = new LoginAdapter(this);
        listView.setAdapter(adapter);
        iv_back_home= (ImageView) findViewById(R.id.iv_user_back);
        iv_back_home.setOnClickListener(this);
        String token = SharedPreUtil.getToken(this, "token");
        String url = API.USER_CENTER_DATA + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI(this) + "&token=" + token;
        requestUserData(url);
    }

    private void requestUserData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<User> userBaseEntry = ParserNews.getLoginSuccInfo(jsonObject.toString());
                        int status = userBaseEntry.getStatus();
                        if (status != 0) {
                            Toast.makeText(UserActivity.this, "用户数据请求错误！！", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = userBaseEntry.getData();
                            integral.setText("用户积分：" + user.getIntegration());
                            number.setText(user.getComnum() + "");
                            adapter.appendDataed(user.getLoginlog(), true);
                            adapter.upDataAdapter();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_user_exit:
                dialog();
                break;
            case R.id.iv_user_back:
                startActivity(new Intent(UserActivity.this,HomeActivity.class));
                break;
        }
    }

    private void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(UserActivity.this);
        builder.setMessage("你要干啥？");
        builder.setTitle("提示");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                SharedPreUtil.clearAll(UserActivity.this);
//                SharedPreUtil.putString(UserActivity.this,"name",null);
//                SharedPreUtil.putBoolean(UserActivity.this,"isLogin",false);
                Intent intent=new Intent(UserActivity.this, HomeActivity.class);
                intent.putExtra("isback",true);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("点错了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
