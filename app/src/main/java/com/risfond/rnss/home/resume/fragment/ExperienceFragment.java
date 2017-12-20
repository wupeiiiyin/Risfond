package com.risfond.rnss.home.resume.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Abbott on 2017/3/27.
 * 职位页面
 */

@SuppressLint("ValidFragment")
public class ExperienceFragment extends BaseFragment {
    @BindView(R.id.experience_from)
    EditText experienceFrom;
    @BindView(R.id.experience_to)
    EditText experienceTo;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.dismiss)
    TextView dismiss;

    private Context context;
    private SelectCallBack callBack;
    private String experience_from;
    private String experience_to;

    public ExperienceFragment(String experience_from, String experience_to, SelectCallBack callBack) {
        this.experience_from = experience_from;
        this.experience_to = experience_to;
        this.callBack = callBack;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_experience_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();
        experienceFrom.setText(experience_from);
        experienceTo.setText(experience_to);
    }

    @OnClick({R.id.tv_reset, R.id.tv_confirm, R.id.dismiss})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reset:
                reset();
                break;
            case R.id.tv_confirm:
                confirm();
                break;
            case R.id.dismiss:
                callBack.onOutside();
                ImeUtil.hideSoftKeyboard(experienceFrom);
                break;
        }
    }

    private void confirm() {
        experience_from = experienceFrom.getText().toString().trim();
        experience_to = experienceTo.getText().toString().trim();

        /*if (!TextUtils.isEmpty(experience_from) && !TextUtils.isEmpty(experience_to)) {
            if (Integer.parseInt(experience_from) > Integer.parseInt(experience_to)) {
                ToastUtil.showShort(context, "结束年限不能小于开始年限");
                return;
            }
        }*/

        callBack.onExperienceConfirm(experience_from, experience_to);
    }

    private void reset() {
        experienceFrom.setText("");
        experienceTo.setText("");
    }

    @Override
    protected void lazyLoad() {

    }

}
