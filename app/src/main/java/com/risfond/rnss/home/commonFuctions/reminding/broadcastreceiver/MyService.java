package com.risfond.rnss.home.commonFuctions.reminding.broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.risfond.rnss.R;

public class MyService extends Service {
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();//继承onCreate方法
        try {
            mp = new MediaPlayer();
            mp = MediaPlayer.create(MyService.this, R.raw.iphonex);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                // 释放资源
                try {
                    mp.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {//服务停止时停止播放音乐并释放资源
        mp.stop();
        mp.release();
        mp = null;
        super.onDestroy();
    }
}