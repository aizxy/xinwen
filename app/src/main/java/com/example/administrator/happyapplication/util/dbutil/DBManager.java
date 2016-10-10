package com.example.administrator.happyapplication.util.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.happyapplication.gloable.Contacts;


/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "news.db";
    public static final String NEWSTYPE_NAME = "news_type";
    public static final String NEWS_NAME = "news";
    public static final String NEWSFAVORITE_NAME = "news_favorite";

    public DBManager(Context context) {
        super(context, DB_NAME, null, Integer.parseInt(Contacts.VER));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_1 = "create table " + NEWSTYPE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,subid INTEGER,subgroup TEXT);";
        String sql_2 = "create table " + NEWS_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,summary TEXT,icon TEXT,stamp TEXT,title TEXT,nid INTEGER,link TEXT,type INTEGER);";
        String sql_3 = "create table " + NEWSFAVORITE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,summary TEXT,icon TEXT,stamp TEXT,title TEXT,nid INTEGER,link TEXT,type INTEGER);";
        sqLiteDatabase.execSQL(sql_1);
        sqLiteDatabase.execSQL(sql_2);
        sqLiteDatabase.execSQL(sql_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
