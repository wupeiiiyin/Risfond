package com.risfond.rnss.register.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.WheelDialog;
import com.risfond.rnss.entry.RegisterCompany;
import com.risfond.rnss.entry.RegisterResponse;
import com.risfond.rnss.login.activity.LoginActivity;
import com.risfond.rnss.register.modelimpl.RegisterImpl;
import com.risfond.rnss.register.modelinterface.IRegister;
import com.risfond.rnss.widget.ShowRegisterSuccessDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterThirdActivity extends BaseActivity implements WheelDialog.TimeSelected,
        WheelDialog.DataSelected, ResponseCallBack, DialogUtil.PressCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_title)
    RelativeLayout llTitle;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.et_school)
    EditText etSchool;
    @BindView(R.id.et_major)
    EditText etMajor;
    @BindView(R.id.tv_graduation_time)
    TextView tvGraduationTime;
    @BindView(R.id.et_last_company)
    EditText etLastCompany;
    @BindView(R.id.et_last_position)
    EditText etLastPosition;

    private Context context;
    private List<RegisterCompany> educationList = new ArrayList<>();
    private String Id, education, educationCode, school, major, graduationTime, last_company, last_position;

    private ShowRegisterSuccessDialog successDialog;

    private Map<String, String> request = new HashMap<>();
    private IRegister iRegister;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_register_third;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RegisterThirdActivity.this;
        tvTitle.setText(R.string.regist_title3);
        tvNext.setText(R.string.register_finish);

        successDialog = new ShowRegisterSuccessDialog(context, this);
        createData();
        Id = getIntent().getStringExtra("Id");
        iRegister = new RegisterImpl();
    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "上传中");
        request.put("Id", Id);
        request.put("Edu", educationCode);
        request.put("EduSchool", school);
        request.put("Major", major);
        request.put("EduTime", graduationTime);
        request.put("LastCompany", last_company);
        request.put("LastJob", last_position);

        iRegister.registerRequest("", request, URLConstant.URL_ADD_EDU_INFO, this);
    }

    private void createData() {
        String[] data = getResources().getStringArray(R.array.education);
        for (String str : data) {
            RegisterCompany data1 = new RegisterCompany();
            data1.setCompanyName(str.split(" ")[0]);
            data1.setCompanyId(Integer.parseInt(str.split(" ")[1]));
            educationList.add(data1);
        }
    }

    @OnClick({R.id.tv_next, R.id.tv_education, R.id.tv_graduation_time})
    public void onClick(View view) {
        ImeUtil.hideSoftKeyboard(etSchool);
        switch (view.getId()) {
            case R.id.tv_next:
                submit();
                break;
            case R.id.tv_education:
                education = tvEducation.getText().toString().trim();
                WheelDialog.getInstance().showDataDialog(context, "学历", education, educationList, this);
                break;
            case R.id.tv_graduation_time:
                graduationTime = tvGraduationTime.getText().toString().trim();
                WheelDialog.getInstance().showDateSelectDialog(context, "毕业时间",
                        WheelDialog.TYPE_FORMAT_yyyyMM, WheelDialog.TYPE_YEAR_MONTH, graduationTime, this);
                break;
        }
    }

    private void submit() {
        education = tvEducation.getText().toString().trim();
        school = etSchool.getText().toString().trim();
        major = etMajor.getText().toString().trim();
        graduationTime = tvGraduationTime.getText().toString().trim();
        last_company = etLastCompany.getText().toString().trim();
        last_position = etLastPosition.getText().toString().trim();

        if (TextUtils.isEmpty(education)) {
            ToastUtil.showShort(context, "请选择学历");
        } else if (TextUtils.isEmpty(school)) {
            ToastUtil.showShort(context, "请输入毕业学校");
        } else if (TextUtils.isEmpty(major)) {
            ToastUtil.showShort(context, "请输入专业");
        } else if (TextUtils.isEmpty(graduationTime)) {
            ToastUtil.showShort(context, "请选择毕业时间");
        } else if (TextUtils.isEmpty(last_company)) {
            ToastUtil.showShort(context, "请输入上一家公司名称");
        } else if (TextUtils.isEmpty(last_position)) {
            ToastUtil.showShort(context, "请输入上一份职位名称");
        } else {
            requestService();
        }

    }

    public static void startAction(Context context, String Id) {
        Intent intent = new Intent(context, RegisterThirdActivity.class);
        intent.putExtra("Id", Id);
        context.startActivity(intent);
    }

    @Override
    public void onTimeSelected(String time) {
        tvGraduationTime.setText(time);
    }

    @Override
    public void onDataSelected(String value, String code) {
        tvEducation.setText(value);
        educationCode = code;
    }

    private void updateUI(final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (o instanceof RegisterResponse) {
                    successDialog.show();
                } else {
                    ToastUtil.showShort(context, o.toString());
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }

    @Override
    public void onPressButton(int buttonIndex) {
        LoginActivity.startAction(context);
    }
}
