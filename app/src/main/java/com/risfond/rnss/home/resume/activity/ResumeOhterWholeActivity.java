package com.risfond.rnss.home.resume.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.resume.fragment.IndustrieFragment;
import com.risfond.rnss.home.resume.fragment.LanguageFragment;
import com.risfond.rnss.home.resume.fragment.PositionFragment;
import com.risfond.rnss.home.resume.fragment.PostFragment;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc
 */
public class ResumeOhterWholeActivity extends BaseActivity implements BaseSuccessCaseWholeFragment.OnSelectListener, SelectCallBack {
    public static String KEY_WHOLE = "KEY_WHOLE";
    public static String KEY_PARAMS = "KEY_PARAMS";
    public static String FRAGMENT_TYPE_KEY = "FRAGMENT_TYPE_KEY";
    public static String INDUSTRIE_FRAGMENT = "INDUSTRIE_FRAGMENT";
    public static String POSITION_FRAGMENT = "POSITION_FRAGMENT";
    public static String POST_FRAGMENT = "POST_FRAGMENT";
    public static String LANGUAGE_FRAGMENT = "LANGUAGE_FRAGMENT";
    private ResumeWhole mResumeWhole;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_ohter_whole;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra(FRAGMENT_TYPE_KEY);
        mResumeWhole = (ResumeWhole) getIntent().getParcelableExtra(KEY_PARAMS);
        Fragment fragment = null;
        if (INDUSTRIE_FRAGMENT.equals(type)) {
            fragment = new IndustrieFragment(mResumeWhole, this);
            fragment.setArguments(getIntent().getBundleExtra(IndustrieFragment.INDUSTRIE_TYPE));
            tvTitle.setText("选择行业");
        } else if (POSITION_FRAGMENT.equals(type)) {
            fragment = new PositionFragment(mResumeWhole.getDesiredlocations(), mResumeWhole.getDesiredlocationsTip(), this);
            tvTitle.setText("期望地点");
        } else if (POST_FRAGMENT.equals(type)) {
            fragment = new PostFragment(mResumeWhole, this);
            tvTitle.setText("期望职位");
        } else if (LANGUAGE_FRAGMENT.equals(type)) {
            fragment = new LanguageFragment(mResumeWhole, this);
            tvTitle.setText("语言能力");
        }
        if (fragment == null) {
            this.finish();
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_framelayout, fragment);
        fragmentTransaction.commit();
    }

    public void shutdownWindow(BaseWhole baseWhole) {
        EventBus.getDefault().post(baseWhole);
        this.finish();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onConfirm(BaseWhole whole) {
        shutdownWindow(whole);
    }

    @Override
    public void onPositionConfirm(List<String> positions, List<String> names) {
        mResumeWhole.setDesiredlocations(positions);
        mResumeWhole.setDesiredlocationsTip(names);
        shutdownWindow(mResumeWhole);
    }

    @Override
    public void onSelected(List<String> positions, List<String> names) {

    }

    @Override
    public void onExperienceConfirm(String from, String to) {

    }

    @Override
    public void onEducationConfirm(List<String> educations, List<String> educationName) {

    }

    @Override
    public void onMoreConfirm(List<String> recommends, String age_from, String age_to, List<String> sex, String salary_from, String salary_to, List<String> languages, String page) {

    }

    @Override
    public void onOutside() {

    }
}
