package com.example.administrator.happyapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.happyapplication.activity.UserActivity;
import com.example.administrator.happyapplication.base.MybaseActivity;
import com.example.administrator.happyapplication.fragment.FavoriteFragment;
import com.example.administrator.happyapplication.fragment.HomeFragment;
import com.example.administrator.happyapplication.fragment.JunshiFragment;
import com.example.administrator.happyapplication.fragment.LeftFragment;
import com.example.administrator.happyapplication.fragment.LoginFragment;
import com.example.administrator.happyapplication.fragment.PicFragment;
import com.example.administrator.happyapplication.fragment.RegeditFragment;
import com.example.administrator.happyapplication.fragment.RightFragment;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.Updates;
import com.example.administrator.happyapplication.model.parser.ParserNews;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends MybaseActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";
    private HomeFragment homeFragment;
    private LeftFragment leftFragment;
    private RightFragment rightFragment;
    private LoginFragment loginFragment;
    private RegeditFragment regeditFragment;
    private JunshiFragment junshiFragment;
    private FavoriteFragment favoriteFragment;
    private PicFragment picFragment;

    private ImageView iv_left, iv_right;
    public static SlidingMenu slidingMenu;

    private TextView tv_home_title;

//    private RequestQueue requestQueue;

    String path;
    int version;
    int old=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        iv_left = (ImageView) findViewById(R.id.iv_home_left);
        iv_right = (ImageView) findViewById(R.id.iv_home_right);
        iv_right.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        loadHomeFragment();
        initSlidingMenu();
    }

    public void loadHomeFragment() {
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contianer, homeFragment).commit();
    }

    private void initSlidingMenu() {
        leftFragment = new LeftFragment();
        rightFragment = new RightFragment();

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.layout_menu);
        slidingMenu.setSecondaryMenu(R.layout.layout_right);

        getSupportFragmentManager().beginTransaction().add(R.id.slidingMenu_contianer, leftFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_right_contianer, rightFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home_left:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.iv_home_right:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showSecondaryMenu();
                }
                break;
        }

    }

    public void setTitle(String name) {
        tv_home_title.setText(name);
    }

    public void showLoginFragment() {
        setTitle("用户登陆");
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, loginFragment).commit();
    }

    public void showUserActivity() {
        openActivity(UserActivity.class, null);
    }

    public void showRegeditFragment() {
        setTitle("账号注册");
        if (regeditFragment == null) {
            regeditFragment = new RegeditFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, regeditFragment).commit();
    }

    public void changeFragmentStatus() {
        rightFragment.changeView();
    }

    public void showJunshiFragment() {
        if (junshiFragment == null) {
            junshiFragment = new JunshiFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, junshiFragment).commit();
    }

    public void showDifHomeFragment() {
        setTitle("资讯");
        slidingMenu.showContent();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, homeFragment).commit();
    }

    public void showPicFragment() {
        setTitle("图片");
        slidingMenu.showContent();
        if (picFragment == null) {
            picFragment = new PicFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, picFragment).commit();
    }

    public void showFavoriteFragment() {
        setTitle("我的收藏");
        slidingMenu.showContent();
        if (favoriteFragment == null) {
            favoriteFragment = new FavoriteFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, favoriteFragment).commit();
    }

    private void sendInfo() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL("http://192.168.3.80:8080/test/update.json");
                    HttpURLConnection htt= (HttpURLConnection) url.openConnection();
                    InputStream in = htt.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    StringBuilder str = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        str.append(line);
                    }
                    Message msg=new Message();
                    msg.what=2;
                    msg.obj=str.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请升级APP至版本");
        builder.setMessage("是否升级？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    sendInfo();
                    downFile(path);
                } else {
                    Toast.makeText(HomeActivity.this, "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();
    }

    private void downFile(final String path){
        final ProgressDialog pd;
        pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载，请耐心等待....");
        pd.show();

        new Thread(){
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://192.168.3.80:8080/apk/update.apk");
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    InputStream in = httpURLConnection.getInputStream();
                    httpURLConnection.connect();
                    FileOutputStream fileOutputStream=null;
                    if(in!=null){
                        File file=new File(Environment.getExternalStorageDirectory(),"Test.apk");
                        fileOutputStream=new FileOutputStream(file);
                        byte[] buf=new byte[2];
                        int ch=-1;
                        int process=0;
                        while((ch=in.read(buf))!=-1){
                            fileOutputStream.write(buf,0,ch);
                            process+=ch;
                            pd.setProgress(process);
                        }
                    }
                    fileOutputStream.flush();
                    if(fileOutputStream!=null){
                        fileOutputStream.close();
                    }
                    pd.dismiss();
                    Message msg=new Message();
                    msg.what=1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                update();
            }
            if(msg.what==2){
                String json= (String) msg.obj;
                BaseEntry<Updates> b=ParserNews.getUpdateInfo(json);
                Updates u=b.getData();
                String p=u.getLink();
                downFile(p);
            }
        }
    };
    void update(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "Test.apk")),
                      "application/vnd.android.package-archive");
        startActivity(intent);

    }
    public void upDate(){
        sendInfo();
        if (old!=version) {
            Toast.makeText(HomeActivity.this,"要更新",Toast.LENGTH_SHORT).show();
            showUpdateDialog();
        } else {
            Toast.makeText(HomeActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
        }
    }
}
