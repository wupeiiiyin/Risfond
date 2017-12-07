package com.risfond.rnss.home.commonFuctions.successCase.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.share.ShareUtil;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.StatusBarUtils;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.Evaluate;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsDetailActivity;
import com.risfond.rnss.home.js.JsToJava;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessCaseMainActivity extends BaseActivity {
    @BindView(R.id.wv_resume_detail)
    WebView wvResumeDetail;
    @BindView(R.id.pb_resume_detail)
    ProgressBar pbResumeDetail;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_share)
    LinearLayout llTitleShare;
    @BindView(R.id.ll_title_search)
    LinearLayout tvResumeSearch;
    @BindView(R.id.iv_title_right)
    ImageView mRightIcon;

    @BindView(R.id.id_successcase_main_title)
    LinearLayout mTitleBarRootView;
    @BindView(R.id.id_title_rootview)
    RelativeLayout id_title_rootview;
    private Context context;
    private String url;
    private String id;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_success_case_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = SuccessCaseMainActivity.this;
        initTitleBar();

        url = URLConstant.URL_SUCCESSCASE__H5;
        initWebView();
        EventBusUtil.registerEventBus(this);
    }

    private void initTitleBar() {
        tvTitle.setVisibility(View.INVISIBLE);
        mRightIcon.setImageResource(R.mipmap.rs_successcase_list);
        tvResumeSearch.setVisibility(View.VISIBLE);
        // Color.parseColor("#135dbd")
        int colors[] = {Color.argb(146,19,93,189), Color.parseColor("#26a4ee")};
        final GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors);
        mTitleBarRootView.setBackgroundResource(R.mipmap.rs_successcase_title);
        id_title_rootview.setBackgroundColor(Color.TRANSPARENT);
        StatusBarUtils.setTransparentForWindow(this);
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        if (contentView.getChildCount() > 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.getChildAt(1).setBackground(bg);
            } else {
                mTitleBarRootView.setBackgroundDrawable(bg);
                contentView.getChildAt(1).setBackgroundDrawable(bg);
            }
        }
    }

    @OnClick(R.id.iv_title_right)
    public void onClick(View v) {
        SuccessCaseActivity.StartAction(this);
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
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置可以使用
        settings.setDomStorageEnabled(true);
        //应用可以有数据库
        settings.setDatabaseEnabled(false);
        //应用可以有缓存
        settings.setAppCacheEnabled(false);

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
        wvResumeDetail.addJavascriptInterface(new SuccessCaseJs(), "AndroidWebView");

    }

    @Subscribe
    public void onEventBus(final Evaluate evaluate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (evaluate != null) {
                    llTitleShare.setVisibility(View.VISIBLE);
                } else {
                    llTitleShare.setVisibility(View.GONE);
                }

                llTitleShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareUtil.showShare(context, evaluate.getTitle(), evaluate.getDetails(), evaluate.getImg(), evaluate.getUrl());
                    }
                });
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SuccessCaseMainActivity.class);
        context.startActivity(intent);
    }

    public class SuccessCaseJs{

        @JavascriptInterface
        public void seeDetail(String id) {
            if (TextUtils.isEmpty(id)) {
                return;
            }else{
                Intent intent = new Intent(SuccessCaseMainActivity.this, SuccessCaseActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        }
    }
}
