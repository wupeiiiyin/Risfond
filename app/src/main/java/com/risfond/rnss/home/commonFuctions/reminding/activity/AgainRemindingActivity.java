package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgainRemindingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llllll)
    ListView listRemindingItem;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_again_reminding;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("事务提醒");
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, RemindingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
