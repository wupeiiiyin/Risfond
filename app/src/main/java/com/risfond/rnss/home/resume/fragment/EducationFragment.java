package com.risfond.rnss.home.resume.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.entry.SelectEventBus;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * Created by Abbott on 2017/3/27.
 * 职位页面
 */

@SuppressLint("ValidFragment")
public class EducationFragment extends BaseFragment {

    @BindView(R.id.cb_buxian)
    CheckBox cbBuxian;
    @BindView(R.id.cb_boshi)
    CheckBox cbBoshi;
    @BindView(R.id.cb_shuoshi)
    CheckBox cbShuoshi;
    @BindView(R.id.cb_benke)
    CheckBox cbBenke;
    @BindView(R.id.cb_dazhuan)
    CheckBox cbDazhuan;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.dismiss)
    TextView dismiss;

    private List<String> educations = new ArrayList<>();
    private List<String> educationNames = new ArrayList<>();
    private SelectCallBack callBack;

    public EducationFragment(List<String> education,List<String> educationName, SelectCallBack callBack) {
        educations.addAll(education);
        educationNames.addAll(educationName);
        this.callBack = callBack;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_education_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        for (int i = 0; i < educations.size(); i++) {
            switch (Integer.parseInt(educations.get(i))) {
                case 0:
                    cbBuxian.setChecked(true);
                    break;
                case 1:
                    cbBoshi.setChecked(true);
                    break;
                case 2:
                    cbShuoshi.setChecked(true);
                    break;
                case 3:
                    cbBenke.setChecked(true);
                    break;
                case 4:
                    cbDazhuan.setChecked(true);
                    break;
            }

        }

    }

    @OnClick({R.id.tv_reset, R.id.tv_confirm, R.id.dismiss})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reset:
                reset();
                break;
            case R.id.tv_confirm:
                callBack.onEducationConfirm(educations,educationNames);
                break;
            case R.id.dismiss:
                callBack.onOutside();
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_buxian, R.id.cb_boshi, R.id.cb_shuoshi,
            R.id.cb_benke, R.id.cb_dazhuan})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_buxian:
                if (isChecked) {
                    educations.clear();
                    educationNames.add("学历不限");
                    clearOthers();
                } else {
                    educationNames.remove("学历不限");
                }
                break;
            case R.id.cb_boshi:
                if (isChecked) {
                    if (!educations.contains("1")) {
                        educations.add("1");
                        educationNames.add("博士");
                    }
                    clearBuxian();
                } else {
                    educations.remove("1");
                    educationNames.remove("博士");
                }
                break;
            case R.id.cb_shuoshi:
                if (isChecked) {
                    if (!educations.contains("2")) {
                        educations.add("2");
                        educationNames.add("硕士");
                    }
                    clearBuxian();
                } else {
                    educations.remove("2");
                    educationNames.remove("硕士");
                }
                break;
            case R.id.cb_benke:
                if (isChecked) {
                    if (!educations.contains("3")) {
                        educations.add("3");
                        educationNames.add("本科");
                    }
                    clearBuxian();
                } else {
                    educations.remove("3");
                    educationNames.remove("本科");
                }
                break;
            case R.id.cb_dazhuan:
                if (isChecked) {
                    if (!educations.contains("4")) {
                        educations.add("4");
                        educationNames.add("大专");
                    }
                    clearBuxian();
                } else {
                    educations.remove("4");
                    educationNames.remove("大专");
                }
                break;
        }
    }

    private void clearOthers() {
        cbBoshi.setChecked(false);
        cbShuoshi.setChecked(false);
        cbBenke.setChecked(false);
        cbDazhuan.setChecked(false);
    }

    private void clearBuxian() {
        cbBuxian.setChecked(false);
    }

    private void reset() {
        cbBuxian.setChecked(true);
        cbBoshi.setChecked(false);
        cbShuoshi.setChecked(false);
        cbBenke.setChecked(false);
        cbDazhuan.setChecked(false);
        educations.clear();
    }

    @Override
    protected void lazyLoad() {

    }

}
