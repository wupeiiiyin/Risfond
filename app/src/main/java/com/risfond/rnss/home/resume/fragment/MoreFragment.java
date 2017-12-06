package com.risfond.rnss.home.resume.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.resume.activity.ResumeOhterWholeActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.risfond.rnss.home.resume.activity.ResumeOhterWholeActivity.FRAGMENT_TYPE_KEY;


/**
 * Created by Abbott on 2017/3/27.
 * 职位页面
 */

@SuppressLint("ValidFragment")
public class MoreFragment extends BaseFragment implements  View.OnClickListener {
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
    @BindView(R.id.id_schoolname)
    EditText mSchoolname;
    @BindView(R.id.id_major)
    EditText mMajor;
    @BindView(R.id.id_language)
    TextView mLanguage;
    @BindView(R.id.id_industrie)
    TextView mIndustrie;
    @BindView(R.id.id_desired_industries)
    TextView mDesiredIndustries;
    @BindView(R.id.id_desired_locations)
    TextView mDesiredLocations;
    @BindView(R.id.id_desiredoccupations)
    TextView mDesiredoccupations;
    @BindView(R.id.id_history_all)
    RadioButton mHistoryAll;
    @BindView(R.id.id_history_have)
    RadioButton mHistoryHave;
    @BindView(R.id.id_history_nhave)
    RadioButton mHistoryNhave;
    @BindView(R.id.id_history)
    RadioGroup mHistory;
    @BindView(R.id.id_updatetime)
    RadioGroup mUpdateTimeGroup;

    Unbinder unbinder;
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
    private OnResumeSelectListener mOnResumeSelectListener;


    private ResumeWhole mResumeWhole = new ResumeWhole();

    public MoreFragment() {
    }

    public MoreFragment(ResumeWhole resumeWhole, OnResumeSelectListener onResumeSelectListener) {
        mOnResumeSelectListener = onResumeSelectListener;
        mResumeWhole = resumeWhole;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_more_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBusUtil.registerEventBus(this);

        setSelectToView();
        initChangeListener(mUpdateTimeGroup);
    }

    private void initChangeListener(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                initChangeListener((ViewGroup) viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                ((RadioButton) viewGroup.getChildAt(i)).setOnClickListener(this);
            }
        }
    }

    @OnClick({R.id.tv_reset, R.id.tv_confirm, R.id.dismiss,
            R.id.id_desired_industries, R.id.id_industrie,
            R.id.id_desired_locations, R.id.id_desiredoccupations,
            R.id.id_language})
    public void onClickEvent(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tv_reset:
                reset();
                break;
            case R.id.tv_confirm:
                confirm();
                break;
            case R.id.id_industrie:
                //当前行业
                intent.setClass(getActivity(), ResumeOhterWholeActivity.class);
                intent.putExtra(FRAGMENT_TYPE_KEY, ResumeOhterWholeActivity.INDUSTRIE_FRAGMENT);

                bundle.putString(IndustrieFragment.INDUSTRIE_TYPE, IndustrieFragment.INDUSTRIE);
                intent.putExtra(IndustrieFragment.INDUSTRIE_TYPE, bundle);
                intent.putExtra(ResumeOhterWholeActivity.KEY_PARAMS, mResumeWhole);
                startActivity(intent);
                break;
            case R.id.id_desired_industries:
                //期待行业
                intent.setClass(getActivity(), ResumeOhterWholeActivity.class);
                intent.putExtra(FRAGMENT_TYPE_KEY, ResumeOhterWholeActivity.INDUSTRIE_FRAGMENT);
                bundle.putString(IndustrieFragment.INDUSTRIE_TYPE, IndustrieFragment.DESIRED_INDUSTRIES);
                intent.putExtra(IndustrieFragment.INDUSTRIE_TYPE, bundle);
                intent.putExtra(ResumeOhterWholeActivity.KEY_PARAMS, mResumeWhole);
                startActivity(intent);
                break;
            case R.id.id_desired_locations:
                //期待地点
                intent.setClass(getActivity(), ResumeOhterWholeActivity.class);
                intent.putExtra(FRAGMENT_TYPE_KEY, ResumeOhterWholeActivity.POSITION_FRAGMENT);
                intent.putExtra(ResumeOhterWholeActivity.KEY_PARAMS, mResumeWhole);
                startActivity(intent);
                break;
            case R.id.id_desiredoccupations:
                //期待职业
                intent.setClass(getActivity(), ResumeOhterWholeActivity.class);
                intent.putExtra(FRAGMENT_TYPE_KEY, ResumeOhterWholeActivity.POST_FRAGMENT);
                intent.putExtra(ResumeOhterWholeActivity.KEY_PARAMS, mResumeWhole);
                startActivity(intent);
                break;
            case R.id.id_language:
                //语言能力
                intent.setClass(getActivity(), ResumeOhterWholeActivity.class);
                intent.putExtra(FRAGMENT_TYPE_KEY, ResumeOhterWholeActivity.LANGUAGE_FRAGMENT);
                intent.putExtra(ResumeOhterWholeActivity.KEY_PARAMS, mResumeWhole);
                startActivity(intent);
                break;
            case R.id.dismiss:
                ImeUtil.hideSoftKeyboard(ageFrom);
                break;
        }
    }

    private void confirm() {
        //年龄
        mResumeWhole.setAgefrom(Integer.parseInt(ageFrom.getText().toString().trim().length()>0? this.ageFrom.getText().toString().trim():"0"));
        mResumeWhole.setAgeto(Integer.parseInt(ageTo.getText().toString().trim().length()>0?ageTo.getText().toString().trim():"0"));
        //性别
        mResumeWhole.setGender(Arrays.asList(rgSex.findViewById(rgSex.getCheckedRadioButtonId()).getTag().toString()));
        //学校名称
        mResumeWhole.setSchoolname(mSchoolname.getText().toString().trim());
        //专业名称
        mResumeWhole.setMajor(mMajor.getText().toString().trim());
        //期望年薪
        mResumeWhole.setSalaryfrom(Integer.parseInt(salaryFrom.getText().toString().trim().length()>0?salaryFrom.getText().toString().trim():"0"));
        mResumeWhole.setSalaryto(Integer.parseInt(salaryTo.getText().toString().trim().length()>0?salaryTo.getText().toString().trim():"0"));
        //推荐状态
        mResumeWhole.setResumestatus(Arrays.asList(rgRecommend.findViewById(rgRecommend.getCheckedRadioButtonId()).getTag().toString()));
        //推荐历史
        mResumeWhole.setHistory(Integer.parseInt(mHistory.findViewById(mHistory.getCheckedRadioButtonId()).getTag().toString()));

        //页数
        String pageindex = etPage.getText().toString().trim();
        mResumeWhole.setPageindex(TextUtils.isEmpty(pageindex) ? 1 : Integer.parseInt(pageindex));

        //更新时间
        setLastUpdateTimeFrom(mUpdateTimeGroup);
        mResumeWhole.setLastupdatetimeto(format.format(Calendar.getInstance().getTime()));
        if ("32".equals(mResumeWhole.getLastupdatetimeTag())) {
            //一个月以上 只有开始结束日期
            mResumeWhole.setLastupdatetimefrom("");
        } else if ("0".equals(mResumeWhole.getLastupdatetimeTag())) {
            mResumeWhole.setLastupdatetimefrom("");
            mResumeWhole.setLastupdatetimeto("");
        }
        if (mOnResumeSelectListener != null) {
            mOnResumeSelectListener.onConfirm(mResumeWhole);
        }
    }

    private void reset() {
        mResumeWhole.setAgefrom(0);
        mResumeWhole.setAgeto(0);

        mResumeWhole.setGender(Arrays.asList("0"));
        mResumeWhole.setSchoolname("");
        mResumeWhole.setMajor("");

        mResumeWhole.setLang(new ArrayList<String>());
        mResumeWhole.setLangs(new ArrayList<String>());

        mResumeWhole.setIndustrys(new ArrayList<String>());
        mResumeWhole.setIndustrysTip(new ArrayList<String>());

        mResumeWhole.setDesiredIndustries(new ArrayList<String>());
        mResumeWhole.setDesiredIndustriesTip(new ArrayList<String>());

        mResumeWhole.setDesiredlocations(new ArrayList<String>());
        mResumeWhole.setDesiredlocationsTip(new ArrayList<String>());

        mResumeWhole.setDesiredoccupations(new ArrayList<String>());
        mResumeWhole.setDesiredoccupationsTip(new ArrayList<String>());
        mResumeWhole.setSalaryfrom(0);
        mResumeWhole.setSalaryto(0);

        mResumeWhole.setResumestatus(Arrays.asList("0"));
        mResumeWhole.setHistory(0);

        //更新日期
        mResumeWhole.setLastupdatetimefrom("");
        mResumeWhole.setLastupdatetimeto("");
        mResumeWhole.setLastupdatetimeTag("0");

        setSelectToView();
    }

    @Override
    protected void lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIndustrieResult(BaseWhole whole) {
        mResumeWhole = (ResumeWhole) whole;
        setSelectToView();
        UtilHelper.outLog(TAG, mResumeWhole.toString());
    }

    private void setSelectToView() {
        //年龄
        if (mResumeWhole.getAgefrom() != 0) {
            ageFrom.setText(String.valueOf(mResumeWhole.getAgefrom()));
        }
        if (mResumeWhole.getAgeto() != 0) {
            ageTo.setText(String.valueOf(mResumeWhole.getAgeto()));
        }
        //性别
        switch (Integer.parseInt(mResumeWhole.getGender().get(0))) {
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
        //学校名称
        mSchoolname.setText(mResumeWhole.getSchoolname());
        //专业名称
        mMajor.setText(mResumeWhole.getMajor());

        //语言
        mLanguage.setText(mResumeWhole.getLang().size() > 0 ? joinSelect(mResumeWhole.getLangs()) : "请选择");
        //当前行业
        mIndustrie.setText(mResumeWhole.getIndustrys().size() > 0 ? joinSelect(mResumeWhole.getIndustrysTip()) : "请选择");
        //期望行业
        mDesiredIndustries.setText(mResumeWhole.getDesiredIndustries().size() > 0 ? joinSelect(mResumeWhole.getDesiredIndustriesTip()) : "请选择");
        //期望地点
        mDesiredLocations.setText(mResumeWhole.getDesiredlocations().size() > 0 ? joinSelect(mResumeWhole.getDesiredlocationsTip()) : "请选择");
        //期望职位
        mDesiredoccupations.setText(mResumeWhole.getDesiredoccupations().size() > 0 ? joinSelect(mResumeWhole.getDesiredoccupationsTip()) : "请选择");
        //期望年薪
        if (mResumeWhole.getSalaryfrom() != 0) {
            salaryFrom.setText(String.valueOf(mResumeWhole.getSalaryfrom()));
        }
        if (mResumeWhole.getSalaryto() != 0) {
            salaryTo.setText(String.valueOf(mResumeWhole.getSalaryto()));
        }
        //推荐状态
        switch (Integer.parseInt(mResumeWhole.getResumestatus().get(0))) {
            case 1:
                rbRKetui.setChecked(true);
                break;
            case 2:
                rbRWurao.setChecked(true);
                break;
            default:
                rbRAll.setChecked(true);
                break;
        }

        //推荐历史
        switch (mResumeWhole.getHistory()) {
            case 1:
                mHistoryHave.setChecked(true);
                break;
            case 2:
                mHistoryNhave.setChecked(true);
                break;
            default:
                mHistoryAll.setChecked(true);
                break;
        }
        //分页
        etPage.setText(String.valueOf(mResumeWhole.getPageindex()));

        //更新日期
        clearAll(mUpdateTimeGroup);
        initRadioButton(mUpdateTimeGroup, Integer.parseInt(mResumeWhole.getLastupdatetimeTag()));
    }

    private String joinSelect(List<String> data) {
        StringBuffer sb = new StringBuffer();
        if (data == null) {
            return sb.toString();
        }
        for (String s : data) {
            sb.append(s + "、");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();

    }

    /**
     * 日期解析
     *
     * @param tag
     * @return
     */
    private String parseTimeToStr(int tag) {
        if (tag == 0) {
            return "";
        }
        Calendar currCalendar = Calendar.getInstance();
        currCalendar.setTimeInMillis(System.currentTimeMillis());
        currCalendar.set(Calendar.DATE, currCalendar.get(Calendar.DATE) - tag);
        return format.format(currCalendar.getTime());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void clearAll(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                clearAll((ViewGroup) viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                ((RadioButton) viewGroup.getChildAt(i)).setChecked(false);
            }
        }
    }

    private void setLastUpdateTimeFrom(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                setLastUpdateTimeFrom((ViewGroup) viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                if (((RadioButton) viewGroup.getChildAt(i)).isChecked()) {
                    String tag = viewGroup.getChildAt(i).getTag().toString();
                    mResumeWhole.setLastupdatetimefrom(parseTimeToStr(Integer.parseInt(tag)));
                    mResumeWhole.setLastupdatetimeTag(tag);
                }
            }
        }
    }
    private void initRadioButton(ViewGroup viewGroup,int tag) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                initRadioButton((ViewGroup) childAt,tag);
            }
            if (childAt instanceof RadioButton) {
                if (childAt.getTag().toString().equals(String.valueOf(tag))) {
                    ((RadioButton) childAt).setChecked(true);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        CompoundButton buttonView = (CompoundButton) v;
        clearAll(mUpdateTimeGroup);
        buttonView.setChecked(true);
    }


    public interface OnResumeSelectListener {
        void onConfirm(ResumeWhole resumeWhole);
    }
}
