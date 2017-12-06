package com.risfond.rnss.home.commonFuctions.successCase.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.SuccessCaseWhole;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/6
 * @desc    成功案例  其他
 */
public class SuccessCaseMoreFragment extends BaseSuccessCaseWholeFragment{
    public SuccessCaseMoreFragment(SuccessCaseWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
    }

    public static SuccessCaseMoreFragment getInstance(SuccessCaseWhole baseWhole, OnSelectListener onSelectListener) {
        return new SuccessCaseMoreFragment(baseWhole, onSelectListener);
    }
    @Override
    protected void initView(ViewGroup rootView) {
        View view = getLayoutInflater().inflate(R.layout.fragment_successcase_more_layout, rootView, false);
        rootView.addView(view);
    }

    @Override
    protected void onResetClick(View view) {

    }

    @Override
    protected BaseWhole onConfirmClick(View view, BaseWhole whole) {
        return whole;
    }
}
