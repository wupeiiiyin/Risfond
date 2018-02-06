package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import butterknife.BindView;

import static android.R.attr.key;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("日程");
        llDetailstimeOnclick.setOnClickListener(this);
        Intent intent = getIntent();
        intent.getStringExtra("key");
        //int key = intent.getIntExtra("key", 0);
        Log.e("HB",key+"");
//        String time=intent.getStringExtra("time12");
//        String searches=intent.getStringExtra("searches");
//        Log.e("HB",time);
//        Log.e("HB",searches);
    }

    public void sett(String ss,String sss){
        tvDetailstimeContent.setText(ss);
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
