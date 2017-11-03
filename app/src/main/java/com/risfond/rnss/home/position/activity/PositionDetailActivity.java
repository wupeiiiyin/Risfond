package com.risfond.rnss.home.position.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.position.adapter.MyPagerAdapter;
import com.risfond.rnss.home.position.fragment.PositionDetailFragment;
import com.risfond.rnss.home.position.fragment.RecommendManagementFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 职位详情页面
 */
public class PositionDetailActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_position_top)
    TabLayout tlPositionTop;
    @BindView(R.id.vp_position_detail)
    ViewPager vpPositionDetail;

    private MyPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    private String id;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_position_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();
        tvTitle.setText("职位详情");

        id = getIntent().getStringExtra("id");

        PositionDetailFragment detailFragment = new PositionDetailFragment();
        Bundle detailBundle = new Bundle();
        detailBundle.putString("id", id);
        detailFragment.setArguments(detailBundle);
        fragments.add(detailFragment);

        RecommendManagementFragment recommendFragment = new RecommendManagementFragment();
        Bundle recommendBundle = new Bundle();
        recommendBundle.putString("jobid", id);
        recommendFragment.setArguments(recommendBundle);
        fragments.add(recommendFragment);

        tabNames.add("职位详情");
        tabNames.add("推荐管理");

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpPositionDetail.setAdapter(pagerAdapter);
        tlPositionTop.setupWithViewPager(vpPositionDetail);
        vpPositionDetail.setCurrentItem(1);
    }

    public static void startAction(Context context, String id) {
        Intent intent = new Intent(context, PositionDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

}
