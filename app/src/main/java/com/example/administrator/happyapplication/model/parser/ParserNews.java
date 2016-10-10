package com.example.administrator.happyapplication.model.parser;

import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.model.BaseEntry;
import com.example.administrator.happyapplication.model.NewsType;
import com.example.administrator.happyapplication.model.Register;
import com.example.administrator.happyapplication.model.SubType;
import com.example.administrator.happyapplication.model.Ties;
import com.example.administrator.happyapplication.model.Updates;
import com.example.administrator.happyapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class ParserNews {
    public static List<SubType> getNewsType(String json){
        Gson gson=new Gson();
        BaseEntry<List<NewsType>> list=gson.fromJson(json,
                new TypeToken<BaseEntry<List<NewsType>>>(){}.getType());
        return list.getData().get(0).getSubList();
    }
    public static List<Summarys> getSummarys(String json){
        Gson gson=new Gson();
        BaseEntry<List<Summarys>> list=gson.fromJson(json,new TypeToken<BaseEntry<List<Summarys>>>(){}.getType());
        return list.getData();
    }

    public static Register getRegisterInfo(String json){
        Gson gson=new Gson();
        BaseEntry<Register> registerBaseEntry=gson.fromJson(json,new TypeToken<BaseEntry<Register>>(){}.getType());
        return registerBaseEntry.getData();
    }

    public static BaseEntry<Register> getBaseEntryRegister(String json){
        Gson gson=new Gson();
        BaseEntry<Register> registerBaseEntry=gson.fromJson(json,new TypeToken<BaseEntry<Register>>(){}.getType());
        return registerBaseEntry;
    }

    public static BaseEntry<User> getLoginSuccInfo(String json){
        Gson gson=new Gson();
        BaseEntry<User> userBaseEntry=gson.fromJson(json,new TypeToken<BaseEntry<User>>(){}.getType());
        return userBaseEntry;
    }

    public static BaseEntry getTiesInfo(String json){
        Gson gson=new Gson();
        BaseEntry<Integer> baseEntry=gson.fromJson(json,new TypeToken<BaseEntry<Integer>>(){}.getType());
        return baseEntry;
    }

    public static BaseEntry<List<Ties>> getTiesSuccInfo(String json){
        Gson gson=new Gson();
        BaseEntry<List<Ties>> baseEntry=gson.fromJson(json,new TypeToken<BaseEntry<List<Ties>>>(){}.getType());
        return baseEntry;
    }

    public static BaseEntry<Updates> getUpdateInfo(String json){
        Gson gson=new Gson();
        BaseEntry<Updates> baseEntry=gson.fromJson(json,new TypeToken<BaseEntry<Updates>>(){}.getType());
        return baseEntry;
    }
}
