package com.risfond.rnss.home.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.CustomerSearch;
import com.risfond.rnss.entry.CustomerSearchResponse;
import com.risfond.rnss.home.commonFuctions.news.activity.NewsSearchActivity;
import com.risfond.rnss.home.commonFuctions.news.fragment.NewsFragment;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferencePagerAdapter;
import com.risfond.rnss.home.customer.adapter.CustomerSearchAdapter;
import com.risfond.rnss.home.customer.fragment.CustomerFragment;
import com.risfond.rnss.home.customer.modelImpl.CustomerSearchImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 客户首页
 */
public class CustomerSearchActivity extends BaseActivity{
    private ReferencePagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.id_tab_customer)
    TabLayout mTab;
    @BindView(R.id.id_viewpager_customer)
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_customer;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();
        initTitle();
        initlayout();
    }

    private void initTitle() {
        tvTitle.setText("客户");
        llTitleSearch.setVisibility(View.VISIBLE);
    }

    private void initlayout() {
        tabNames.add("我的客户");
        tabNames.add("合作客户");
        tabNames.add("其他客户");

        fragments.add(CustomerFragment.getInstance(CustomerFragment.GUISHU_TYPE_MY_CUSTOM));
        fragments.add(CustomerFragment.getInstance(CustomerFragment.GUISHU_TYPE_COOP_CUSTOM));
        fragments.add(CustomerFragment.getInstance(CustomerFragment.GUISHU_TYPE_OTHER_CUSTOM));

        pagerAdapter = new ReferencePagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        mViewPager.setAdapter(pagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

    }

    @OnClick({R.id.ll_title_search})
    public void onSearch(View v) {
        if (v.getId() == R.id.ll_title_search) {
            CustomerSearchResultActivity.StartAction(this);
        }
    }
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, CustomerSearchActivity.class);
        context.startActivity(intent);
    }



}
