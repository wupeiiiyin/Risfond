package com.risfond.rnss.home.homesearch.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.home.customer.activity.CustomerSearchResultActivity;
import com.risfond.rnss.home.position.activity.PositionSearchResultActivity;
import com.risfond.rnss.home.resume.activity.ResumeSearchResultActivity;
import com.risfond.rnss.search.activity.SearchActivity;
import butterknife.BindView;
import butterknife.OnClick;

/*
* 主页搜索
* */
public class HomeSearchActivity extends BaseActivity {
    private Context context;

    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.ll_top_search)
    LinearLayout llTopSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.tv_resume_search)
    TextView tvResumeSearch;
    @BindView(R.id.tv_customer_search)
    TextView tvCustomerSearch;
    @BindView(R.id.tv_position_search)
    TextView tvPositionSearch;
    @BindView(R.id.tv_contacts_search)
    TextView tvContactsSearch;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_home_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = HomeSearchActivity.this;
    }

    @OnClick({R.id.et_search,R.id.tv_resume_search,R.id.tv_customer_search,R.id.tv_position_search,R.id.tv_contacts_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_search:
            case R.id.tv_resume_search:
                ResumeSearchResultActivity.StartAction(context);
                break;
            case R.id.tv_customer_search:
                CustomerSearchResultActivity.StartAction(context);
                break;
            case R.id.tv_position_search:
                PositionSearchResultActivity.StartAction(context);
                break;
            case R.id.tv_contacts_search:
                SearchActivity.startAction(context);
                break;
        }
    }

    @Override
    public void back(View v) {
        super.back(v);
        ImeUtil.hideSoftKeyboard(etSearch);
        finish();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, HomeSearchActivity.class);
        context.startActivity(intent);
    }

}
