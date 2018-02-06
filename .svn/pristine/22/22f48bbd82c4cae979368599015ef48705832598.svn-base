package com.risfond.rnss.home.commonFuctions.successCase.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.WheelDialog;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.entry.SuccessCaseWhole;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/6
 * @desc 成功案例  其他
 */
public class SuccessCaseMoreFragment extends BaseSuccessCaseWholeFragment implements View.OnClickListener, WheelDialog.TimeSelected {
    private TextView mStartTime;
    private TextView mEndTime;
    private EditText mStartSalary;
    private EditText mEndSalary;

    private int mCurrentSelectViewId;
    public SuccessCaseMoreFragment(SuccessCaseWhole baseWhole, OnSelectListener onSelectListener) {
        super(baseWhole, onSelectListener);
    }

    public static SuccessCaseMoreFragment getInstance(SuccessCaseWhole baseWhole, OnSelectListener onSelectListener) {
        return new SuccessCaseMoreFragment(baseWhole, onSelectListener);
    }

    @Override
    protected void initView(ViewGroup rootView) {
        View view = getLayoutInflater().inflate(R.layout.fragment_successcase_more_layout, rootView, false);
        mStartTime = (TextView) view.findViewById(R.id.id_start_time);
        mEndTime = (TextView) view.findViewById(R.id.id_end_time);
        mStartTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);
        mStartSalary = (EditText) view.findViewById(R.id.id_start_salary);
        mEndSalary = (EditText) view.findViewById(R.id.id_end_salary);
        rootView.addView(view);
        initView();
    }
    private void initView() {
        SuccessCaseWhole baseWhole = (SuccessCaseWhole) getBaseWhole();
        mStartTime.setText(baseWhole.getStartTime());
        mEndTime.setText(baseWhole.getEndTime());

        if (baseWhole.getStartYearlySalary() >0) {
            mStartSalary.setText(String.valueOf(baseWhole.getStartYearlySalary()));
        }
        if (baseWhole.getEndYearlySalary() >0) {
            mEndSalary.setText(String.valueOf(baseWhole.getEndYearlySalary()));
        }


    }

    @Override
    protected void onResetClick(View view) {
        mStartTime.setText("");
        mEndTime.setText("");
        mStartSalary.setText("");
        mEndSalary.setText("");
    }

    @Override
    protected BaseWhole onConfirmClick(View view, BaseWhole whole) {
        SuccessCaseWhole successCaseWhole = (SuccessCaseWhole) whole;
        successCaseWhole.setStartTime(mStartTime.getText().toString());
        successCaseWhole.setEndTime(mEndTime.getText().toString());
        successCaseWhole.setStartYearlySalary(Integer.parseInt(mStartSalary.getText().toString().trim().length()>0?mStartSalary.getText().toString().trim():"0"));
        successCaseWhole.setEndYearlySalary(Integer.parseInt(mEndSalary.getText().toString().trim().length()>0?mEndSalary.getText().toString().trim():"0"));
        return successCaseWhole;
    }

    @Override
    public void onClick(View v) {
        mCurrentSelectViewId = v.getId();
        WheelDialog.getInstance().showDateSelectDialog(getContext(), "",
                WheelDialog.TYPE_FORMAT_yyyyMMdd, WheelDialog.TYPE_YEAR_MONTH_DATE, mEndTime.getText().toString(), this);
    }

    @Override
    public void onTimeSelected(String time) {
        switch (mCurrentSelectViewId) {
            case R.id.id_start_time:
                mStartTime.setText(time);
                break;
            case R.id.id_end_time:
                mEndTime.setText(time);
                break;
        }
    }
}
