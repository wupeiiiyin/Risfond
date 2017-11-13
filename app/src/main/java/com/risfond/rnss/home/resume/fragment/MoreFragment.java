package com.risfond.rnss.home.resume.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;


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
public class MoreFragment extends BaseFragment {
    @BindView(R.id.rb_r_all)
    RadioButton rbRAll;
    @BindView(R.id.rb_r_ketui)
    RadioButton rbRKetui;
    @BindView(R.id.rb_r_wurao)
    RadioButton rbRWurao;
    @BindView(R.id.age_from)
    EditText ageFrom;
    @BindView(R.id.age_to)
    EditText ageTo;
    @BindView(R.id.rb_buxian)
    RadioButton rbBuxian;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.salary_from)
    EditText salaryFrom;
    @BindView(R.id.salary_to)
    EditText salaryTo;
    @BindView(R.id.cb_0)
    CheckBox cb0;
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.cb_3)
    CheckBox cb3;
    @BindView(R.id.cb_4)
    CheckBox cb4;
    @BindView(R.id.cb_5)
    CheckBox cb5;
    @BindView(R.id.cb_6)
    CheckBox cb6;
    @BindView(R.id.cb_7)
    CheckBox cb7;
    @BindView(R.id.cb_9)
    CheckBox cb9;
    @BindView(R.id.cb_12)
    CheckBox cb12;
    @BindView(R.id.cb_13)
    CheckBox cb13;
    @BindView(R.id.cb_14)
    CheckBox cb14;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.dismiss)
    TextView dismiss;
    @BindView(R.id.rg_recommend)
    RadioGroup rgRecommend;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_page)
    EditText etPage;

    private SelectCallBack callBack;
    private List<String> languages = new ArrayList<>();
    private List<String> languages_texts = new ArrayList<>();//创建一个集合
    private List<String> recommends = new ArrayList<>();
    private List<String> sexs = new ArrayList<>();
    private List<String> sexs_texts = new ArrayList<>();//创建一个集合
    private String age_From;
    private String age_To;
    private String salary_From;
    private String salary_To;
    private String page;
    private boolean isHasData;

    public MoreFragment(List<String> recommend, String age_from, String age_to, List<String> sex,List<String> sexs_text,
                        String salary_from, String salary_to, List<String> language,List<String> languages_t, String page,
                        boolean isHasData, SelectCallBack callBack) {
        recommends.addAll(recommend);
        this.age_From = age_from;
        this.age_To = age_to;
        sexs.addAll(sex);
        sexs_texts.addAll(sexs_text);//添加
        this.salary_From = salary_from;
        this.salary_To = salary_to;
        languages.addAll(language);
        languages_texts.addAll(languages_t);//添加
        this.page = page;
        this.isHasData = isHasData;
        this.callBack = callBack;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_more_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        if (Integer.parseInt(page) > 1) {
            if (isHasData){
                page = String.valueOf(Integer.parseInt(page) - 1);
            }
        }
        etPage.setHint(page);

        if (recommends.size() > 0) {
            switch (Integer.parseInt(recommends.get(0))) {
                case 0:
                    rbRAll.setChecked(true);
                    break;
                case 1:
                    rbRKetui.setChecked(true);
                    break;
                case 2:
                    rbRWurao.setChecked(true);
                    break;
            }
        }

        ageFrom.setText(age_From);
        ageTo.setText(age_To);

        if (sexs.size() > 0) {
            switch (Integer.parseInt(sexs.get(0))) {
                case 1:
                    rbMan.setChecked(true);
                    break;
                case 2:
                    rbWoman.setChecked(true);
                    break;
                default:
                    rbBuxian.setChecked(true);
                    break;
            }
        }

        salaryFrom.setText(salary_From);
        salaryTo.setText(salary_To);

        /*if (languages.size() == 0) {
            languages.add("00");
        }*/

        for (int i = 0; i < languages.size(); i++) {
            switch (Integer.parseInt(languages.get(i))) {
                case 0:
                    cb0.setChecked(true);
                    break;
                case 1:
                    cb1.setChecked(true);
                    break;
                case 2:
                    cb2.setChecked(true);
                    break;
                case 3:
                    cb3.setChecked(true);
                    break;
                case 4:
                    cb4.setChecked(true);
                    break;
                case 5:
                    cb5.setChecked(true);
                    break;
                case 6:
                    cb6.setChecked(true);
                    break;
                case 7:
                    cb7.setChecked(true);
                    break;
                case 9:
                    cb9.setChecked(true);
                    break;
                case 12:
                    cb12.setChecked(true);
                    break;
                case 13:
                    cb13.setChecked(true);
                    break;
                case 14:
                    cb14.setChecked(true);
                    break;
            }

        }

        rgRecommend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_r_all:
                        recommends.clear();
                        break;
                    case R.id.rb_r_ketui:
                        recommends.clear();
                        recommends.add("1");
                        break;
                    case R.id.rb_r_wurao:
                        recommends.clear();
                        recommends.add("2");
                        break;
                }
            }
        });
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_buxian:
                        sexs.clear();
                        break;
                    case R.id.rb_man:
                        sexs.clear();
                        sexs_texts.clear();//清除
                        sexs.add("1");
                        sexs_texts.add("男");//添加
                        break;
                    case R.id.rb_woman:
                        sexs.clear();
                        sexs_texts.clear();//清除
                        sexs.add("2");
                        sexs_texts.add("女");//添加
                        break;
                }
            }
        });
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
                ImeUtil.hideSoftKeyboard(ageFrom);
                callBack.onOutside();
                break;
        }
    }

    private void confirm() {
        age_From = ageFrom.getText().toString().trim();
        age_To = ageTo.getText().toString().trim();

        salary_From = salaryFrom.getText().toString().trim();
        salary_To = salaryTo.getText().toString().trim();

        page = etPage.getText().toString().trim();
        if (TextUtils.isEmpty(page)) {
            page = "1";
        }

        callBack.onMoreConfirm(recommends, age_From, age_To, sexs,sexs_texts, salary_From, salary_To, languages,languages_texts, page);//回调
    }

    @OnCheckedChanged({R.id.cb_0, R.id.cb_1, R.id.cb_2, R.id.cb_3, R.id.cb_4,
            R.id.cb_5, R.id.cb_6, R.id.cb_7, R.id.cb_9, R.id.cb_12, R.id.cb_13,
            R.id.cb_14})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_0:
                if (isChecked) {
                    /*if (!languages.contains("00")) {
                        languages.add("00");
                    }*/
                    languages.clear();
                    languages_texts.clear();//清除
                    clearOthers();
                } /*else {
                    languages.remove("00");
                }*/
                break;
            case R.id.cb_1:
                if (isChecked) {
                    if (!languages.contains("01")) {
                        languages.add("01");
                        languages_texts.add("汉语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("01");
                    languages_texts.remove("汉语");
                }
                break;
            case R.id.cb_2:
                if (isChecked) {
                    if (!languages.contains("02")) {
                        languages.add("02");
                        languages_texts.add("英语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("02");
                    languages_texts.remove("英语");
                }
                break;
            case R.id.cb_3:
                if (isChecked) {
                    if (!languages.contains("03")) {
                        languages.add("03");
                        languages_texts.add("日语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("03");
                    languages_texts.remove("日语");
                }
                break;
            case R.id.cb_4:
                if (isChecked) {
                    if (!languages.contains("04")) {
                        languages.add("04");
                        languages_texts.add("法语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("04");
                    languages_texts.remove("法语");
                }
                break;
            case R.id.cb_5:
                if (isChecked) {
                    if (!languages.contains("05")) {
                        languages.add("05");
                        languages_texts.add("德语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("05");
                    languages_texts.remove("德语");
                }
                break;
            case R.id.cb_6:
                if (isChecked) {
                    if (!languages.contains("06")) {
                        languages.add("06");
                        languages_texts.add("韩语、朝鲜语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("06");
                    languages_texts.remove("韩语、朝鲜语");
                }
                break;
            case R.id.cb_7:
                if (isChecked) {
                    if (!languages.contains("07")) {
                        languages.add("07");
                        languages_texts.add("俄语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("07");
                    languages_texts.remove("俄语");
                }
                break;
            case R.id.cb_9:
                if (isChecked) {
                    if (!languages.contains("09")) {
                        languages.add("09");
                        languages_texts.add("西班牙语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("09");
                    languages_texts.remove("西班牙语");
                }
                break;
            case R.id.cb_12:
                if (isChecked) {
                    if (!languages.contains("12")) {
                        languages.add("12");
                        languages_texts.add("阿拉伯语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("12");
                    languages_texts.remove("阿拉伯语");
                }
                break;
            case R.id.cb_13:
                if (isChecked) {
                    if (!languages.contains("13")) {
                        languages.add("13");
                        languages_texts.add("意大利语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("13");
                    languages_texts.remove("意大利语");
                }
                break;
            case R.id.cb_14:
                if (isChecked) {
                    if (!languages.contains("14")) {
                        languages.add("14");
                        languages_texts.add("葡萄牙语");
                    }
                    clearBuxian();
                } else {
                    languages.remove("14");
                    languages_texts.remove("葡萄牙语");
                }
                break;
        }
    }

    private void clearOthers() {
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        cb6.setChecked(false);
        cb7.setChecked(false);
        cb9.setChecked(false);
        cb12.setChecked(false);
        cb13.setChecked(false);
        cb14.setChecked(false);
    }

    private void clearBuxian() {
        cb0.setChecked(false);
    }

    private void reset() {

        rbRAll.setChecked(true);

        rbBuxian.setChecked(true);

        cb0.setChecked(true);
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        cb6.setChecked(false);
        cb7.setChecked(false);
        cb9.setChecked(false);
        cb12.setChecked(false);
        cb13.setChecked(false);
        cb14.setChecked(false);

        ageFrom.setText("");
        ageTo.setText("");
        age_From = "";
        age_To = "";

        salaryFrom.setText("");
        salaryTo.setText("");
        salary_From = "";
        salary_To = "";

    }

    @Override
    protected void lazyLoad() {

    }

}
