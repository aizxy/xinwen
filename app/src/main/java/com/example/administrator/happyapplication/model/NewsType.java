package com.example.administrator.happyapplication.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class NewsType {
    private int gid;
    private String group;
    private List<SubType> subgrp;

    public NewsType(int gid, String group, List<SubType> subList) {
        this.gid = gid;
        this.group = group;
        this.subgrp = subList;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<SubType> getSubList() {
        return subgrp;
    }

    public void setSubList(List<SubType> subList) {
        this.subgrp = subList;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "gid=" + gid +
                ", group='" + group + '\'' +
                ", subList=" + subgrp +
                '}';
    }
}
