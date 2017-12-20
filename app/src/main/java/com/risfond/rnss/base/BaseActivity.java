package com.risfond.rnss.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.risfond.rnss.common.utils.AppManager;
import com.risfond.rnss.common.utils.UmengUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Abbott on 2017/3/23.
 * activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //所有activity竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置软键盘隐藏
        //        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(getContentViewResId());

        mUnBinder = ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        init(savedInstanceState);
    }

    public abstract int getContentViewResId();

    //在这里初始化数据
    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        AppManager.getInstance().finishActivity(this);
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

    /**
     * @return void    返回类型
     * @throws
     * @Title: back
     * @说 明:标题上面的返回键处理
     * @参 数: @param v
     */
    public void back(View v) {
        AppManager.getInstance().finishActivity(this);
    }


    public void startActivity(Class clzz, boolean isFinish){
        startActivity(new Intent(this,clzz));
        if (isFinish){
            finish();
        }
    }
}
