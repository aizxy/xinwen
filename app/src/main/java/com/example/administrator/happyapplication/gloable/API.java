package com.example.administrator.happyapplication.gloable;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class API {
    public static final String ServerIP = "http://118.244.212.82:9092/newsClient/";
    /**
     * 首页列表
     */
    public static final String NEWS_LIST = ServerIP + "";
    /**
     * 标题列表
     */
    public static final String NEWS_SORT = ServerIP + "news_sort?";
    /**
     * 用户登陆界面
     */
    public static final String USER_LOGIN = ServerIP + "user_login?";

    public static final String USER_REGISTER=ServerIP+"user_register?";

    public static final String USER_CENTER_DATA = ServerIP + "user_home?";

    public static final String USER_TIES=ServerIP+"cmt_num?";

    public static final String TIES_NAME=ServerIP+"cmt_list?";

    public static final String COMMITE_TIES=ServerIP+"cmt_commit?";

    public static final String UPDATE_INFO=ServerIP+"";

    public static final String UPDATE_REQUEST=ServerIP+"update?";
}
