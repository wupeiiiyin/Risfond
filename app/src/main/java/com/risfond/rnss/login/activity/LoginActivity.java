package com.risfond.rnss.login.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.em.EMHelper;
import com.risfond.rnss.common.utils.AndroidUtils;
import com.risfond.rnss.common.utils.CountDownTextViewUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.entry.Login;
import com.risfond.rnss.login.modleImpl.LoginImpl;
import com.risfond.rnss.login.modleImpl.VerificationCodeImpl;
import com.risfond.rnss.login.modleInterface.ILogin;
import com.risfond.rnss.login.modleInterface.IVerificationCode;
import com.risfond.rnss.message.activity.MainActivity;
import com.risfond.rnss.register.activity.RegisterFirstActivity;
import com.risfond.rnss.register.activity.RegisterSecondActivity;
import com.risfond.rnss.register.activity.RegisterThirdActivity;
import com.risfond.rnss.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements ResponseCallBack, CountDownTextViewUtil.ICountDownListener {
    private Context context;
    private String account;
    private String pwd;
    private ILogin iLogin;
    private IVerificationCode iVerificationCode;
    private Map<String, String> request;
    private String type;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例

    private CountDownTextViewUtil countDown;

    @BindView(R.id.et_user)
    ClearEditText etUser;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.content)
    View content;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);
        context = LoginActivity.this;
        iLogin = new LoginImpl();
        iVerificationCode = new VerificationCodeImpl();
        request = new HashMap<>();

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkLoginText();
                }
                return false;
            }
        });

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3

        countDown = new CountDownTextViewUtil(tvVerificationCode, 60, 1, this);

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = content.getBottom() - bottom - 100;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(50);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomIn(logo, dist);
                    }

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((content.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(50);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        zoomOut(logo);
                    }
                }

            }
        });
    }


    /**
     * 开始登录请求
     */
    private void requestLoginService() {
        request.put("account", account);
        request.put("code", pwd);
        request.put("id", AndroidUtils.getIP(context));
        request.put("systems",AndroidUtils.getSystemModel());
        request.put("platforms","android");
        DialogUtil.getInstance().showLoadingDialog(context, "登录中...");
        iLogin.loginRequest(request, "", this);
        Log.e("TAGAAA", URLConstant.URL_LOGIN+"=======================");
        String s = account + pwd + AndroidUtils.getIP(context) + AndroidUtils.getSystemModel() + "android";
        Log.e("TAG:", "requestLoginService: "+s );
    }

    /**
     * 获取验证码请求
     */
    private void requestCodeService() {
        request.put("account", account);
        DialogUtil.getInstance().showLoadingDialog(context, "获取验证码中...");
        iVerificationCode.VerificationCodeRequest(request, "", this);
    }

    /**
     * 检查输入信息
     */
    private void checkLoginText() {
        account = etUser.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            if (TextUtils.isEmpty(account)) {
                ToastUtil.showShort(context, "请输入用户名");
            } else {
                ToastUtil.showShort(context, "请输入验证码");
            }
        } else {
            if (NetUtil.isConnected(context)) {
                //登录请求
                requestLoginService();
            } else {
                ToastUtil.showShort(context, "请检查网络连接");
            }

        }
    }

    /**
     * 登录环信聊天
     */
    private void loginChat(final Object obj) {
        final Login login = (Login) obj;
        EMClient.getInstance().login(login.getEaseMobAccount(), login.getEaseMobPwd(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMHelper.getInstance().updateChatInfo();
                updateUI(login);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.getInstance().closeLoadingDialog();
                        if (code == 200) {
                            EMHelper.getInstance().updateChatInfo();
                            updateUI(login);
                        } else {
                            ToastUtil.showShort(context, "聊天登录失败");
                        }
                    }
                });
            }
        });

    }

    private void updateUI(final Login login) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countDown.cancel();
                DialogUtil.getInstance().closeLoadingDialog();
                SPUtil.saveLoginMsg(context, account, pwd, login);
                MainActivity.startAction(context);
                finish();
            }
        });

    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_verification_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                type = "login";
                checkLoginText();
                break;
            case R.id.tv_register:
                RegisterFirstActivity.startAction(context);
                break;
            case R.id.tv_verification_code:
                type = "code";
                checkPhoneNumber();
                break;
            default:
                break;
        }
    }

    private void checkPhoneNumber() {
        account = etUser.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShort(context, "请输入用户名");
        } else {
            requestCodeService();
        }
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight() / 2);
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist / 2);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(100);
        mAnimatorSet.start();
    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        view.setPivotY(view.getHeight() / 2);
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(100);
        mAnimatorSet.start();

    }

    /**
     * 启动登录页面
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDown.cancel();
    }

    @Override
    public void onSuccess(Object obj) {
        if (type.equals("login")) {
            loginChat(obj);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvVerificationCode.setTextColor(ContextCompat.getColor(context, R.color.color_login_input_hint));
                    countDown.start();
                    DialogUtil.getInstance().closeLoadingDialog();
                    ToastUtil.showShort(context, "验证码已发送");
                }
            });
        }
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                ToastUtil.showShort(context, str);
            }
        });
    }

    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                ToastUtil.showShort(context, str);
            }
        });
    }

    @Override
    public void onCountDownFinish() {
        tvVerificationCode.setEnabled(true);
        tvVerificationCode.setTextColor(ContextCompat.getColor(context, R.color.color_verification_code));
        tvVerificationCode.setText("获取验证码");
    }
}
