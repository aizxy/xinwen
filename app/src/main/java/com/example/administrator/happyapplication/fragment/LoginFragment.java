package com.example.administrator.happyapplication.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import com.example.administrator.happyapplication.activity.UserActivity;
import com.example.administrator.happyapplication.gloable.API;
import com.example.administrator.happyapplication.gloable.Contacts;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.Register;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.example.administrator.happyapplication.util.CommonUtil;
import com.example.administrator.happyapplication.util.SharedPreUtil;
import com.example.administrator.happyapplication.view.MyEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "LoginFragment";
    private Button btn_regedit_login;
    private Button btn_login_login;
    private MyEditText et_loginname,et_loginpwd;
    private TextView tv_login_forgetpass;

    private RequestQueue requestQueue;

    private PopupWindow popupWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        tv_login_forgetpass= (TextView) view.findViewById(R.id.tv_login_forgetpass);
        tv_login_forgetpass.setOnClickListener(this);
        btn_regedit_login= (Button) view.findViewById(R.id.btn_login_regist);
        btn_regedit_login.setOnClickListener(this);
        btn_login_login= (Button) view.findViewById(R.id.btn_login_login);
        btn_login_login.setOnClickListener(this);
        et_loginname= (MyEditText) view.findViewById(R.id.et_loginName);
        et_loginpwd= (MyEditText) view.findViewById(R.id.et_loginPwd);
        requestQueue= Volley.newRequestQueue(getActivity());
        showPopupWindow();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_login_forgetpass){
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
            }else if(popupWindow!=null){
//                popupWindow.showAsDropDown(btn_login_login,0,2);
                popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER,0,0);
            }
        }
        if(view.getId()==R.id.btn_login_regist){
            ((HomeActivity)getActivity()).showRegeditFragment();
        }
        if(view.getId()==R.id.btn_login_login){
            String name=et_loginname.getText().toString();
            String pwd=et_loginpwd.getText().toString();
            if(TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!CommonUtil.verifyPassword(pwd)){
                Toast.makeText(getActivity(),"密码应是6-16位的数字或字母",Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreUtil.putBoolean(getActivity(),"isLogin",true);
            SharedPreUtil.putString(getActivity(),"name",name);
            String url= API.USER_LOGIN+"ver="+ Contacts.VER+"&uid="+name+"&pwd="+pwd+"&device="+"0";
            requestLogin(url);
        }
    }

    private void requestLogin(String url){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseLogin= ParserNews.getBaseEntryRegister(jsonObject.toString());
                        int result=baseLogin.getStatus();
                        if(result==0){
                            startActivity(new Intent(getActivity(), UserActivity.class));
                            SharedPreUtil.saveRegisterInfo(baseLogin,getContext());
                        }
                        if(result==-1){
                            Toast.makeText(getActivity(),"用户名或密码错误！！",Toast.LENGTH_SHORT).show();
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

    private void showPopupWindow() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_forgetpass, null);
        final EditText et = (EditText) view.findViewById(R.id.ed_forget_email);
        Button btn= (Button) view.findViewById(R.id.btn_forget_find);
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=et.getText().toString();
                final String api= API.ServerIP+"user_forgetpass?ver=1&email="+email;
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            URL url=new URL(api);
                            HttpURLConnection http= (HttpURLConnection) url.openConnection();
                            http.setConnectTimeout(5000);
                            http.setReadTimeout(5000);
                            InputStream in=http.getInputStream();
                            BufferedReader br=new BufferedReader(new InputStreamReader(in));
                            String line;
                            StringBuilder sb=new StringBuilder();
                            while ((line=br.readLine())!=null){
                                sb.append(line);
                            }
                            Message msg=new Message();
                            msg.what=1;
                            msg.obj=sb.toString();
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                popupWindow.dismiss();
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                String json= (String) msg.obj;
                Gson gson =new Gson();
                BaseEntry<Register> registerBaseEntry=gson.fromJson(json,new TypeToken<BaseEntry<Register>>(){}.getType());
                Register register=registerBaseEntry.getData();
                String explain=register.getExplain();
                if("已成功发送到邮箱".equals(explain)){
                    Toast.makeText(getActivity(),explain,Toast.LENGTH_SHORT).show();

                    ((HomeActivity)getActivity()).showDifHomeFragment();
                }else{
                    Toast.makeText(getActivity(),"邮箱不正确！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
