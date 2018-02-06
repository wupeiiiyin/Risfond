package com.risfond.rnss.home.commonFuctions.myCourse.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.SystemBarTintManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;


public class MyCourseDetailActivity extends AppCompatActivity {

    private Context context;
    private WebView webView;
    private ImageView ivBack;
    private RelativeLayout rlBack;
    private ProgressBar pbResumeDetail;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_detail);
        context = MyCourseDetailActivity.this;
        webView = (WebView) findViewById(R.id.wv_x5);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        pbResumeDetail = (ProgressBar) findViewById(R.id.pb_resume_detail);

        url = getIntent().getStringExtra("url");

        webView.loadUrl(url);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        webView.setWebViewClient(client);
        webView.setWebChromeClient(webChromeClient);

        setStatusBarColor(R.color.transparent);
        webSettings();
        autoPlay();
        goBack();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置状态栏颜色
     * 也就是所谓沉浸式状态栏
     */
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
            titleMargin();
        }
    }

    private void statusBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            titleMargin();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            titleMargin();
        }
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);

    }

    private void titleMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, getStatusBarHeight(), 0, 0);
        rlBack.setLayoutParams(params);
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 70;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    private void autoPlay() {
        Bundle data = new Bundle();
        data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
        data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，
        data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
            webView.getX5WebViewExtension().setScrollBarFadingEnabled(false);
        }
    }

    private void webSettings() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(false);
        webSetting.setDatabaseEnabled(false);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private void goBack() {
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("tel:")) {
                String mobile = url.substring(url.lastIndexOf("/") + 1);
                CommonUtil.call(context, mobile.substring(4, mobile.length()));
                //这个超连接,java已经处理了，webview不要处理了
                return true;
            } else if (url.contains("mailto:")) {
                CallUtil.mail(context, url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            webView.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            if (pbResumeDetail != null) {
                if (newProgress != 100) {
                    pbResumeDetail.setVisibility(View.VISIBLE);
                }
                pbResumeDetail.setProgress(newProgress);

                if (pbResumeDetail.getProgress() == 100) {
                    final Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            Message message = new Message();
                            message.what = 1;
                            progressHandler.sendMessage(message);
                            timer.cancel();
                        }
                    };
                    timer.schedule(task, 500);
                }
            }
            super.onProgressChanged(webView, newProgress);

        }
    };

    private Handler progressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgId = msg.what;
            if (msgId == 1) {
                if (pbResumeDetail != null) {
                    pbResumeDetail.setVisibility(View.GONE);
                }
            }
        }
    };

    public static void StartAction(Context context, String url) {
        Intent intent = new Intent(context, MyCourseDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
