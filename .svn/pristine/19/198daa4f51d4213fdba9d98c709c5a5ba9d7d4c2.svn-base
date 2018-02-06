package com.risfond.rnss.home.commonFuctions.dynamics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.home.commonFuctions.dynamics.modelimpl.PublishImpl;
import com.risfond.rnss.home.commonFuctions.dynamics.modelinterface.IPublish;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布职位动态页面
 */
public class PublishingDynamicsActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_publish)
    LinearLayout llTitlePublish;
    @BindView(R.id.et_message)
    EditText etMessage;

    private Context context;
    private Map<String, String> request = new HashMap<>();
    private IPublish iPublish;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_publishing_dynamics;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = PublishingDynamicsActivity.this;
        tvTitle.setText("发布动态");
        iPublish = new PublishImpl();

    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "发布中...");
        request.put("staffId", String.valueOf(SPUtil.loadId(context)));
        request.put("content", etMessage.getText().toString().trim());
        request.put("categroy", "1");
        iPublish.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_ADD_INTERACTION, this);
    }

    @OnClick({R.id.ll_title_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_publish:
                if (TextUtils.isEmpty(etMessage.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请输入要发布的动态");
                } else {
                    requestService();
                }
                break;
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, PublishingDynamicsActivity.class);
        context.startActivity(intent);
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof String) {
                    String data = obj.toString();
                    if (data.equals("success")) {
                        ToastUtil.showShort(context, "发布成功");
                        setResult(999);
                        finish();
                    } else {
                        ToastUtil.showShort(context, data);
                    }
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }
}
