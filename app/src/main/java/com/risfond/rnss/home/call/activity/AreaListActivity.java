package com.risfond.rnss.home.call.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.call.adapter.AreaListAdapter;
import com.risfond.rnss.home.call.widget.Area;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaListActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context context;
    private AreaListAdapter mAdapter;
    private List<Area> mDatas = new ArrayList<>();
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_area_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = AreaListActivity.this;
        mAdapter = new AreaListAdapter(context, mDatas);
        mManager = new LinearLayoutManager(this);
        tvTitle.setText("国际电话区号");
        rv.setLayoutManager(mManager);
        rv.setAdapter(mAdapter);

        initList();
        onItemClick();
    }

    private void onItemClick() {
        mAdapter.setOnItemClickListener(new AreaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("area", mDatas.get(position));
                setResult(2008, intent);
                finish();
            }
        });
    }

    private void initList() {
        rv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rv.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {

        for (int i = 0; i < data.length; i++) {
            Area cityBean = new Area();
            cityBean.setAreacode(data[i].split(",")[0]);
            cityBean.setAreacodeid(data[i].split(",")[1]);
            mDatas.add(cityBean);
        }
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();

        indexBar.setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
