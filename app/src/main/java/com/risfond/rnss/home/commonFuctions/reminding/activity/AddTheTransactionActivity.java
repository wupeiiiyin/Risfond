package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;

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
                //ToastUtil.showShort(getApplication(),"选择时间");
//                AlertDialog.Builder db = new AlertDialog.Builder(this);
//                db.setView(R.layout.activity_choose_time);
//                db.show();
                startActivity(ChooseTimeActivity.class,true);
                break;
            case R.id.ll_addthetransaction_reminding:
                ToastUtil.showShort(getApplication(),"提前提醒");
                break;
            case R.id.tv_addthetransaction_commit:
                ToastUtil.showShort(getApplication(),"保存提交");
                break;
        }
    }
}
