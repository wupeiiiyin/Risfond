package com.risfond.rnss.home.customer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.entry.MyLeaveResponse;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelImpl.MyLeaveImpl;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelInterface.ILeave;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by vicky on 2017/8/11.
 */

public class ReturnFollowActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_leave_reason)
    EditText metLeaveReason;
    @BindView(R.id.rl_leave_reason)
    RelativeLayout mrlLeaveReason;
    @BindView(R.id.ll_back)
    LinearLayout mll_back;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_title)
    TextView mtvTitle;

    private Context context;
    private Map<String, String> request = new HashMap<>();

    private ILeave mILeave;
    private MyLeaveResponse responseLeave;

    private String itemId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.rl_leave_reason, R.id.ll_back, R.id.btn_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_leave_reason:
                metLeaveReason.requestFocus();
                showSoftKeyboard(metLeaveReason);
                break;

            case R.id.ll_back:
                hideSoftKeyboard(mll_back);
                finish();
                break;

            case R.id.btn_ok:
                leaveRequest();
                hideSoftKeyboard(mBtnOk);
                break;
            default:
                break;
        }
    }

    private void leaveRequest() {
        request = new HashMap<>();

        request.put("clientId",itemId+"");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));

        if (!TextUtils.isEmpty(metLeaveReason.getText().toString().trim())) {
            request.put("contents", metLeaveReason.getText().toString().trim());
        } else {
            ToastUtil.showShortCent(context, "回访记录不能为空");
            return;
        }


        DialogUtil.getInstance().closeLoadingDialog();
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        mILeave.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_RETURN_FOLLOW, this);
    }

    private void showSoftKeyboard(EditText view) {
        if (view != null && context != null) {
            ((InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                    InputMethodManager.SHOW_FORCED);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_client_application;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        statusBar();
        context = ReturnFollowActivity.this;
        mtvTitle.setText("回访跟进");
        mILeave = new MyLeaveImpl();
        itemId = getIntent().getStringExtra("itemId");

        initEdie();
    }

    private void statusBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            titleMargin();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            titleMargin();
        }
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);

    }

    private void initEdie() {
        metLeaveReason.addTextChangedListener(contentTextWatcher);
    }

    public static void StartAction(Activity context, String itemId) {
        Intent intent = new Intent(context, ReturnFollowActivity.class);
        intent.putExtra("itemId",itemId);
        context.startActivityForResult(intent,1000);
    }

    //   content 4000字以内
    private TextWatcher contentTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 4000) {
                ToastUtil.showShortCent(context, "不能超过4000字符");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            editStart = metLeaveReason.getSelectionStart();
            editEnd = metLeaveReason.getSelectionEnd();
            if (temp.length() > 4000) {
                editable.delete(editEnd - (temp.length() - 4000), editEnd);
                int tempSelection = editStart;
                metLeaveReason.setText(editable);
                metLeaveReason.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof MyLeaveResponse) {
                    responseLeave = (MyLeaveResponse) obj;
                    if (responseLeave != null && responseLeave.isStatus()) {
                        Intent data = new Intent();
                        setResult(1001, data);
                        ToastUtil.showShort(context, responseLeave.getMessage() + "");
                        finish();
                    } else {
                        if (responseLeave != null && responseLeave.getMessage() != null) {
                            ToastUtil.showShort(context, responseLeave.getMessage() + "");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (str != null) {
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }

    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (str != null) {
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }
}
