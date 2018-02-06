package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private TransactiondatabaseSQL ttdbsqlite;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_details_time;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modifyanddelete,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.update:
//                ttdbsqlite.updatetransaction();
                startActivity(AddTheTransactionActivity.class, false);
                break;
            case R.id.delete:

//                ttdbsqlite.deletetransaction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("日程");
        llDetailstimeOnclick.setOnClickListener(this);

        Intent intent = getIntent();
        String tv_itemcontent = intent.getStringExtra("tv_itemcontent");
        String tv_itemtime = intent.getStringExtra("tv_itemtime");
        tvDetailstimeContent.setText(tv_itemcontent);
        tvDetailstimeTimedate.setText(tv_itemtime);





        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
    }

//    public void sett(String ss,String sss){
//        tvDetailstimeContent.setText(ss);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_detailstime_onclick:
                startActivity(RemindingTimeActivity.class, false);
                break;
        }
    }
}
