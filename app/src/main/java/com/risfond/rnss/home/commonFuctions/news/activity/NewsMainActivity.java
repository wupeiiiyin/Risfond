package com.risfond.rnss.home.commonFuctions.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.news.fragment.NewsFragment;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferencePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsMainActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.tab_news)
    TabLayout tabNews;
    @BindView(R.id.vp_news)
    ViewPager vpNews;

    private Context context;
    private ReferencePagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    private NewsFragment newsFragment;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_news_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = NewsMainActivity.this;
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        tvTitle.setText("新闻");
        llTitleSearch.setVisibility(View.VISIBLE);
        initlayout();
    }

    @OnClick({R.id.ll_title_search})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_title_search) {
            NewsSearchActivity.StartAction(context);
        }
    }

    private void initlayout() {
        tabNames.add("2 公司新闻");
        tabNames.add("1 媒体报道");
        tabNames.add("5 员工活动");
        tabNames.add("3 企业公告");

        for (int i = 0; i < tabNames.size(); i++) {
            newsFragment = new NewsFragment();
            Bundle detailBundle = new Bundle();
            detailBundle.putString("Category", tabNames.get(i).split(" ")[0]);
            newsFragment.setArguments(detailBundle);
            fragments.add(newsFragment);
        }

        pagerAdapter = new ReferencePagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpNews.setAdapter(pagerAdapter);
        tabNews.setupWithViewPager(vpNews);
        vpNews.setCurrentItem(0);

    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, NewsMainActivity.class);
        context.startActivity(intent);
    }

}
