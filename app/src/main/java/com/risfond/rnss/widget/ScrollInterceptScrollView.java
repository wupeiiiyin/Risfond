package com.risfond.rnss.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by Abbott on 2018/2/6.
 */

public class ScrollInterceptScrollView extends ScrollView {
    private int downX, downY;
    private int mTouchSlop;
//    private View childView;// 子View（ScrollerView的唯一子类）
    private int y;// 点击时y坐标
    private Rect rect = new Rect();// 矩形(用来保存inner的初始状态，判断是够需要动画回弹效果)
    // y方向上当前触摸点的前一次记录位置
    private int previousY = 0;
    // y方向上的触摸点的起始记录位置
    private int startY = 0;
    // y方向上的触摸点当前记录位置
    private int currentY = 0;
    // y方向上两次移动间移动的相对距离
    private int deltaY = 0;

    // 第一个子视图
    private View childView;

    // 用于记录childView的初始位置
    private Rect topRect = new Rect();

    //水平移动搞定距离
    private float moveHeight;
    public ScrollInterceptScrollView(Context context) {
        this(context, null);
    }

    public ScrollInterceptScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollInterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollInterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (null == childView) {
            return super.dispatchTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                previousY = startY;

                // 记录childView的初始位置
                topRect.set(childView.getLeft(), childView.getTop(),
                        childView.getRight(), childView.getBottom());
                moveHeight = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = (int) event.getY();
                deltaY = currentY - previousY;
                previousY = currentY;

                //判定是否在顶部或者滑到了底部
                if ((!childView.canScrollVertically(-1) && (currentY - startY) > 0) || (!childView.canScrollVertically(1) && (currentY - startY) < 0)) {
                    //计算阻尼
                    float distance = currentY - startY;
                    if (distance < 0) {
                        distance *= -1;
                    }

                    float damping = 0.5f;//阻尼值
                    float height = getHeight();
                    if (height != 0) {
                        if (distance > height) {
                            damping = 0;
                        } else {
                            damping = (height - distance) / height;
                        }
                    }
                    if (currentY - startY < 0) {
                        damping = 1 - damping;
                    }

                    //阻力值限制再0.3-0.5之间，平滑过度
                    damping *= 0.25;
                    damping += 0.25;

                    moveHeight = moveHeight + (deltaY * damping);

                    childView.layout(topRect.left, (int) (topRect.top + moveHeight), topRect.right,
                            (int) (topRect.bottom + moveHeight));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!topRect.isEmpty()) {
                    //开始回移动画
                    upDownMoveAnimation();
                    // 子控件回到初始位置
                    childView.layout(topRect.left, topRect.top, topRect.right,
                            topRect.bottom);
                }
                //重置一些参数
                startY = 0;
                currentY = 0;
                topRect.setEmpty();
                break;
        }

        return super.dispatchTouchEvent(event);
    }
    // 初始化上下回弹的动画效果
    private void upDownMoveAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
                childView.getTop(), topRect.top);
        animation.setDuration(600);
        animation.setFillAfter(true);
        //设置阻尼动画效果
        animation.setInterpolator(new DampInterpolator());
        childView.setAnimation(animation);
    }

    public class DampInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            //没看过源码，猜测是input是时间（0-1）,返回值应该是进度（0-1）
            //先快后慢，为了更快更慢的效果，多乘了几次，现在这个效果比较满意
            return 1 - (1 - input) * (1 - input) * (1 - input) * (1 - input) * (1 - input);
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                // 判断是否滑动，若滑动就拦截事件
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    //this.setTranslationY(moveY-downY);
                    // isbanlanceInterface.isbanlce(moveY-downY);
                    //invalidate();
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private int mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                // 判断是否滑动，若滑动就拦截事件
                // if (Math.abs(moveY - downY) > mTouchSlop) {
                //this.setTranslationY(moveY-downY);
                isbanlanceInterface.isbanlce(getScrollY());
                //  }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    public interface isbanlanceInterface {
        void isbanlce(float length);
    }

    public isbanlanceInterface isbanlanceInterface;

    public void setbanlaceInterface(isbanlanceInterface isbanlanceInterface) {
        this.isbanlanceInterface = isbanlanceInterface;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    @Override
    public void scrollTo(int x, int y) {
        if (x == 0 && y == 0 || y <= 0) {
            super.scrollTo(x, y);
        }
    }
    //====================================================================================================
    private boolean isSlowlyChange = true;
    public void setSlowlyChange(boolean slowlyChange) {
        this.isSlowlyChange = slowlyChange;
    }

//    /**
//     * 初始化设置参数
//     *
//     * @param titleView
//     * @param referenceView
//     * @param backgroundColor
//     * @param backgroundColorRGB
//     */
//    public void initAlphaTitle(View titleView, View referenceView, int backgroundColor, int[] backgroundColorRGB) {
//
//    }
//        this.mTitleView = titleView;
//        this.mReferenceView = referenceView;
//        this.backgroundColor = backgroundColor;
//        this.backgroundColorRGB = backgroundColorRGB;
//    }
//
//    @Override
//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//
//        if (scrollY >= mReferenceView.getTop() + mReferenceView.getMeasuredHeight()) {
//            mTitleView.setBackgroundColor(backgroundColor);
//        } else if (scrollY >= 0) {
//            if (!isSlowlyChange) {
//                mTitleView.setBackgroundColor(Color.TRANSPARENT);
//            } else {
//                float persent = scrollY * 1f / (mReferenceView.getTop() + mReferenceView.getMeasuredHeight());
//                int alpha = (int) (255 * persent);
//                int color = Color.argb(alpha, backgroundColorRGB[0], backgroundColorRGB[1], backgroundColorRGB[2]);
//                mTitleView.setBackgroundColor(color);
//            }
//        }
//
    }

