package com.risfond.rnss.home.commonFuctions.workorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.workorder.fragment.WorkOrderFragment;
import com.risfond.rnss.home.position.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WorkOrderActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_order)
    XTabLayout tabOrder;
    @BindView(R.id.vp_order_list)
    ViewPager vpOrderList;

    private Context context;
    private MyPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private List<String> tabNames;
    private WorkOrderFragment receivedFragment, unReceivedFragment;
    private Bundle receiveBundle, unReceiveBundle;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_work_order;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = WorkOrderActivity.this;
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();
        tvTitle.setText("工单");

        initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        tabNames.add("未接收");
        tabNames.add("已接收");

        receivedFragment = new WorkOrderFragment();
        receiveBundle = new Bundle();
        receiveBundle.putString("Status", "1");//未接收
        receivedFragment.setArguments(receiveBundle);
        fragments.add(receivedFragment);

        unReceivedFragment = new WorkOrderFragment();
        unReceiveBundle = new Bundle();
        unReceiveBundle.putString("Status", "2");//已接收
        unReceivedFragment.setArguments(unReceiveBundle);
        fragments.add(unReceivedFragment);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpOrderList.setAdapter(pagerAdapter);
        tabOrder.setupWithViewPager(vpOrderList);
        vpOrderList.setCurrentItem(0);

    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, WorkOrderActivity.class);
        context.startActivity(intent);
    }

}
