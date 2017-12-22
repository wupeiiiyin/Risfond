package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTheTransactionActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_addthetransaction_content)
    EditText editAddthetransactionContent;
    @BindView(R.id.ll_addthetransaction_time)
    LinearLayout llAddthetransactionTime;
    @BindView(R.id.ll_addthetransaction_reminding)
    LinearLayout llAddthetransactionReminding;
    @BindView(R.id.tv_addthetransaction_commit)
    TextView tvAddthetransactionCommit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_the_transaction;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("添加事务");
        ButterKnife.bind(this);



    }




    @OnClick({R.id.ll_addthetransaction_time, R.id.ll_addthetransaction_reminding, R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_addthetransaction_time:
                break;
            case R.id.ll_addthetransaction_reminding:
                ToastUtil.showShort(getApplication(),"提前提醒");
                break;
            case R.id.tv_addthetransaction_commit:
                String arr_list = editAddthetransactionContent.getText().toString();
                if (arr_list == null || arr_list.equals("")) {
                    Toast.makeText(getApplicationContext(), "添加的内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this,RemindingActivity.class);
                    startActivity(intent.putExtra("arr_list",arr_list));
                    finish();
                    break;
                }

        }
    }
}
