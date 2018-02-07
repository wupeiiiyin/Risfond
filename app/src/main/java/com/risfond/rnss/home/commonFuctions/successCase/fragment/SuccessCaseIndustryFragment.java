package com.risfond.rnss.home.commonFuctions.successCase.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.SuccessCaseWhole;


/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/4
 * @desc    行业选择
 */
public class SuccessCaseIndustryFragment extends BaseSuccessCaseWholeFragment {

    public SuccessCaseIndustryFragment(SuccessCaseWhole successCaseWhole, OnSelectListener onSelectListener) {
        super(successCaseWhole, onSelectListener);
    }

    public static SuccessCaseIndustryFragment getInstance(SuccessCaseWhole successCaseWhole, OnSelectListener onSelectListener) {
        return new SuccessCaseIndustryFragment(successCaseWhole, onSelectListener);
    }

    @Override
    public  void initView(ViewGroup rootView) {

    }

    @Override
    public void onResetClick(View view) {

    }

    @Override
    public BaseWhole onConfirmClick(View view, BaseWhole successCaseWhole) {
        return null;
    }
}
