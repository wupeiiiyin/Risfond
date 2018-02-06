package com.risfond.rnss.register.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CountDownTextViewUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.RegisterResponse;
import com.risfond.rnss.register.modelimpl.RegisterImpl;
import com.risfond.rnss.register.modelimpl.RegisterVerificationCodeImpl;
import com.risfond.rnss.register.modelinterface.IRegister;
import com.risfond.rnss.register.modelinterface.IRegisterVerificationCode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFirstActivity extends BaseActivity implements CountDownTextViewUtil.ICountDownListener,
        ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_title)
    RelativeLayout llTitle;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;
    @BindView(R.id.et_QQ)
    EditText etQQ;
    @BindView(R.id.et_weChat)
    EditText etWeChat;
    @BindView(R.id.et_mail)
    EditText etMail;

    private Context context;
    private String mobile, verificationCode, QQ, weChat, mail;
    private CountDownTextViewUtil countDown;

    private Map<String, String> request = new HashMap<>();
    private Map<String, String> request2 = new HashMap<>();
    private IRegister iRegister;
    private IRegisterVerificationCode iRegisterVerificationCode;
    private int operateType = 0;//0获取验证码，1提交

    @Override
    public int getContentViewResId() {
        return R.layout.activity_register_first;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RegisterFirstActivity.this;
        tvTitle.setText(R.string.regist_title1);
        tvNext.setText(R.string.register_next);

        countDown = new CountDownTextViewUtil(tvVerificationCode, 60, 1, this);

        iRegister = new RegisterImpl();
        iRegisterVerificationCode = new RegisterVerificationCodeImpl();
    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "上传中");
        request.put("Phone", mobile);
        request.put("Code", verificationCode);
        request.put("QQ", QQ);
        request.put("WeiXin", weChat);
        request.put("Email", mail);
        iRegister.registerRequest("", request, URLConstant.URL_CHECK_MESS, this);
    }

    private void requestVerificationCode() {
        DialogUtil.getInstance().showLoadingDialog(context, "获取验证码...");
        request2.put("Phone", mobile);
        iRegisterVerificationCode.registerVerificationCodeRequest("", request2, URLConstant.URL_GET_MESSAGE, this);
    }

    @OnClick({R.id.tv_next, R.id.tv_verification_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                operateType = 1;
                ImeUtil.hideSoftKeyboard(etCode);
                submit();
                break;
            case R.id.tv_verification_code:
                operateType = 0;
                mobile = etMobile.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showShort(context, "请输入手机号");
                } else {
                    requestVerificationCode();
                }
                break;
        }
    }

    private void submit() {
        mobile = etMobile.getText().toString().trim();
        verificationCode = etCode.getText().toString().trim();
        QQ = etQQ.getText().toString().trim();
        weChat = etWeChat.getText().toString().trim();
        mail = etMail.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showShort(context, "请输入手机号");
        } else if (TextUtils.isEmpty(verificationCode)) {
            ToastUtil.showShort(context, "请输入验证码");
        } else if (TextUtils.isEmpty(QQ)) {
            ToastUtil.showShort(context, "请输入QQ号");
        } else if (TextUtils.isEmpty(weChat)) {
            ToastUtil.showShort(context, "请输入微信号");
        } else if (TextUtils.isEmpty(mail)) {
            ToastUtil.showShort(context, "请输入个人邮箱");
        } else {
            requestService();
        }


    }

    @Override
    public void onCountDownFinish() {
        if (tvVerificationCode != null) {
            tvVerificationCode.setEnabled(true);
            tvVerificationCode.setTextColor(ContextCompat.getColor(context, R.color.color_resume_blue_txt));
            tvVerificationCode.setText("获取验证码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDown.cancel();
    }

    private void updateUI(final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (o instanceof RegisterResponse) {
                    if (operateType == 0) {
                        countDown.start();
                        tvVerificationCode.setTextColor(ContextCompat.getColor(context, R.color.color_login_input_hint));
                        ToastUtil.showShort(context, "验证码已发送");
                    } else {
                        RegisterSecondActivity.startAction(context, ((RegisterResponse) o).getData());
                    }
                } else {
                    ToastUtil.showShort(context, o.toString());
                }

            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, RegisterFirstActivity.class);
        context.startActivity(intent);
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
