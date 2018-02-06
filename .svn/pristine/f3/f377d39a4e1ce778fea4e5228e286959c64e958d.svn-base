package com.hyphenate.easeui.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by Abbott on 2017/6/6.
 */

public class GlideUtil {
    public static Bitmap downLoadImage(final Context context, final String url, final ImageCallBack imageCallBack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap myBitmap = Glide.with(context)
                            .load(url)
                            .asBitmap()
                            .centerCrop()
                            .into(300, 400)
                            .get();
                    imageCallBack.callBack(myBitmap);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return null;
    }

    public static void downLoadImage(Context context, String url, ImageView imageView) {
        //优先加载
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(R.drawable.ease_default_image)
                .override(320, 480)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

    }

    public static void downLoadHeadImage(Context context, String url, ImageView imageView, Transformation transformation) {
        //优先加载
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(R.drawable.ease_default_avatar)
                .override(R.dimen.w, R.dimen.h)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(transformation)
                .dontAnimate()
                .into(imageView);

    }

    public static void downLoadPhoto(Context context, String url, ImageView imageView, Transformation transformation) {
        //优先加载
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(R.drawable.ease_default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(transformation)
                .dontAnimate()
                .into(imageView);

    }

    public static void loadResumeImage(Context context, String url, ImageView imageView, Transformation transformation) {
        //优先加载
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(R.drawable.person_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(transformation)
                .dontAnimate()
                .into(imageView);

    }

    /**
     * 新同事头像
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void colleagueImage(Context context, String url, ImageView imageView) {
        //优先加载
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(R.drawable.ease_default_avatar)
                .override(ScreenUtil.dip2px(context, 105), ScreenUtil.dip2px(context, 86))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

    }

    public static void beforeDownLoadImage(Context context, String url) {
        Glide.with(context.getApplicationContext())
                .load(url);
    }

    public static void downLoadGif(Context context, Object obj, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(obj)
                .asGif()
                .override(150, 150)
                .error(R.drawable.ease_default_expression)
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

    }

    public static void beforeDownLoadGif(Context context, Object obj) {
        Glide.with(context)
                .load(obj);
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public static boolean clearCacheDiskSelf(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface ImageCallBack {
        void callBack(Bitmap bitmap);
    }

}
