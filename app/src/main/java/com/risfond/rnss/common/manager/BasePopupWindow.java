package com.risfond.rnss.common.manager;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.risfond.rnss.R;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/11/29
 * @desc
 */
public abstract class BasePopupWindow {
    protected String TAG = this.getClass().getSimpleName();
    private int sum_long_time = 400;
    private float curr_alpha = -1f;//0完全不透明  1完全透明
    private float bgAlpha = -1f;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1010) {
                if (curr_alpha == 1f) {
                    //完全透明
                    bgAlpha += 0.1f;
                    if (bgAlpha > curr_alpha) {
                        clearBackground();
                        return;
                    }
                } else {
                    //半透明效果
                    bgAlpha -= 0.1f;
                    if (bgAlpha < curr_alpha) {
                        return;
                    }
                }

                setBackgroundAlpha(bgAlpha);
                Message rmsg = Message.obtain();
                rmsg.what = 0x1010;
                rmsg.obj = bgAlpha;
                mHandler.sendMessageDelayed(rmsg, sum_long_time / 10);
            }
        }
    };
    private Context mContext;
    private Activity mActivity;
    private int layoutId;
    //////////////////////////
    /**
     * 窗口关闭事件
     */
    private PopupWindow.OnDismissListener mOnDismissListener;
    /**
     * 窗口外是否可以取消
     */
    private boolean is_outsideTouchable = true;
    /**
     * 事件是否可用
     */
    private boolean is_touchable = true;
    /**
     * window默认动画样式
     */
    private int animationStyle = R.style.mypopwindow_anim_style;


    protected PopupWindow mPopupWindow;
    protected View mRootView;

    public BasePopupWindow(Context context, Activity activity, int layoutId, int animationStyle) {
        mContext = context;
        mActivity = activity;
        this.layoutId = layoutId;
        this.animationStyle = animationStyle;
        init(mContext, mActivity, layoutId);
    }

    public BasePopupWindow(Context context, Activity activity, int layoutId) {
        mContext = context;
        mActivity = activity;
        this.layoutId = layoutId;

        init(mContext, mActivity, layoutId);
    }

    public BasePopupWindow(Context context, Activity activity, int layoutId, PopupWindow.OnDismissListener onDismissListener) {
        mContext = context;
        mActivity = activity;
        this.layoutId = layoutId;
        this.mOnDismissListener = onDismissListener;
        init(mContext, mActivity, layoutId);
    }

    public BasePopupWindow init(Context context, Activity activity, int layoutId) {
        mRootView = LayoutInflater.from(context).inflate(layoutId, new FrameLayout(context), false);
        mPopupWindow = new PopupWindow(mRootView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        mPopupWindow.setTouchable(is_touchable);
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOutsideTouchable(is_outsideTouchable);
        mPopupWindow.setAnimationStyle(animationStyle);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.update();
        if (mOnDismissListener != null) {
            mPopupWindow.setOnDismissListener(mOnDismissListener);
        }
        initLayoutView(mRootView);
        return this;
    }

    public void hide() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 显示window
     *
     * @param view
     */
    public BasePopupWindow show(View view) {
        return show(view, Gravity.BOTTOM);
    }

    /**
     * 显示window
     *
     * @param view
     */
    public BasePopupWindow show(View view, int gravity) {
        return show(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示window
     *
     * @param view
     */
    public BasePopupWindow show(View view, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(view, gravity, x, y);
        }
        return this;
    }

    public void clearBackground() {
        if (oldLayoutParams != null) {
            mActivity.getWindow().setAttributes(oldLayoutParams);
        }
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置添加屏幕的背景透明度,无过渡动画
     *
     * @param bgAlpha
     */
    private WindowManager.LayoutParams oldLayoutParams;

    private BasePopupWindow setBackgroundAlpha(float bgAlpha) {
        if (oldLayoutParams == null) {
            oldLayoutParams = mActivity.getWindow().getAttributes();
        }
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }


    /**
     * 设置添加屏幕的背景透明度，根据is_tran是否需要过度动画
     *
     * @param bgAlpha
     * @param is_tran
     * @return
     */
    public BasePopupWindow setBackgroundAlpha(float bgAlpha, boolean is_tran) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.curr_alpha = bgAlpha;
        if (this.curr_alpha < 1f) {
            this.bgAlpha = 1f;
        }
        if (is_tran) {
            Message rmsg = Message.obtain();
            rmsg.what = 0x1010;
            rmsg.obj = bgAlpha;
            mHandler.sendMessage(rmsg);
        } else {
            setBackgroundAlpha(bgAlpha);
        }
        return this;
    }

    public abstract void initLayoutView(View rootView);


    public Context getContext() {
        return mContext;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public PopupWindow.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        if (mOnDismissListener != null) {
            mPopupWindow.setOnDismissListener(mOnDismissListener);
        }
    }

    public boolean is_outsideTouchable() {
        return is_outsideTouchable;
    }

    public void setIs_outsideTouchable(boolean is_outsideTouchable) {
        this.is_outsideTouchable = is_outsideTouchable;
    }

    public boolean is_touchable() {
        return is_touchable;
    }

    public void setIs_touchable(boolean is_touchable) {
        this.is_touchable = is_touchable;
    }

    public int getAnimationStyle() {
        return animationStyle;
    }

    public void setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        mPopupWindow = popupWindow;
    }
}
