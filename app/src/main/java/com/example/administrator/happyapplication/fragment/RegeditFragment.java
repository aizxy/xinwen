package com.example.administrator.happyapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import org.json.JSONObject;


public class RegeditFragment extends Fragment {
    private static final String TAG = "RegeditFragment";
    private EditText et_regedit_email,et_regedit_name,et_regedit_pwd;
    private Button btn_regedit;
    private CheckBox cb_regedit;

    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_regedit, container, false);
        et_regedit_email= (EditText) view.findViewById(R.id.et_regedit_add);
        et_regedit_name= (EditText) view.findViewById(R.id.et_regedit_nickName);
        et_regedit_pwd= (EditText) view.findViewById(R.id.et_regedit_pwd);
        btn_regedit= (Button) view.findViewById(R.id.btn_regedit);
        btn_regedit.setOnClickListener(onClickListener);
        cb_regedit= (CheckBox) view.findViewById(R.id.cb_regedit_agree);
        requestQueue= Volley.newRequestQueue(getActivity());
        return view;
    }

    private void requestRegister(String url){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseRegister=ParserNews.getBaseEntryRegister(jsonObject.toString());
                        Register register= ParserNews.getRegisterInfo(jsonObject.toString());
                        int result=register.getResult();
                        String s=register.getToken();
                        if(result==-2) {
                            Toast.makeText(getActivity(),"用户昵称重复",Toast.LENGTH_SHORT).show();
                        }

                        if(result==-3){
                            Toast.makeText(getActivity(),"邮箱重复",Toast.LENGTH_SHORT).show();
                        }
                        if(result==0){
                            startActivity(new Intent(getActivity(), UserActivity.class));
                            SharedPreUtil.saveRegisterInfo(baseRegister,getContext());
                            ((HomeActivity)getContext()).changeFragmentStatus();
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
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name,pwd,email;
            name=et_regedit_name.getText().toString();
            pwd=et_regedit_pwd.getText().toString();
            email=et_regedit_email.getText().toString();
            if (!cb_regedit.isChecked()){
                Toast.makeText(getActivity(),"请接受协议规定",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!CommonUtil.verifyEmail(email)){
                Toast.makeText(getActivity(),"请输入正确的邮箱",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(),"请输入用户名",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!CommonUtil.verifyPassword(pwd)){
                Toast.makeText(getActivity(),"请输入6-16位的字母或数字",Toast.LENGTH_SHORT).show();
                return;
            }
            String url= API.USER_REGISTER+"ver="+ Contacts.VER+"&uid="+name+"&email="+email+"&pwd="+pwd;
            requestRegister(url);
        }
    };
}
