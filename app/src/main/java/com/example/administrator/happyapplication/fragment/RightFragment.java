package com.example.administrator.happyapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.happyapplication.HomeActivity;
import com.example.administrator.happyapplication.R;
import com.example.administrator.happyapplication.activity.UserActivity;
import com.example.administrator.happyapplication.util.ImageLoader;
import com.example.administrator.happyapplication.util.SharedPreUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class RightFragment extends Fragment implements View.OnClickListener {

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR= 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private ImageView iv_loginImageView,iv_loginedImageView;
    private TextView tv_loginTextView;
    private RelativeLayout rl_right_user,rl_right_default;
    private boolean isLogin;

    private TextView tv_loginedUsername,tv_update;

    private ImageView fun_weibo,fun_friends;

    Platform plat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        iv_loginedImageView= (ImageView) view.findViewById(R.id.iv_loginedImageView);
        fun_weibo= (ImageView) view.findViewById(R.id.fun_weibo);
        fun_weibo.setOnClickListener(this);
        fun_friends= (ImageView) view.findViewById(R.id.fun_friends);
        fun_friends.setOnClickListener(this);
        rl_right_default= (RelativeLayout) view.findViewById(R.id.rl_right_default);
        rl_right_user= (RelativeLayout) view.findViewById(R.id.rl_right_user);
        tv_loginedUsername= (TextView) view.findViewById(R.id.tv_loginedUsername);
        rl_right_user.setOnClickListener(this);
        tv_update= (TextView) view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        iv_loginImageView = (ImageView) view.findViewById(R.id.iv_loginImageView);
        tv_loginTextView = (TextView) view.findViewById(R.id.tv_loginTextView);
        iv_loginImageView.setOnClickListener(this);
        tv_loginTextView.setOnClickListener(this);
        isLogin= SharedPreUtil.getIsLogined(getContext(),"isLogin",false);
        changeView();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fun_friends){
            showShare();
        }
        if (view.getId() == R.id.iv_loginImageView || view.getId() == R.id.tv_loginTextView) {
            ((HomeActivity) getActivity()).showLoginFragment();
        }
        if(view.getId()==R.id.rl_right_user){
            ((HomeActivity)getActivity()).showUserActivity();
        }
        if(view.getId()==R.id.tv_update){
            ((HomeActivity)getActivity()).upDate();
        }
        if(view.getId()==R.id.fun_weibo){
           ShareSDK.initSDK(getActivity());
            plat=ShareSDK.getPlatform(SinaWeibo.NAME);
            if(plat==null){
                return;
            }
            if(plat.isAuthValid()){
                plat.removeAccount(true);
                return;
            }
            plat.SSOSetting(false);
            plat.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    if (i == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_COMPLETE;
                        msg.arg2 = i;
                        msg.obj =  new Object[] {platform.getName(), hashMap};
                        handler.sendMessage(msg);
                    }
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    if (i == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_ERROR;
                        msg.arg2 = i;
                        msg.obj = throwable;
                        handler.sendMessage(msg);
                    }
                    throwable.printStackTrace();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    if (i == Platform.ACTION_USER_INFOR) {
                        Message msg = new Message();
                        msg.what = MSG_AUTH_CANCEL;
                        msg.arg2 = i;
                        msg.obj = platform;
                        handler.sendMessage(msg);
                    }
                }
            });
            plat.showUser(null);
        }
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_AUTH_CANCEL: {
                    Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();
                } break;
                case MSG_AUTH_ERROR: {
                    Throwable t = (Throwable) msg.obj;
                    String text = "caught error: " + t.getMessage();
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                } break;
                case MSG_AUTH_COMPLETE: {
                    SharedPreUtil sharedPreUtil=new SharedPreUtil();
                    sharedPreUtil.savePlatInfo(plat,getContext());
                    SharedPreUtil.putString(getContext(),"name",plat.getDb().getUserName());
                    SharedPreUtil.putString(getContext(),"id",plat.getDb().getUserId());
                    SharedPreUtil.putString(getContext(),"icon",plat.getDb().getUserIcon());
                    SharedPreUtil.putBoolean(getContext(),"isLogin",true);
                    changeView();
                    Intent intent=new Intent(getActivity(), UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } break;
            }
        }
    };



    public void changeView(){
        isLogin= SharedPreUtil.getIsLogined(getContext(),"isLogin",false);
        String name=SharedPreUtil.getString(getContext(),"name");
        String icon=SharedPreUtil.getString(getContext(),"icon");
        if(isLogin){
            if(name==null){
                name="立即登陆";
            }
            rl_right_user.setVisibility(View.VISIBLE);
            rl_right_default.setVisibility(View.GONE);
            tv_loginedUsername.setText(name);
            ImageLoader imageLoader=new ImageLoader(getContext());
            imageLoader.display(icon,iv_loginedImageView);
        }else{
            rl_right_default.setVisibility(View.VISIBLE);
            rl_right_user.setVisibility(View.GONE);
        }
    }

    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getActivity());
    }
}
