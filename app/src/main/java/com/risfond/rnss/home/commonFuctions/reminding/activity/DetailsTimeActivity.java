package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import butterknife.BindView;

public class DetailsTimeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_detailstime_timeclock)
    TextView tvDetailstimeTimeclock;
    @BindView(R.id.tv_detailstime_timedate)
    TextView tvDetailstimeTimedate;
    @BindView(R.id.tv_detailstime_content)
    TextView tvDetailstimeContent;

    @BindView(R.id.ll_detailstime_onclick)
    RelativeLayout llDetailstimeOnclick;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_details_time;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("日程");
        Intent intent = getIntent();
        intent.getStringExtra("time");
        llDetailstimeOnclick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_detailstime_onclick:

                startActivity(RemindingTimeActivity.class, false);
                break;
        }
    }
}
