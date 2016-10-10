package com.example.administrator.happyapplication.model;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class Updates {
    private String pkgName;
    private int version;
    private String link;
    private String md5;

    public Updates(String pkgName, int version, String link, String md5) {
        this.pkgName = pkgName;
        this.version = version;
        this.link = link;
        this.md5 = md5;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "Updates{" +
                "pkgName='" + pkgName + '\'' +
                ", version=" + version +
                ", link='" + link + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
