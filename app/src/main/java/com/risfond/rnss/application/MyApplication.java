package com.risfond.rnss.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.risfond.rnss.common.em.EMHelper;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.UmengUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.Properties;

/**
 * Created by Abbott on 2017/5/11.
 */

public class MyApplication extends Application {
    public static Properties properties = null;
    private Context applicationContext;

    public static MyApplication instance;

    public static MyApplication getMyInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        properties = PropertiesUtil.getProperties(getApplicationContext());
        EMHelper.getInstance().initEMChat(applicationContext);
        UmengUtil.initUmeng();
        ZXingLibrary.initDisplayOpinion(this);
        initX5Environment();
    }

    private void initX5Environment(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        try {
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(),  cb);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
