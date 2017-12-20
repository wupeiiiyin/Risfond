package com.risfond.rnss.home.position.activity;

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
import com.risfond.rnss.home.position.adapter.MyPagerAdapter;
import com.risfond.rnss.home.position.fragment.PositionDetailFragment;
import com.risfond.rnss.home.position.fragment.RecommendInfoFragment;
import com.risfond.rnss.home.position.fragment.RecommendManagementFragment;
import com.risfond.rnss.home.position.fragment.RecommendProcessFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐信息页面
 */
public class RecommendInfoActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tl_recommend_top)
    TabLayout tlRecommendTop;
    @BindView(R.id.vp_recommend_info)
    ViewPager vpRecommendInfo;
    @BindView(R.id.iv_liuCheng)
    ImageView ivLiuCheng;

    private Context context;
    private MyPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    private String Recomid;
    private String type;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_recommend_info;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RecommendInfoActivity.this;
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();
        tvTitle.setText("推荐信息");

        Recomid = getIntent().getStringExtra("Recomid");
        type = getIntent().getStringExtra("type");

        RecommendInfoFragment recommendInfoFragment = new RecommendInfoFragment();
        Bundle detailBundle = new Bundle();
        detailBundle.putString("id", Recomid);
        recommendInfoFragment.setArguments(detailBundle);
        fragments.add(recommendInfoFragment);

        RecommendProcessFragment recommendFragment = new RecommendProcessFragment();
        Bundle recommendBundle = new Bundle();
        recommendBundle.putString("Recomid", Recomid);
        recommendFragment.setArguments(recommendBundle);
        fragments.add(recommendFragment);

        tabNames.add("推荐信息");
        tabNames.add("流程管理");

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpRecommendInfo.setAdapter(pagerAdapter);
        tlRecommendTop.setupWithViewPager(vpRecommendInfo);

        vpRecommendInfo.setCurrentItem(1);

        ivLiuCheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProcessNextActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("JobCandidateId", Recomid);
                startActivityForResult(intent, 998);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 998) {
            if (resultCode == 998) {
                type = data.getStringExtra("type");
            }
        }
    }

    public static void startAction(Context context, String Recomid, String type) {
        Intent intent = new Intent(context, RecommendInfoActivity.class);
        intent.putExtra("Recomid", Recomid);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

}
