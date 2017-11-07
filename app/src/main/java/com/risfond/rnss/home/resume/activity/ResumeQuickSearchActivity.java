package com.risfond.rnss.home.resume.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

/**
 * 快捷搜索界面
 */
public class ResumeQuickSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_quick_search);
    }

    @Override
    public int getContentViewResId() {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
}
