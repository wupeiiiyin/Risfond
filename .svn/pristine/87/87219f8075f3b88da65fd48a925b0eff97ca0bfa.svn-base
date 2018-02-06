package com.risfond.rnss.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.risfond.rnss.common.utils.UmengUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Abbott on 2017/3/23.
 * fragment基类
 */

public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private View mRootView;
    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnBinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    public abstract void init(Bundle savedInstanceState);

    public abstract int getLayoutResId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UmengUtil.onResumeToFragment(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        UmengUtil.onPauseToFragment(getActivity());
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

}
