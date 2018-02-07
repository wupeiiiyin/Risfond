package com.risfond.rnss.home.commonFuctions.referencemanage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferencePagerAdapter;
import com.risfond.rnss.home.commonFuctions.referencemanage.fragment.ReferenceMangePageFragment;
import com.risfond.rnss.home.position.adapter.MyPagerAdapter;
import com.risfond.rnss.widget.FixNumTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 推荐管理页面
 */
public class ReferenceManageActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.activity_customer)
    LinearLayout activityCustomer;
    @BindView(R.id.tl_position_top)
    FixNumTabLayout tlPositionTop;
    @BindView(R.id.vp_position_detail)
    ViewPager vpPositionDetail;

    private Context context;
    private ReferencePagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_referce_manage;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ReferenceManageActivity.this;
        tvTitle.setText("推荐管理");
        llSearch.setVisibility(View.VISIBLE);
        initlayout();

    }

    private void initlayout() {
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        tabNames.add("1 加入项目");
        tabNames.add("4 推给顾问");
        tabNames.add("2 推给客户");
        tabNames.add("10 预约面试");
        tabNames.add("6 客户面试");
        tabNames.add("8 确认Offer");
        tabNames.add("9 成功入职");
        tabNames.add("3 否决人选");
        tabNames.add("5 人选放弃");
        tabNames.add("7 客户否决");
        tabNames.add("12 人选离职");

        for (int i = 0; i < tabNames.size(); i++) {
            ReferenceMangePageFragment detailFragment = new ReferenceMangePageFragment();
            Bundle detailBundle = new Bundle();
            detailBundle.putString("id", tabNames.get(i).split(" ")[0]);
            detailFragment.setArguments(detailBundle);
            fragments.add(detailFragment);
        }

        pagerAdapter = new ReferencePagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpPositionDetail.setAdapter(pagerAdapter);
        tlPositionTop.setupWithViewPager(vpPositionDetail);
        vpPositionDetail.setCurrentItem(0);
    }

    @OnClick({R.id.ll_title_search})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_title_search) {
            ReferenceManageSearchResultActivity.StartAction(context);
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ReferenceManageActivity.class);
        context.startActivity(intent);
    }
}
