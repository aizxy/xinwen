package com.example.administrator.happyapplication.util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.happyapplication.bean.Summarys;
import com.example.administrator.happyapplication.model.SubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class DBTools {
    private Context context;
    private DBManager dbManager;
    private SQLiteDatabase sd;
    public DBTools(Context context){
        this.context=context;
        dbManager=new DBManager(context);
    }
    public boolean saveLocalFavorite(Summarys summarys){
        sd=dbManager.getReadableDatabase();
        String sql="select nid from "+DBManager.NEWSFAVORITE_NAME+" where nid=?";
        Cursor c=sd.rawQuery(sql,new String[]{summarys.getNid()+""});
        if(c.moveToNext()){
            return false;
        }
        ContentValues values=new ContentValues();
        values.put("type",summarys.getType());
        values.put("nid",summarys.getNid());
        values.put("summary",summarys.getSummary());
        values.put("icon",summarys.getIcon());
        values.put("stamp",summarys.getStamp());
        values.put("title",summarys.getTitle());
        values.put("link",summarys.getLink());
        sd.insert(DBManager.NEWSFAVORITE_NAME,null,values);
        c.close();
        sd.close();
        return true;
    }

    public List<Summarys> getLocalFavorite(){
        List<Summarys> summarysList =new ArrayList<>();
        sd=dbManager.getReadableDatabase();
        Cursor c=sd.query(DBManager.NEWSFAVORITE_NAME,null,null,null,null,null,null);
        if(c.moveToFirst()){
            do {
                int type=c.getInt(c.getColumnIndex("type"));
                int nid=c.getInt(c.getColumnIndex("nid"));
                String summary=c.getString(c.getColumnIndex("summary"));
                String icon=c.getString(c.getColumnIndex("icon"));
                String stamp=c.getString(c.getColumnIndex("stamp"));
                String title=c.getString(c.getColumnIndex("title"));
                String link=c.getString(c.getColumnIndex("link"));
                Summarys summarys=new Summarys(icon,stamp,summary,title,link, nid,type);
                summarysList.add(summarys);
            }while (c.moveToNext());
            c.close();
            sd.close();
        }
        return summarysList;
    }

    public void saveLocalNews(Summarys summarys){
        sd=dbManager.getReadableDatabase();
        String sql="select nid from "+DBManager.NEWS_NAME+" where nid=?";
        Cursor c=sd.rawQuery(sql,new String[]{summarys.getNid()+""});
        if(c.moveToNext()){
            return;
        }
        ContentValues values=new ContentValues();
        values.put("type",summarys.getType());
        values.put("nid",summarys.getNid());
        values.put("summary",summarys.getSummary());
        values.put("icon",summarys.getIcon());
        values.put("stamp",summarys.getStamp());
        values.put("title",summarys.getTitle());
        values.put("link",summarys.getLink());
        sd.insert(DBManager.NEWS_NAME,null,values);
        c.close();
        sd.close();
    }

    public List<Summarys> getLocalNews(){
        List<Summarys> summarysList =new ArrayList<>();
        sd=dbManager.getReadableDatabase();
        Cursor c=sd.query(DBManager.NEWS_NAME,null,null,null,null,null,null);
        if(c.moveToFirst()){
            do {
                int type=c.getInt(c.getColumnIndex("type"));
                int nid=c.getInt(c.getColumnIndex("nid"));
                String summary=c.getString(c.getColumnIndex("summary"));
                String icon=c.getString(c.getColumnIndex("icon"));
                String stamp=c.getString(c.getColumnIndex("stamp"));
                String title=c.getString(c.getColumnIndex("title"));
                String link=c.getString(c.getColumnIndex("link"));
                Summarys summarys=new Summarys(icon,stamp,summary,title,link, nid,type);
                summarysList.add(summarys);
            }while (c.moveToNext());
            c.close();
            sd.close();
        }
        return summarysList;
    }
    public void saveLocalSubType(SubType subType){
        sd=dbManager.getReadableDatabase();
        String sql="select subid from "+DBManager.NEWSTYPE_NAME+" where subid=?";
        Cursor c=sd.rawQuery(sql,new String[]{subType.getSubid()+""});
        if(c.moveToNext()){
            return;
        }
        ContentValues values=new ContentValues();
        values.put("subid",subType.getSubid());
        values.put("subgroup",subType.getSubgroup());
        sd.insert(DBManager.NEWSTYPE_NAME,null,values);
        c.close();
        sd.close();
    }

    public List<SubType> getLocalSubType(){
        List<SubType> subTypes =new ArrayList<>();
        sd=dbManager.getReadableDatabase();
        Cursor c=sd.query(DBManager.NEWSTYPE_NAME,null,null,null,null,null,null);
        if(c.moveToFirst()){
            do {
                int subid=c.getInt(c.getColumnIndex("subid"));
                String subgroup=c.getString(c.getColumnIndex("subgroup"));
                SubType subType=new SubType(subgroup,subid);
                subTypes.add(subType);
            }while (c.moveToNext());
            c.close();
            sd.close();
        }
        return subTypes;
    }
}
