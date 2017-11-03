package com.risfond.rnss.home.position.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.home.js.JsToJava;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/7/13.
 * 职位详情H5页面
 */

public class PositionDetailFragment extends BaseFragment {

    @BindView(R.id.wv_resume_detail)
    WebView wvResumeDetail;
    @BindView(R.id.pb_resume_detail)
    ProgressBar pbResumeDetail;

    private Context context;
    private String url;
    private String id;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_position_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        id = getArguments().getString("id");
        url = URLConstant.URL_JOB_DETAIL_H5 + "?token=" + SPUtil.loadToken(context) + "&id=" + id;
        initWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initWebView() {
        WebSettings settings = wvResumeDetail.getSettings();
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
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置可以使用
        settings.setDomStorageEnabled(true);
        //应用可以有数据库
        settings.setDatabaseEnabled(false);
        //应用可以有缓存
        settings.setAppCacheEnabled(false);
        wvResumeDetail.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wvResumeDetail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && wvResumeDetail.canGoBack()) {
                    wvResumeDetail.goBack();
                    return true;
                }
                return false;
            }
        });
        wvResumeDetail.setWebViewClient(new WebViewClient() {

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
        wvResumeDetail.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //有的手机不走此方法，故在onPageFinished中也添加了设置
            }

            /** 加载数据的进度变化 */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
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

                super.onProgressChanged(view, newProgress);
            }

            /** 处理HTML的对话框 */
            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                showDialog(message, result);
                return true;
            }

        });

        wvResumeDetail.loadUrl(url);
        wvResumeDetail.addJavascriptInterface(new JsToJava(context, SPUtil.loadToken(context)), "AndroidWebView");

    }

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

    private void showDialog(String msg, final JsResult result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                result.confirm();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                result.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void lazyLoad() {

    }
}
