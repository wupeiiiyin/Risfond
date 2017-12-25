package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.os.Bundle;
import android.widget.TextView;


import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import butterknife.BindView;

public class RemindingTimeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding_time;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("提醒时间");

    }
}
