package com.risfond.rnss.home.commonFuctions.successCase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.entry.SuccessCaseWhole;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc 成功案例 BaseFragment
 */
public abstract class BaseSuccessCaseWholeFragment extends BaseFragment {
    @BindView(R.id.id_select_rootview)
    FrameLayout mSelectRootview;
    @BindView(R.id.id_select_button_rootview)
    LinearLayout mSelectButtonRootview;
    @BindView(R.id.dismiss)
    TextView mDismissView;
    protected String TAG = this.getClass().getSimpleName();

    private SuccessCaseWhole mSuccessCaseWhole;
    private OnSelectListener mOnSelectListener;

    public BaseSuccessCaseWholeFragment(SuccessCaseWhole successCaseWhole, OnSelectListener onSelectListener) {
        mSuccessCaseWhole = successCaseWhole;
        mOnSelectListener = onSelectListener;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView(mSelectRootview);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.include_base_whole;
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tv_reset, R.id.tv_confirm,R.id.dismiss})
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reset:
                mSuccessCaseWhole = onResetClick(v, mSuccessCaseWhole);
                break;
            case R.id.tv_confirm:
                mSuccessCaseWhole = onConfirmClick(v, mSuccessCaseWhole);
                if (mOnSelectListener != null) {
                    mOnSelectListener.onConfirm(mSuccessCaseWhole);
                }
                break;
            case R.id.dismiss:
                if (mOnSelectListener != null) {
                    mOnSelectListener.onCancel();
                }
                break;
        }
    }
    public LayoutInflater getLayoutInflater() {
        return getActivity().getLayoutInflater();
    }
    public SuccessCaseWhole getSuccessCaseWhole() {
        return mSuccessCaseWhole;
    }

    public OnSelectListener getOnSelectListener() {
        return mOnSelectListener;
    }

    public void hideButton() {
        mSelectButtonRootview.setVisibility(View.GONE);
    }
    public void showButton() {
        mSelectButtonRootview.setVisibility(View.VISIBLE);
    }


    abstract void initView(ViewGroup rootView);

    abstract SuccessCaseWhole onResetClick(View view, SuccessCaseWhole successCaseWhole);

    abstract SuccessCaseWhole onConfirmClick(View view, SuccessCaseWhole successCaseWhole);


    public interface OnSelectListener {
        void onCancel();

        void onConfirm(SuccessCaseWhole successCaseWhole);
    }
}
