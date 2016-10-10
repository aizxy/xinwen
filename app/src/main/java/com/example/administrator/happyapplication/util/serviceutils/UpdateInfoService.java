package com.example.administrator.happyapplication.util.serviceutils;

import android.content.Context;

import com.example.administrator.happyapplication.bean.UpdateInfo;
import com.example.administrator.happyapplication.gloable.API;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class UpdateInfoService {
    public UpdateInfoService(Context context){
    }

    public UpdateInfo getUpdateInfo() throws Exception{
        String path= API.UPDATE_INFO+"/update.txt";
        StringBuffer sb=new StringBuffer();
        String line=null;
        BufferedReader br=null;
        try{
            URL url=new URL(path);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream is=httpURLConnection.getInputStream();
            br=new BufferedReader(new InputStreamReader(is));
            while ((line=br.readLine())!=null){
                sb.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(br!=null){
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String info=sb.toString();
        UpdateInfo updateInfo=new UpdateInfo();
        updateInfo.setVersion(info.split("&")[1]);
        updateInfo.setDescription(info.split("&")[2]);
        updateInfo.setUrl(info.split("&")[3]);
        return updateInfo;
    }
}
