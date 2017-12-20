package com.risfond.rnss.scan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;

public class ScanActivity extends BaseActivity implements CodeUtils.AnalyzeCallback {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;

    private CaptureFragment captureFragment;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_scan;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        tvTitle.setText("扫码");

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.frame_scan);
        captureFragment.setAnalyzeCallback(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
        bundle.putString(CodeUtils.RESULT_STRING, result);
        resultIntent.putExtras(bundle);
        ScanActivity.this.setResult(RESULT_OK, resultIntent);
        ScanActivity.this.finish();
    }

    @Override
    public void onAnalyzeFailed() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
        bundle.putString(CodeUtils.RESULT_STRING, "");
        resultIntent.putExtras(bundle);
        ScanActivity.this.setResult(RESULT_OK, resultIntent);
        ScanActivity.this.finish();
    }

}
