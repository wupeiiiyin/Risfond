package com.risfond.rnss.home.position.activity;

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
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferencePagerAdapter;
import com.risfond.rnss.home.customer.activity.CustomDetailActivity;
import com.risfond.rnss.home.customer.activity.CustomerSearchActivity;
import com.risfond.rnss.home.customer.activity.CustomerSearchResultActivity;
import com.risfond.rnss.home.customer.adapter.CustomerSearchAdapter;
import com.risfond.rnss.home.customer.fragment.CustomerFragment;
import com.risfond.rnss.home.customer.modelImpl.CustomerSearchImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;
import com.risfond.rnss.home.position.adapter.PositionSearchAdapter;
import com.risfond.rnss.home.position.fragment.PositionFragment;
import com.risfond.rnss.home.position.modelImpl.PositionSearchImpl;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的职位 页面
 */
public class PositionSearchActivity extends BaseActivity  {
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
        return R.layout.activity_position;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();
        initTitle();
        initlayout();
    }

    private void initTitle() {
        tvTitle.setText("职位");
        llTitleSearch.setVisibility(View.VISIBLE);
    }

    private void initlayout() {
        tabNames.add("我的职位");
        tabNames.add("合作职位");
        tabNames.add("其他职位");

        fragments.add(PositionFragment.getInstance(PositionFragment.GUISHU_TYPE_MY_CUSTOM));
        fragments.add(PositionFragment.getInstance(PositionFragment.GUISHU_TYPE_COOP_CUSTOM));
        fragments.add(PositionFragment.getInstance(PositionFragment.GUISHU_TYPE_OTHER_CUSTOM));

        pagerAdapter = new ReferencePagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        mViewPager.setAdapter(pagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

    }

    @OnClick({R.id.ll_title_search})
    public void onSearch(View v) {
        if (v.getId() == R.id.ll_title_search) {
            PositionSearchResultActivity.StartAction(this);
        }
    }
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, PositionSearchActivity.class);
        context.startActivity(intent);
    }
}
