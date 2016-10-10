package com.example.administrator.happyapplication.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class DBTools extends SQLiteOpenHelper {
    private static final String DB_NAME="news.db";
    private static final String TABLE_NAME="news";
    private SQLiteDatabase sd;

    public DBTools(Context context){
        super(context,DB_NAME,null,1);
    }

    public DBTools(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table "+TABLE_NAME+"(uid INTEGER PRIMARY KEY AUTOINCREMENT,subgroup TEXT,group TEXT,gid TEXT,subid TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

//    public void saveData(Nnnnew nnnnews){
//        sd=getReadableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("subgroup",nnnnews.getSubgroup());
//        values.put("group",nnnnews.getSubid());
//        values.put("gid",nnnnews.getGid());
//        values.put("subid",nnnnews.getGroup());
//        sd.insert(TABLE_NAME,null,values);
//    }
//
//    public List<Nnnnews> findAll(){
//        sd=getReadableDatabase();
//        List<Nnnnews> list=null;
//        Cursor c=sd.query(TABLE_NAME,null,null,null,null,null,null);
//        if(c!=null){
//            list=new ArrayList<Nnnnews>();
//            while(c.moveToNext()){
//                Nnnnews nnnnews=new Nnnnews(c.getString(1),c.getString(2),c.getString(3),c.getString(4));
//                list.add(nnnnews);
//            }
//        }
//        return list;
//    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
