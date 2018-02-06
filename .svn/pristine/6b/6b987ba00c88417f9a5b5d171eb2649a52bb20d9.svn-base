package com.risfond.rnss.home.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
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
import com.risfond.rnss.home.customer.fragment.CustomerInfoFragment;
import com.risfond.rnss.home.customer.fragment.CustomerRecordFragment;
import com.risfond.rnss.home.customer.modelImpl.CustomerAuthenticationImpl;
import com.risfond.rnss.home.customer.modelImpl.CustomerDetailImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerAuthentication;
import com.risfond.rnss.home.customer.modelInterface.ICustomerDetail;
import com.risfond.rnss.home.position.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的客户详情
 */
public class CustomDetailActivity2 extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.iv_authentication)
    ImageView ivAuthentication;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tl_custom_top)
    TabLayout tlCustomTop;
    @BindView(R.id.vp_custom_detail)
    ViewPager vpCustomDetail;
    @BindView(R.id.iv_huifang)
    ImageView mHuifang;

    private Context context;
    private ICustomerDetail iCustomerDetail;
    private ICustomerAuthentication iCustomerAuthentication;
    private Map<String, String> request = new HashMap<>();
    private Map<String, String> request2 = new HashMap<>();
    private String id;
    private String url;
    private CustomerDetail customerDetail;
    private CustomerAuthenticationResponse authenticationResponse;
    private MyPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    private CustomerInfoFragment infoFragment;
    private CustomerRecordFragment recordFragment;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_custom_detail2;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = CustomDetailActivity2.this;
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        tvTitle.setText("客户详情");
        iCustomerDetail = new CustomerDetailImpl();
        iCustomerAuthentication = new CustomerAuthenticationImpl();

        id = getIntent().getStringExtra("id");
        url = getIntent().getStringExtra("url");

        tabNames.add("基本信息");
        tabNames.add("回访记录");

        customDetailRequest();
    }

    /**
     * 请求客户详情
     */
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

    private void initViewData() {
        if (customerDetail != null) {
            tvCode.setText(customerDetail.getCode());
            tvName.setText(customerDetail.getName());
            tvIndustry.setText(customerDetail.getIndustry());
            tvAddress.setText(customerDetail.getAddress());
            authenticationStatus(customerDetail.getCertificationStatus());
            ivAuthentication.setVisibility(View.VISIBLE);
            initTab();
        } else {
            ivAuthentication.setVisibility(View.INVISIBLE);
        }
    }

    private void changeAuthenticationStatus() {
        if (authenticationResponse != null) {
            if (authenticationResponse.isStatus()) {
                ivAuthentication.setImageResource(R.mipmap.iconyirenzheng);
            } else {
                ToastUtil.showShort(context, authenticationResponse.getMessage());
            }
        }
    }

    /**
     * 企业认证状态
     *
     * @param status
     */
    private void authenticationStatus(int status) {
        if (status == 1) {
            ivAuthentication.setImageResource(R.mipmap.iconyirenzheng);
        } else {
            ivAuthentication.setImageResource(R.mipmap.icondianjirenzheng);
        }
    }

    private void initTab() {
        fragments = new ArrayList<>();
        infoFragment = new CustomerInfoFragment();
        Bundle infoBundle = new Bundle();
        infoBundle.putString("id", id);
        infoFragment.setArguments(infoBundle);
        fragments.add(infoFragment);

        if (recordFragment == null) {
            recordFragment = new CustomerRecordFragment();
        }
        //        Bundle recordBundle = new Bundle();
        if (customerDetail != null) {
            //            recordBundle.putParcelableArrayList("data", customerDetail.getFHlist());
            //            recordFragment.setArguments(recordBundle);
            recordFragment.setData(customerDetail.getFHlist());
        }
        fragments.add(recordFragment);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpCustomDetail.setAdapter(pagerAdapter);
        tlCustomTop.setupWithViewPager(vpCustomDetail);
        vpCustomDetail.setCurrentItem(1);
    }

    @OnClick({R.id.iv_authentication, R.id.iv_huifang})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_authentication://认证
                if (customerDetail.getCertificationStatus() != 1) {
                    authenticationRequest();
                }
                break;
            case R.id.iv_huifang:
                ReturnFollowActivity.StartAction(CustomDetailActivity2.this, id + "");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            customDetailRequest();
        }
    }

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Context context, String id, String url) {
        Intent intent = new Intent(context, CustomDetailActivity2.class);
        intent.putExtra("id", id);
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
                    initViewData();
                } else if (obj instanceof CustomerAuthenticationResponse) {
                    authenticationResponse = (CustomerAuthenticationResponse) obj;
                    changeAuthenticationStatus();
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
                initTab();
            }
        });
    }

    @Override
    public void onError(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                initTab();
            }
        });
    }

}
