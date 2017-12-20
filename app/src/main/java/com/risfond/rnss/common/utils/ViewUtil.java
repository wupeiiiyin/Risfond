package com.risfond.rnss.common.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ScrollView;

/**
 * Created by Abbott on 2017/9/11.
 */

public class ViewUtil {
    public static void keyBoardChangeView(Context context, ScrollView scrollView, final View content) {
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        final int keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    int dist = content.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(50);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if ((content.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(50);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }
                }

            }
        });
    }

    /**
     * 1、获取main在窗体的可视区域
     * 2、获取main在窗体的不可视区域高度
     * 3、判断不可视区域高度，之前根据经验值，在有些手机上有点不大准，现改成屏幕整体高度的1/3
     * 1、大于屏幕整体高度的1/3：键盘显示  获取Scroll的窗体坐标
     * 算出main需要滚动的高度，使scroll显示。
     * 2、小于屏幕整体高度的1/3：键盘隐藏
     *
     * @param main   根布局
     * @param scroll 需要显示的最下方View
     */
    public static void addLayoutListener(Context context, final View main, final View scroll) {
        final int screenHeight = context.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom - rect.top;
                if (mainInvisibleHeight > screenHeight / 3) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom - rect.top;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    public static void autoScrollView(final View root, final View scrollToView) {
        final int[] scrollToPosition = {0};
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {
                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);
                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition[0] += scrollHeight;
                        } else {
                            //键盘隐藏
                            scrollToPosition[0] = 0;
                        }
                        root.scrollTo(0, scrollToPosition[0]);
                    }
                });
    }

}
