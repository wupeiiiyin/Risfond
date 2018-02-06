package com.risfond.rnss.home.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.CustomerAuthenticationResponse;
import com.risfond.rnss.entry.CustomerDetail;
import com.risfond.rnss.home.commonFuctions.publicCustomer.activity.ClientApplicationActivity;
import com.risfond.rnss.home.customer.adapter.CustomerDetailAdapter;
import com.risfond.rnss.home.customer.modelImpl.CustomerAuthenticationImpl;
import com.risfond.rnss.home.customer.modelImpl.CustomerDetailImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerAuthentication;
import com.risfond.rnss.home.customer.modelInterface.ICustomerDetail;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 公共客户详情
 */
public class CustomDetailActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_customer_list)
    RecyclerView rvCustomerList;
    @BindView(R.id.ll_right_text)
    LinearLayout mLLTitleright;
    @BindView(R.id.tv_right_text)
    TextView mTitleright;

    private Context context;
    private CustomerDetailAdapter customerAdapter;
    private ICustomerDetail iCustomerDetail;
    private Map<String, String> request = new HashMap<>();
    private ICustomerAuthentication iCustomerAuthentication;
    private Map<String, String> request2 = new HashMap<>();
    private CustomerAuthenticationResponse authenticationResponse;
    private int authenticationPosition;
    private String id;
    private String url;
    private CustomerDetail customerDetail;
    private String mOptionStatus;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_custom_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = CustomDetailActivity.this;
        customerDetail = new CustomerDetail();
        mLLTitleright.setVisibility(View.VISIBLE);
        tvTitle.setText("客户详情");
        iCustomerDetail = new CustomerDetailImpl();
        iCustomerAuthentication = new CustomerAuthenticationImpl();

        id = getIntent().getStringExtra("id");
        mOptionStatus = getIntent().getStringExtra("OptionStatus");
        if (mOptionStatus.equals("2")) {
            mTitleright.setText("已申请");
        } else {
            mTitleright.setText("申请转入");
        }
        url = getIntent().getStringExtra("url");

        rvCustomerList.setLayoutManager(new LinearLayoutManager(context));
        customerAdapter = new CustomerDetailAdapter(context, customerDetail);
        rvCustomerList.setAdapter(customerAdapter);

        customDetailRequest();

        onAuthenticationClick();
    }

    /**
     * 企业认证
     */
    private void onAuthenticationClick() {
        customerAdapter.setOnAuthenticationListener(new CustomerDetailAdapter.OnAuthenticationListener() {
            @Override
            public void OnAuthenticationClick(View view, int position) {
                authenticationPosition = position;
                authenticationRequest();
            }
        });
    }

    private void customDetailRequest() {
        DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        request.put("id", id);
        iCustomerDetail.customerDetailRequest(SPUtil.loadToken(context), request, url, this);
    }

    /**
     * 请求客户认证
     */
    private void authenticationRequest() {
        DialogUtil.getInstance().showLoadingDialog(context, "认证中...");
        request2.put("clientId", String.valueOf(customerDetail.getClientId()));
        iCustomerAuthentication.customerAuthenticationRequest(SPUtil.loadToken(context), request2,
                URLConstant.URL_REFRESH_CLIENT_STATUE, this);
    }

    @OnClick({R.id.ll_right_text})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_right_text:
                if (!mOptionStatus.equals("2")) {
                    ClientApplicationActivity.StartAction(context, id, mOptionStatus + "");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Context context, String id, String mOptionStatus, String url) {
        Intent intent = new Intent(context, CustomDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("OptionStatus", mOptionStatus);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof CustomerDetail) {
                    customerDetail = (CustomerDetail) obj;
//                    customerAdapter = new CustomerDetailAdapter(context, customerDetail);
//                    rvCustomerList.setAdapter(customerAdapter);
                    customerAdapter.updateData(customerDetail);
                } else if (obj instanceof CustomerAuthenticationResponse) {
                    authenticationResponse = (CustomerAuthenticationResponse) obj;
                    if (authenticationResponse.isStatus()) {
                        customerDetail.setCertificationStatus(1);
                        customerAdapter.updateData(customerDetail);
                    } else {
                        ToastUtil.showShort(context, authenticationResponse.getMessage());
                    }
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });
    }

    @Override
    public void onFailed(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
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
}
