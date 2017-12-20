package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class ExpAndProWebViewActivity extends BaseActivity {
    private Context context;
    private String url;
    private String title;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.exp_pro_wb)
    WebView expProWb;
    @BindView(R.id.exp_pro_progressBar)
    ProgressBar expProProgressBar;

    @Override
    public void init(Bundle savedInstanceState) {
        context = ExpAndProWebViewActivity.this;
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        tvTitle.setText(title);

        initWebView();

    }

    private void initWebView() {
        WebSettings settings = expProWb.getSettings();
        //支持JavaScript脚本语言
        settings.setJavaScriptEnabled(true);
        //允许WebView访问文件数据
        settings.setAllowFileAccess(true);
        // 支持内容缩放控制
        settings.setBuiltInZoomControls(false);
        //编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        //设置自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        //应用可以有数据库
        settings.setDatabaseEnabled(false);
        //应用可以有缓存
        settings.setAppCacheEnabled(true);
        String appCashDir = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCashDir);
        expProWb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && expProWb.canGoBack()) {
                    expProWb.goBack();
                    return true;
                }
                return false;
            }
        });
        expProWb.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
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
        });
        expProWb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //有的手机不走此方法，故在onPageFinished中也添加了设置
            }

            /** 加载数据的进度变化 */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (expProProgressBar != null) {
                    if (newProgress != 100) {
                        expProProgressBar.setVisibility(View.VISIBLE);
                    }
                    expProProgressBar.setProgress(newProgress);

                    if (expProProgressBar.getProgress() == 100) {
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

                super.onProgressChanged(view, newProgress);
            }

            /** 处理HTML的对话框 */
            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                return true;
            }

        });

        expProWb.loadUrl(url);

    }

    private Handler progressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgId = msg.what;
            if (msgId == 1) {
                if (expProProgressBar != null) {
                    expProProgressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    public int getContentViewResId() {
        return R.layout.activity_exp_and_pro_web_view;
    }

    /**
     * 启动职位列表页面
     *
     * @param context
     * @param url
     * @param title
     */
    public static void startAction(Context context, String url, String title) {
        Intent intent = new Intent(context, ExpAndProWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

}
