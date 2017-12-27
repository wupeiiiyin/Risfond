package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.risfond.rnss.R;

/**
 * DashboardView style 4，仿汽车速度仪表盘
 * Created by woxingxiao on 2016-12-19.
 */
public class DashboardView4 extends View {

    private int mRadius; // 扇形半径
    private int mStartAngle = 0; // 起始角度
    private int mSweepAngle = -180; // 绘制角度
    private int mMin = 0; // 最小值
    private int mMax = 180; // 最大值
    private int mSection = 9; // 值域（mMax-mMin）等分份数
    private int mPortion = 2; // 一个mSection等分份数
    private String mHeaderText = "km/h"; // 表头
    private int mVelocity = mMin; // 实时速度
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度
    private int mPLRadius; // 指针长半径
    private int mPSRadius; // 指针短半径

    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private RectF mRectFArc;
    private RectF mRectFInnerArc;
    private Rect mRectText;

    public DashboardView4(Context context) {
        this(context, null);
    }

    public DashboardView4(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mStrokeWidth = dp2px(3);
        mLength1 = dp2px(8) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(4);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mRectFArc = new RectF();
        mRectFInnerArc = new RectF();
        mRectText = new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);

        int width = resolveSize(dp2px(260), widthMeasureSpec);
        mRadius = (width - mPadding * 2 - mStrokeWidth * 2) / 2;

        // 由起始角度确定的高度
        float[] point1 = getCoordinatePoint(mRadius, mStartAngle);
        // 由结束角度确定的高度
        float[] point2 = getCoordinatePoint(mRadius, mStartAngle + mSweepAngle);
        int height = (int) Math.max(point1[1] + mRadius + mStrokeWidth * 2,
                point2[1] + mRadius + mStrokeWidth * 2);
        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());

        mCenterX = mCenterY = getMeasuredWidth() / 2f;
        mRectFArc.set(
                getPaddingLeft() + mStrokeWidth,
                getPaddingTop() + mStrokeWidth,
                getMeasuredWidth() - getPaddingRight() - mStrokeWidth,
                getMeasuredWidth() - getPaddingBottom() - mStrokeWidth
        );

        mPaint.setTextSize(sp2px(16));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);
        mRectFInnerArc.set(
                getPaddingLeft() + mLength2 + mRectText.height() + dp2px(30),
                getPaddingTop() + mLength2 + mRectText.height() + dp2px(30),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height() - dp2px(30),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height() - dp2px(30)
        );
        //改变指针的长度
        mPLRadius = mRadius - dp2px(50);
        mPSRadius = dp2px(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画板的颜色
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_dark));
        /**
         * 弧度的颜色
         * */
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
//        mPaint.setStrokeWidth(dp2px(10));
        mPaint.setStrokeWidth(20);
//        mPaint.setShader(generateSweepGradient());
        mPaint.setColor(Color.parseColor("#FDA328"));
//        canvas.drawArc(mRectFInnerArc, mStartAngle + 1, mSweepAngle - 2, false, mPaint);
        //TODO  控制圆环的大小
//        canvas.drawArc(mRectFInnerArc, -50, -130, false, mPaint);
        canvas.drawArc(mRectFInnerArc, -50, -130, false, mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);


        //小的弧度
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
//        mPaint.setStrokeWidth(dp2px(10));
        mPaint.setStrokeWidth(20);
//        mPaint.setShader(generateSweepGradient());
        mPaint.setColor(Color.parseColor("#A0DDFF"));
//        canvas.drawArc(mRectFInnerArc, mStartAngle + 1, mSweepAngle - 2, false, mPaint);
        //TODO  控制圆环的大小
        canvas.drawArc(mRectFInnerArc, 0, -35, false, mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);




        /**
         * 画指针
         */
        float θ = mStartAngle + mSweepAngle * (mVelocity - mMin) / (mMax - mMin); // 指针与水平线夹角
        int r = mRadius / 8;

        mPaint.setStrokeWidth(r / 3);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_light));
        float[] p1 = getCoordinatePoint(mPLRadius, θ);
        //绘制三角形
//        Path path = new Path();
//        path.moveTo(mCenterX,mCenterY);//多边形的起点
//        path.moveTo(mCenterX+40,mCenterY+50);
//        path.lineTo(mCenterX,mCenterY+50);
//        path.close();
//        canvas.drawPaint(path,paint);


        canvas.drawLine(p1[0], p1[1], mCenterX, mCenterY, mPaint);
        float[] p2 = getCoordinatePoint(mPSRadius, θ + 180);
        canvas.drawLine(mCenterX, mCenterY, p2[0], p2[1], mPaint);
        canvas.drawRect(mCenterX, mCenterY, p2[0], p2[1], mPaint);


        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_light));
        canvas.drawCircle(mCenterX, mCenterY, 30, mPaint);

        //指针中间的圆
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_dark));
        canvas.drawCircle(mCenterX, mCenterY, 10, mPaint);


        /**
         * 画实时度数值
         */
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPaint.setStrokeWidth(dp2px(2));
        int xOffset = dp2px(22);
        if (mVelocity >= 100) {
            drawDigitalTube(canvas, mVelocity / 100, -xOffset);
            drawDigitalTube(canvas, (mVelocity - 100) / 10, 0);
            drawDigitalTube(canvas, mVelocity % 100 % 10, xOffset);
        } else if (mVelocity >= 10) {
            drawDigitalTube(canvas, -1, -xOffset);
            drawDigitalTube(canvas, mVelocity / 10, 0);
            drawDigitalTube(canvas, mVelocity % 10, xOffset);
        } else {
            drawDigitalTube(canvas, -1, -xOffset);
            drawDigitalTube(canvas, -1, 0);
            drawDigitalTube(canvas, mVelocity, xOffset);
        }
    }

    /**
     * 数码管样式
     */
    //      1
    //      ——
    //   2 |  | 3
    //      —— 4
    //   5 |  | 6
    //      ——
    //       7
    private void drawDigitalTube(Canvas canvas, int num, int xOffset) {
        float x = mCenterX + xOffset;
        float y = mCenterY + dp2px(100);
        int lx = dp2px(5);
        int ly = dp2px(10);
        int gap = dp2px(2);

        // 1
        mPaint.setAlpha(num == -1 || num == 1 || num == 4 ? 25 : 255);
        canvas.drawLine(x - lx, y, x + lx, y, mPaint);
        // 2
        mPaint.setAlpha(num == -1 || num == 1 || num == 2 || num == 3 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx - gap, y + gap, x - lx - gap, y + gap + ly, mPaint);
        // 3
        mPaint.setAlpha(num == -1 || num == 5 || num == 6 ? 25 : 255);
        canvas.drawLine(x + lx + gap, y + gap, x + lx + gap, y + gap + ly, mPaint);
        // 4
        mPaint.setAlpha(num == -1 || num == 0 || num == 1 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx, y + gap * 2 + ly, x + lx, y + gap * 2 + ly, mPaint);
        // 5
        mPaint.setAlpha(num == -1 || num == 1 || num == 3 || num == 4 || num == 5 || num == 7
                || num == 9 ? 25 : 255);
        canvas.drawLine(x - lx - gap, y + gap * 3 + ly,
                x - lx - gap, y + gap * 3 + ly * 2, mPaint);
        // 6
        mPaint.setAlpha(num == -1 || num == 2 ? 25 : 255);
        canvas.drawLine(x + lx + gap, y + gap * 3 + ly,
                x + lx + gap, y + gap * 3 + ly * 2, mPaint);
        // 7
        mPaint.setAlpha(num == -1 || num == 1 || num == 4 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx, y + gap * 4 + ly * 2, x + lx, y + gap * 4 + ly * 2, mPaint);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];

        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

//    private SweepGradient generateSweepGradient() {
//        SweepGradient sweepGradient = new SweepGradient(mCenterX, mCenterY,
//                mColors,
//                new float[]{0, 360 / 360f, mSweepAngle / 360f}
//        );
//
//        Matrix matrix = new Matrix();
//        matrix.setRotate(mStartAngle - 3, mCenterX, mCenterY);
//        sweepGradient.setLocalMatrix(matrix);
//
//        return sweepGradient;
//    }

    public int getVelocity() {
        return mVelocity;
    }

    public void setVelocity(int velocity) {
        if (mVelocity == velocity || velocity < mMin || velocity > mMax) {
            return;
        }

        mVelocity = velocity;
        postInvalidate();
    }
}
