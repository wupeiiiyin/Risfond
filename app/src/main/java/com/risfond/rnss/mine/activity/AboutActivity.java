package com.risfond.rnss.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.BuildConfig;
import com.risfond.rnss.R;
import com.risfond.rnss.application.MyApplication;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.update.UpdateManager;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    private Context context;
    private UpdateManager updateManager;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_check_update)
    TextView tvCheckUpdate;
    @BindView(R.id.tv_clear_cash)
    TextView tvClearCash;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = AboutActivity.this;
        MyApplication myInstance = MyApplication.getMyInstance();

        tvTitle.setText("关于锐仕方达");
        tvVersionName.setText(BuildConfig.VERSION_NAME);
        updateManager = new UpdateManager(context, true);

    }

    @OnClick({R.id.tv_check_update, R.id.tv_clear_cash})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check_update:
                updateManager.checkUpdate();
                break;
            case R.id.tv_clear_cash:
                DialogUtil.getInstance().showConfigDialog(context, "是否清除缓存？", "是", "否", new DialogUtil.PressCallBack() {
                    @Override
                    public void onPressButton(int buttonIndex) {
                        if (buttonIndex == DialogUtil.BUTTON_OK) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    GlideUtil.clearCacheDiskSelf(context);
                                    GlideUtil.clearCacheMemory(context);
                                }
                            }).start();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 启动关于页面
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

}
