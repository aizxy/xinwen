package com.example.administrator.happyapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class CommonUtil {
    /**
     * 获取当前日期
     */
    public static String getSysTime() {
        String systime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        systime = dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 验证密码格式
     */
    public static boolean verifyPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 验证邮箱格式
     */
    public static boolean verifyEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                + "|(([a-zA-Z0-9\\-]+\\.)+))"
                + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 存储大小格式转换
     */
    public static String getFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuffer sb = new StringBuffer();
        if (fileSize < 1024) {
            sb.append(fileSize);
            sb.append("B");
        } else if (fileSize < 1048576) {
            sb.append(df.format((double) fileSize / 1024));
            sb.append("K");
        } else if (fileSize < 1073741824) {
            sb.append(df.format((double) fileSize / 1048576));
            sb.append("M");
        } else {
            sb.append(df.format((double) fileSize / 1073741824));
            sb.append("G");
        }
        return sb.toString();
    }

    /**
     * 判断是否有网络链接
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        //获取手机所有链接管理对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null){
            return false;
        }else {
            //获取NetworkInfo对象
            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
            if(networkInfos!=null&&networkInfos.length>0){
                for(int i=0;i<networkInfos.length;i++){
                    if(networkInfos[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 获取手机的IMEI值
     */
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
