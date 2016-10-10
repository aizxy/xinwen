package com.example.administrator.happyapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.Register;

import cn.sharesdk.framework.Platform;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class SharedPreUtil {
    private static final String SHARED_PATH="app_share";
    private static final String SHARED_PATH_REGISTER="register";

    public static SharedPreferences getDefaultSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_PATH,Context.MODE_PRIVATE);
    }

    public static SharedPreferences getDefaultSharedPreferences_register(Context context){
        return context.getSharedPreferences(SHARED_PATH_REGISTER,Context.MODE_PRIVATE);
    }

    public static void saveRegisterInfo(BaseEntry<Register> baseRegister,Context context){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        Register register=baseRegister.getData();
        editor.putInt("result",register.getResult());
        editor.putString("explain",register.getExplain());
        editor.putString("token",register.getToken());
        editor.commit();
    }

    public static void savePlatInfo(Platform plat, Context context){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("icon",plat.getDb().getUserIcon());
        editor.putString("token",plat.getDb().getToken());
        editor.commit();
    }

    public static void putInt(Context context,String key,int value){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public static int getInt(Context context,String key){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key,0);
    }

    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getString(Context context,String key){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,null);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static boolean getIsLogined(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static String getToken(Context context,String key){
        SharedPreferences sharedPreferences=getDefaultSharedPreferences_register(context);
        return sharedPreferences.getString(key,null);
    }

    public static void clearAll(Context context ){
        SharedPreferences s=context.getSharedPreferences(SHARED_PATH, Activity.MODE_PRIVATE);
        s.edit().clear().commit();
    }
}
