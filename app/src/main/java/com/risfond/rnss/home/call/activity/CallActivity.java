package com.risfond.rnss.home.call.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.home.call.modelImpl.CallImpl;
import com.risfond.rnss.home.call.modelInterface.ICall;
import com.risfond.rnss.home.call.widget.Area;
import com.risfond.rnss.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class CallActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.iv_call_close)
    ImageView ivCallClose;
    @BindView(R.id.rb_line1)
    RadioButton rbLine1;
    @BindView(R.id.rb_line2)
    RadioButton rbLine2;
    @BindView(R.id.rb_line3)
    RadioButton rbLine3;
    @BindView(R.id.rb_line4)
    RadioButton rbLine4;
    @BindView(R.id.rb_phone)
    RadioButton rbPhone;
    @BindView(R.id.rb_landline)
    RadioButton rbLandline;
    @BindView(R.id.tv_show_more)
    TextView tvShowMore;
    @BindView(R.id.rb_area1)
    RadioButton rbArea1;
    @BindView(R.id.rb_area2)
    RadioButton rbArea2;
    @BindView(R.id.rb_area3)
    RadioButton rbArea3;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.ll_call)
    LinearLayout llCall;

    private Context context;
    private String areacode;
    private String transfernumber;
    private String usnumber;
    private String areacodeid;
    private String linecode;
    private Area area;
    private ICall iCall;
    private Map<String, String> request;

    private String MobileNumber;
    private String TelNumber;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_call;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setFinishOnTouchOutside(false);
        context = CallActivity.this;

        transfernumber = getIntent().getStringExtra("phone");
        etPhone.setText(transfernumber);

        iCall = new CallImpl();
        request = new HashMap<>();

        MobileNumber = SPUtil.loadMobileNumber(context);
        TelNumber = SPUtil.loadTelNumber(context);

        rbPhone.setText(MobileNumber);
        rbLandline.setText(TelNumber);

        chinaCode();
        linecode = "1";


        if (MobileNumber.equals("")) {
            rbPhone.setVisibility(View.GONE);
            rbLandline.setChecked(true);
            usnumber = TelNumber;
        } else if (TelNumber.equals("")) {
            rbLandline.setVisibility(View.GONE);
            rbPhone.setChecked(true);
            usnumber = MobileNumber;
        } else {
            usnumber = MobileNumber;
        }
    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "拨打电话中...");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("areacode", areacode);
        request.put("transfernumber", transfernumber);
        request.put("usnumber", usnumber);
        request.put("areacodeid", areacodeid);
        request.put("linecode", linecode);
        iCall.callRequest(SPUtil.loadToken(context), request, URLConstant.URL_CALL_PHONE, this);
    }

    @OnClick({R.id.tv_show_more, R.id.btn_confirm, R.id.iv_call_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show_more:
                startActivityForResult(new Intent(CallActivity.this, AreaListActivity.class), 2008);
                break;
            case R.id.btn_confirm:
                transfernumber = etPhone.getText().toString().trim();
                if (transfernumber.length() > 0) {
                    requestService();
                } else {
                    ToastUtil.showShort(context, "请输入对方号码");
                }
                break;
            case R.id.iv_call_close:
                finish();
                break;
        }

    }

    @OnCheckedChanged({R.id.rb_line1, R.id.rb_line2, R.id.rb_line3, R.id.rb_line4,
            R.id.rb_phone, R.id.rb_landline, R.id.rb_area1, R.id.rb_area2, R.id.rb_area3,})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_line1:
                if (isChecked) {
                    linecode = "1";
                }
                break;
            case R.id.rb_line2:
                if (isChecked) {
                    linecode = "2";
                }
                break;
            case R.id.rb_line3:
                if (isChecked) {
                    linecode = "3";
                }
                break;
            case R.id.rb_line4:
                if (isChecked) {
                    linecode = "4";
                }
                break;
            case R.id.rb_phone:
                if (isChecked) {
                    usnumber = SPUtil.loadMobileNumber(context);
                }
                break;
            case R.id.rb_landline:
                if (isChecked) {
                    usnumber = SPUtil.loadTelNumber(context);
                }
                break;
            case R.id.rb_area1:
                if (isChecked) {
                    chinaCode();
                }
                break;
            case R.id.rb_area2:
                if (isChecked) {
                    HongKongCode();
                }
                break;
            case R.id.rb_area3:
                if (isChecked) {
                    if (area == null) {
                        USCode();
                    } else {
                        selectCode();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void chinaCode() {
        areacode = "86";
        areacodeid = "1";
    }

    private void HongKongCode() {
        areacode = "852";
        areacodeid = "2";
    }

    private void USCode() {
        areacode = "1";
        areacodeid = "7";
    }

    private void selectCode() {
        areacode = area.getAreacode().split("\\+")[1];
        areacodeid = area.getAreacodeid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2008) {
            area = data.getParcelableExtra("area");
            selectCode();
            if (areacode.equals("86")) {
                rbArea1.setChecked(true);
            } else if (areacode.equals("852")) {
                rbArea2.setChecked(true);
            } else {
                rbArea3.setChecked(true);
                rbArea3.setText(area.getAreacode());
            }
        }
    }

    public static void startAction(Context context, String phone) {
        Intent intent = new Intent(context, CallActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                DialogUtil.getInstance().showCallMsg(context);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.getInstance().closeCallMsgDialog();
                    }
                }, 3 * 1000);
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (str.equals("成功")) {
                    ToastUtil.showShort(context, "呼叫失败");
                } else {
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }

    @Override
    public void onError(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画
        overridePendingTransition(0, 0);
    }
}
