package com.risfond.rnss.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.PxUtils;

/**
 * Created by Abbott on 2018/2/5.
 */

public class PanelView extends View {

    private int mWidth;
    private int mHeight;

    private float mPercent;

    //第二个弧的宽度
    private int mScendArcWidth;

    //最小圆的半径
    private int mMinCircleRadius;

    //文字矩形的宽
    private int mRectWidth;

    //文字矩形的高
    private int mRectHeight;


    //文字内容
    private String mText = "";
    //第二行文字内容
    private String mText2 = "";

    //文字的大小
    private int mTextSize;
    private int mTextSize2;

    //设置文字颜色
    private int mTextColor;
    private int mArcColor;
    private int mTextColor2;

    //小圆和指针颜色
    private int mMinCircleColor;

    //刻度的个数
    private int mTikeCount;

    private Context mContext;

    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PanelView, defStyleAttr, 0);
        mArcColor = a.getColor(R.styleable.PanelView_arcColor, Color.parseColor("#5FB1ED"));
        mMinCircleColor = a.getColor(R.styleable.PanelView_pointerColor, Color.parseColor("#C9DEEE"));
        mTikeCount = a.getInt(R.styleable.PanelView_tikeCount, 12);
        mTextSize = a.getDimensionPixelSize(PxUtils.spToPx(R.styleable.PanelView_android_textSize, mContext), 24);
        mTextSize2 = a.getDimensionPixelSize(PxUtils.spToPx(R.styleable.PanelView_android_textSize, mContext), 12);
        mText = a.getString(R.styleable.PanelView_android_text);
        mScendArcWidth = 50;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = PxUtils.dpToPx(160, mContext);
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = PxUtils.dpToPx(160, mContext);
        }
        Log.e("wing", mWidth + "");
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint p = new Paint();
        int strokeWidth = 10;
        p.setStrokeWidth(strokeWidth);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(mArcColor);


        p.setStrokeWidth(mScendArcWidth);
        //绘制粗线

        RectF secondRectF = new RectF(strokeWidth + 50, strokeWidth + 50, mWidth - strokeWidth - 50, mHeight - strokeWidth - 50);
        float secondRectWidth = mWidth - strokeWidth - 50 - (strokeWidth + 50);
        float secondRectHeight = mHeight - strokeWidth - 50 - (strokeWidth + 50);
        float percent = mPercent / 100f;

        //充满的圆弧的度数    -5是大小弧的偏差
        float fill = 180 * percent;

        //空的圆弧的度数
        float empty = 180 - fill;
        //        Log.e("wing", fill + "");

        if (percent == 0) {
            p.setColor(Color.WHITE);
        }
        //画粗弧突出部分左端

        canvas.drawArc(secondRectF, 180, 0, false, p);
        //画粗弧的充满部分
        canvas.drawArc(secondRectF, 180, fill, false, p);

        p.setColor(Color.rgb(160, 221, 255));
        //画弧胡的未充满部分
        canvas.drawArc(secondRectF, 180 + fill, empty, false, p);
        //画粗弧突出部分右端
        if (percent == 1) {
            p.setColor(mArcColor);
        }

        p.setColor(mArcColor);

        //绘制小圆外圈
        p.setStrokeWidth(3);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 20, p);

        //绘制小圆内圈
        p.setColor(mMinCircleColor);
        p.setStrokeWidth(8);
        mMinCircleRadius = 15;
        canvas.drawCircle(mWidth / 2, mHeight / 2, mMinCircleRadius, p);

        p.setColor(mArcColor);

        //绘制指针
        p.setColor(mMinCircleColor);
        p.setStrokeWidth(4);

        canvas.rotate((180 * percent - 180 / 2), mWidth / 2, mHeight / 2);

        canvas.drawLine(mWidth / 2, (mHeight / 2 - secondRectHeight / 2) + mScendArcWidth / 2 + 2, mWidth / 2, mHeight / 2 - mMinCircleRadius, p);

        //将画布旋转回来
        canvas.rotate(-(180 * percent - 180 / 2), mWidth / 2, mHeight / 2);

        //文字矩形的最底部坐标
        float rectBottomY = mHeight / 2 + secondRectHeight / 3 + mRectHeight;

        p.setTextSize(mTextSize);
        mTextColor = getResources().getColor(R.color.text_blue);
        mTextColor2 = getResources().getColor(R.color.color_quick_resume_text);
        p.setColor(mTextColor);
        float txtLength = p.measureText(mText);
        canvas.drawText(mText, (mWidth - txtLength) / 2, rectBottomY, p);

        Paint p2 = new Paint();
        p2.setTextSize(mTextSize2);
        p2.setColor(mTextColor2);
//        p.setTextSize(mTextSize2);
        float txtLength2 = p2.measureText(mText2);
        canvas.drawText(mText2, (mWidth - txtLength2) / 2, rectBottomY + 70, p2);

        super.onDraw(canvas);
    }


    /**
     * 设置百分比
     *
     * @param percent
     */
    public void setPercent(float percent) {
        mPercent = percent;
        invalidate();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        mText = text;
        invalidate();
    }

    public void setContext(String text) {
        mText2 = text;
        invalidate();
    }

    /**
     * 设置圆弧颜色
     *
     * @param color
     */

    public void setArcColor(int color) {
        mArcColor = color;

        invalidate();
    }


    /**
     * 设置指针颜色
     *
     * @param color
     */
    public void setPointerColor(int color) {
        mMinCircleColor = color;

        invalidate();
    }

    /**
     * 设置文字大小
     *
     * @param size
     */
    public void setTextSize(int size) {
        mTextSize = size;

        invalidate();
    }

    /**
     * 设置粗弧的宽度
     *
     * @param width
     */
    public void setArcWidth(int width) {
        mScendArcWidth = width;

        invalidate();
    }
}
