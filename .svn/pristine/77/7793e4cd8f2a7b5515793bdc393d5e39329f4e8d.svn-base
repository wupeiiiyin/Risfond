package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.home.resume.modleImpl.RecommendNextImpl;
import com.risfond.rnss.home.resume.modleInterface.IRecommendNext;
import com.risfond.rnss.widget.CustomerRadioGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 推荐人选页面
 */
public class RecommendPeopleNextActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.cb_rencai)
    CheckBox cbRencai;
    @BindView(R.id.rb_0)
    RadioButton rb0;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.ll_title)
    RelativeLayout llTitle;
    @BindView(R.id.rg_recommend)
    CustomerRadioGroup rgRecommend;

    private Context context;
    private String jobId = "";
    private String resumeId = "";
    private String Statues = "";
    private String IsChecked = "";
    private Map<String, String> request = new HashMap<>();
    private IRecommendNext iRecommendNext;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_recommend_people_next;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);
        context = RecommendPeopleNextActivity.this;
        iRecommendNext = new RecommendNextImpl();
        tvTitle.setText(R.string.recommend_people);
        Statues = "1";
        IsChecked = "1";
        resumeId = getIntent().getStringExtra("resumeId");
        jobId = getIntent().getStringExtra("jobId");
        click();
        checkProcess();
    }

    private void request() {
        DialogUtil.getInstance().showLoadingDialog(context, "上传中...");
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("resumeId", resumeId);
        request.put("jobId", jobId);
        request.put("Text", etMessage.getText().toString().trim());
        request.put("Statues", Statues);
        request.put("IsChecked", IsChecked);
        iRecommendNext.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_ADD_JOB_CANDIDATE_HANDLER, this);
    }

    private void click() {
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etMessage.getText().toString().trim())) {
                    ToastUtil.showShort(context, "请输入推荐短语");
                } else {
                    request();
                }
            }
        });

        cbRencai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IsChecked = "1";
                } else {
                    IsChecked = "5";
                }
            }
        });
    }

    private void checkProcess() {
        rgRecommend.setOnCheckedChangeListener(new CustomerRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomerRadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_0:
                        Statues = "1";
                        break;
                    case R.id.rb_1:
                        Statues = "4";
                        break;
                    case R.id.rb_2:
                        Statues = "2";
                        break;
                    case R.id.rb_3:
                        Statues = "3";
                        break;
                    case R.id.rb_4:
                        Statues = "5";
                        break;
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                ToastUtil.showShort(context, "成功");
                finish();
                setResult(9999);
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                ToastUtil.showLong(context, str);
            }
        });
    }

    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                ToastUtil.showLong(context, str);
            }
        });
    }

    public static void startAction(Context context, String resumeId, String jobId) {
        Intent intent = new Intent(context, RecommendPeopleNextActivity.class);
        intent.putExtra("resumeId", resumeId);
        intent.putExtra("jobId", jobId);
        context.startActivity(intent);
    }

}
