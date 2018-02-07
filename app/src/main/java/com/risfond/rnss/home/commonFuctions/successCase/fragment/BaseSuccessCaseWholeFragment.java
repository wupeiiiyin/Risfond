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
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.SuccessCaseWhole;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc 成功案例 BaseFragment
 */
public abstract class BaseSuccessCaseWholeFragment<T extends BaseWhole> extends BaseFragment {
    @BindView(R.id.id_select_rootview)
    FrameLayout mSelectRootview;
    @BindView(R.id.id_select_button_rootview)
    LinearLayout mSelectButtonRootview;
    @BindView(R.id.dismiss)
    TextView mDismissView;
    protected String TAG = this.getClass().getSimpleName();

    private T mBaseWhole;
    private OnSelectListener mOnSelectListener;

    public BaseSuccessCaseWholeFragment(T baseWhole, OnSelectListener onSelectListener) {
        mBaseWhole = baseWhole;
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
               onResetClick(v);
                break;
            case R.id.tv_confirm:
                mBaseWhole = onConfirmClick(v, mBaseWhole);
                if (mOnSelectListener != null) {
                    mOnSelectListener.onConfirm(mBaseWhole);
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
    public BaseWhole getBaseWhole() {
        return mBaseWhole;
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


    protected abstract void initView(ViewGroup rootView);

    protected abstract void onResetClick(View view);

    protected abstract T onConfirmClick(View view, T whole);


    public interface OnSelectListener {
        void onCancel();

        void onConfirm(BaseWhole whole);
    }
}
