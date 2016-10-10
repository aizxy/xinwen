package com.example.administrator.happyapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class ImageLoader {
    private Context context;
    //Lrucache缓存
    private static LruCache<String, Bitmap> mCache = new LruCache<>((int) (Runtime.getRuntime().freeMemory() / 4));

    public ImageLoader(Context context) {
        this.context = context;
    }

    /**
     * 加载图片
     * @param path
     * @param imageView
     * @return
     */
    public Bitmap display(String path, ImageView imageView){
        Bitmap bitmap=null;
        if(path==null&&path.length()<=0) {
            return bitmap;
        }
        bitmap =loadImageFromReference(path);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }
        //本地缓存取
       bitmap = loadImageFromCache(path);
        if(bitmap!=null){
            mCache.put(path,bitmap);
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }
        DownPic(path,imageView);
        return bitmap;
    }

    private Bitmap loadImageFromReference(String url){
        return mCache.get(url);
    }

    private Bitmap loadImageFromCache(String url){
        String name=url.substring(url.lastIndexOf("/")+1);
        File cacheDir=context.getExternalCacheDir();//返回一个缓存目录
        if(cacheDir==null){
            return null;
        }
        File files[]=cacheDir.listFiles();
        if(files==null){
            return null;
        }
        File bitmapFile=null;
        for(File file:files){
            if(file.getName().equals(name)){
                bitmapFile=file;
            }
        }
        if(bitmapFile==null){
            return null;
        }
        Bitmap fileBitmap=null;
        fileBitmap= BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        return fileBitmap;
    }

    private void saveLocal(String url,Bitmap bitmap){
        String name=url.substring(url.lastIndexOf("/")+1);
        File cacheDir=context.getExternalCacheDir();
        if(!cacheDir.exists()){
            cacheDir.mkdir();
        }
        OutputStream op=null;
        try {
            op=new FileOutputStream(new File(cacheDir,name));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,op);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                op.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从网络下载图片
     * @param path
     * @param iv
     */
    private void DownPic(final String path,final ImageView iv){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int code=msg.what;
                if(code==1){
                    Bitmap bm= (Bitmap) msg.obj;
                    iv.setImageBitmap(bm);
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL(path);
                    HttpURLConnection con= (HttpURLConnection) url.openConnection();
                    InputStream in=con.getInputStream();
                    Bitmap bitmap=BitmapFactory.decodeStream(in);
                    //存入缓存
                    mCache.put(path,bitmap);
                    //存入本地
                    saveLocal(path,bitmap);
                    Message msg =new Message();
                    msg.what=1;
                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
