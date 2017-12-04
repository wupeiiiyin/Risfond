package com.risfond.rnss.home.commonFuctions.successCase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.SuccessCaseWhole;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc 选择排序
 * >> 1：薪资，2：时间
 */
public class SuccessCaseOrderFragment extends BaseSuccessCaseWholeFragment implements View.OnClickListener {

    LinearLayout mSuccesscaseOrderSalary;
    LinearLayout mSuccesscaseOrderTime;
    LinearLayout mSuccesscaseOrderContent;


    public SuccessCaseOrderFragment(SuccessCaseWhole successCaseWhole, OnSelectListener onSelectListener) {
        super(successCaseWhole, onSelectListener);
    }

    public static SuccessCaseOrderFragment getInstance(SuccessCaseWhole successCaseWhole, OnSelectListener onSelectListener) {
        return new SuccessCaseOrderFragment(successCaseWhole, onSelectListener);
    }

    @Override
    void initView(ViewGroup rootView) {
        //隐藏按钮
        hideButton();
        View view = getLayoutInflater().inflate(R.layout.item_successcase_order_layout, rootView, false);
        mSuccesscaseOrderSalary = (LinearLayout) view.findViewById(R.id.id_successcase_order_salary);
        mSuccesscaseOrderTime = (LinearLayout) view.findViewById(R.id.id_successcase_order_time);
        mSuccesscaseOrderContent = (LinearLayout) view.findViewById(R.id.id_successcase_order_content);
        mSuccesscaseOrderSalary.setOnClickListener(this);
        mSuccesscaseOrderTime.setOnClickListener(this);
        rootView.addView(view);
        UtilHelper.outLog(TAG,getSuccessCaseWhole().toString());
        //开始解析
        parseWhole();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_successcase_order_salary:
                getSuccessCaseWhole().setOrderType(1);
                break;
            case R.id.id_successcase_order_time:
                getSuccessCaseWhole().setOrderType(2);
                break;
        }
        parseWhole();

        //关闭窗口 模拟点击确定按钮
        if (getOnSelectListener() != null) {
            getOnSelectListener().onConfirm(getSuccessCaseWhole());
        }
    }

    /**
     * 解析
     */
    private void parseWhole() {
        SuccessCaseWhole successCaseWhole = getSuccessCaseWhole();
        if (successCaseWhole.getOrderType() == 1) {
            changeSelectStatus(mSuccesscaseOrderSalary);
        }
        if (successCaseWhole.getOrderType() == 2) {
            changeSelectStatus(mSuccesscaseOrderTime);
        }
    }

    /**
     * 选中状态
     */
    private void changeSelectStatus(ViewGroup viewGroup) {
        clearAllStatus(mSuccesscaseOrderContent);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof TextView) {
                TextView textView = (TextView) viewGroup.getChildAt(i);
                textView.setTextColor(Color.parseColor("#0E97FF"));
            }
            if (viewGroup.getChildAt(i) instanceof ImageView) {
                viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 清除所有状态
     */
    private void clearAllStatus(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof LinearLayout) {
                clearAllStatus((LinearLayout) viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof TextView) {
                TextView textView = (TextView) viewGroup.getChildAt(i);
                textView.setTextColor(Color.parseColor("#969696"));
            }
            if (viewGroup.getChildAt(i) instanceof ImageView) {
                viewGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    SuccessCaseWhole onResetClick(View view, SuccessCaseWhole successCaseWhole) {
        return null;
    }

    @Override
    SuccessCaseWhole onConfirmClick(View view, SuccessCaseWhole successCaseWhole) {
        UtilHelper.outLog(TAG,successCaseWhole.toString());
        return successCaseWhole;
    }

}
