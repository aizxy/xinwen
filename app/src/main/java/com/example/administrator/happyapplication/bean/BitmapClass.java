package com.example.administrator.happyapplication.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class BitmapClass {
    private Bitmap bitmap;

    public BitmapClass(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "BitmapClass{" +
                "bitmap=" + bitmap +
                '}';
    }
}
