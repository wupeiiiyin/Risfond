package com.risfond.rnss.login.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.hyphenate.chat.EMClient;
import com.mob.MobSDK;
import com.risfond.rnss.common.em.EMHelper;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.UmengUtil;
import com.risfond.rnss.message.activity.MainActivity;


public class WelcomeActivity extends AppCompatActivity {
    private final String TAG = "WelcomeActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = WelcomeActivity.this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(SPUtil.loadToken(context)) || !EMClient.getInstance().isLoggedInBefore()) {
                    LoginActivity.startAction(context);
                    finish();
                } else {
                    updateChat();
                }
            }
        }, 200);

    }

    private void updateChat() {
        if (EMHelper.getInstance().isLoggedIn()) {
            EMHelper.getInstance().updateChatInfo();
        }
        MainActivity.startAction(context);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        UmengUtil.onResumeToActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        UmengUtil.onPauseToActivity(this);
    }

}
