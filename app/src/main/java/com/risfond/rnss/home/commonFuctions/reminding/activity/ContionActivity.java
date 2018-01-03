package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.risfond.rnss.home.commonFuctions.reminding.view.DashboardView4;

public class ContionActivity extends AppCompatActivity {
    //private XingView xXingView = null;
    private DashboardView4 xXingView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 实例化GameView对象 */


        //this.xXingView = new XingView(this);
        this.xXingView = new DashboardView4(this);
        // 设置显示为我们自定义的View(GameView)
        setContentView(xXingView);
    }
}
