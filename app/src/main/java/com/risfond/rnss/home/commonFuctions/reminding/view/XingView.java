package com.risfond.rnss.home.commonFuctions.reminding.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;

import com.risfond.rnss.R;



public class XingView extends View{
    private int center; //中心点
    private float one_radius; //外层菱形圆半径
    private float distance;//多边形之间的间距
    private int defaultSize=300; //默认大小
    private Rect str_rect;   //字体矩形
    private String[] str={"绩效", "生存", "助攻", "物理", "魔法"};
    private Paint center_paint;  //中心线画笔
    private Paint str_paint;     //字体画笔
    private Paint one_paint;     //最外层多边形画笔
    private Paint two_paint;     //第二层多边形画笔
    private Paint three_paint;   //第三次
    private Paint four_paint;    //第四层
    private Paint rank_Paint;    //等级进度画笔
    private Context mContext;
    private Paint paint;
    private float f1,f2,f3,f4,f5;


    public XingView(Context context) {
        super(context);
        this.mContext=context;   //mContext要在构造函数中进行初始化
        init();
    }

    public XingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
    }

    public XingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init();
    }
    private void init(){
        defaultSize=dp_px(defaultSize);

        //初始化字体画笔
        str_paint=new Paint();
        str_paint.setAntiAlias(true);
        str_paint.setColor(Color.BLACK);
        str_paint.setTextSize(dp_px(16));
        str_rect=new Rect();
        str_paint.getTextBounds(str[0],0,str[0].length(),str_rect);  //计算文字所在的矩形，可以得到宽高。宽：rect.width()   高： rect.height();

        //初始化中心线画笔
        center_paint=new Paint();
        center_paint.setAntiAlias(true);  //抗锯齿
        center_paint.setColor(Color.WHITE);

        //初始化最外层多边形画笔
        one_paint=new Paint();
        one_paint.setAntiAlias(true);
        //getResources().getColor(R.color.one)  此用法getColor已经过时，  ContextCompat.getColor(context,R.color.one)  由此方法代替，获取资源颜色
        one_paint.setColor(ContextCompat.getColor(mContext, R.color.one));

        one_paint.setStyle(Paint.Style.STROKE);   //设置空心

        //初始化第二层画笔
        two_paint=new Paint();
        two_paint.setAntiAlias(true);
        PathEffect effects = new DashPathEffect(new float[]{8,8,8,8},1);
        two_paint.setPathEffect(effects);
        two_paint.setStyle(Paint.Style.STROKE);
        two_paint.setColor(ContextCompat.getColor(mContext,R.color.two));

        //初始化第三层多边形画笔
        three_paint = new Paint();
        three_paint.setAntiAlias(true);

        three_paint.setStyle(Paint.Style.STROKE);
        three_paint.setColor(ContextCompat.getColor(mContext,R.color.three));


        //初始化最内层多边形画笔
        four_paint = new Paint();
        four_paint.setAntiAlias(true);
        PathEffect effects2 = new DashPathEffect(new float[]{8,8,8,8},1);
        four_paint.setPathEffect(effects2);
        four_paint.setColor(ContextCompat.getColor(mContext,R.color.four));
        four_paint.setStyle(Paint.Style.STROKE);   //设置空心

        //等级进度画笔
        rank_Paint=new Paint();
        rank_Paint.setAntiAlias(true);
        rank_Paint.setColor(Color.BLUE);
        rank_Paint.setStrokeWidth(8);  //该方法用于设置画笔的空心线宽
        rank_Paint.setStyle(Paint.Style.STROKE);   //设置空心

        paint=new Paint();



    }

    public void setValue1(float value){
        f1=one_radius-one_radius/4 * value;
        invalidate();
    }
    public void setValue2(float value){
        f2=one_radius-one_radius/4 * value;
        invalidate();
    }
    public void setValue3(float value){
        f3=one_radius-one_radius/4 * value;
        invalidate();
    }
    public void setValue4(float value){
        f4=one_radius-one_radius/4 * value;
        invalidate();
    }
    public void setValue5(float value){
        f5=one_radius-one_radius/4 * value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paintStr(canvas);
        onePolygons(canvas);
        twoPolygons(canvas);
        centerLine(canvas);
        paintRank(canvas);

        super.onDraw(canvas);

    }

    //绘制5边形的中心线
    private void centerLine(Canvas canvas){
        canvas.save();  //保存当前状态
        canvas.rotate(0,center,center);
        float startY=getPaddingTop()+2*str_rect.height();
        float endY=center;
        float du=(float) (360/5 + 0.5);
        for(int i=0;i<5;i++){
            canvas.drawLine(center,startY,center,endY,center_paint); //画直线
            canvas.rotate(du,center,center);  //旋转画布，实现循环依次画中心线
        }
        canvas.restore(); //恢复之前状态
    }

    //绘制字体
    private  void paintStr(Canvas canvas){
        canvas.drawText(str[0],center - str_rect.width()/2,(float) (getPaddingTop()+2 * str_rect.height()),str_paint);
        canvas.drawText(str[1],(float)(center + Math.sin(Math.toRadians(360/5)) * one_radius + str_rect.height()/2),
                (float)((getPaddingTop() + 2 * str_rect.height() + one_radius - Math.abs(Math.cos(Math.toRadians(360 / 5)) * one_radius))), str_paint);

        canvas.drawText(str[2], (float) (center + Math.sin(Math.toRadians(360 / 5 / 2)) * one_radius - str_rect.height() / 2 + str_rect.width() / 2),
                (float) ((Math.cos(Math.toRadians(360 / 5 / 2)) * one_radius) + center + str_rect.height()), str_paint);
        canvas.drawText(str[3], (float) (center - Math.sin(Math.toRadians(360 / 5 / 2)) * one_radius + str_rect.height() / 2 - str_rect.width() * 2),
                (float) ((Math.cos(Math.toRadians(360 / 5 / 2)) * one_radius) + center + str_rect.height()), str_paint);
        canvas.drawText(str[4], (float) (center + Math.sin(Math.toRadians(260)) * one_radius -30),
                (float) (Math.cos(Math.toRadians(360 / 3 )) * one_radius) + center + str_rect.height() , str_paint);
    }


    //绘制最外层多边形
    private void onePolygons(Canvas canvas){
//        Path path=new Path();
//        path.moveTo(center, getPaddingTop() + 2 * str_rect.height());                           //起始点
//        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 5)) * one_radius),
//                (float) (getPaddingTop() + 2 * str_rect.height() + one_radius - Math.abs(Math.cos(Math.toRadians(360 / 5)) * one_radius)));
//        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 5 + 360 / 5 / 2)) * one_radius),
//                (float) (Math.cos(Math.toRadians(360 / 5 + 360 / 5 / 2)) * one_radius) + center);
//        path.lineTo((float) (center + Math.sin(Math.toRadians(360 /5 / 2)) * one_radius),
//                (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * one_radius) + center);
//        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 5 / 2)) * one_radius),
//                (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * one_radius) + center);
//        path.lineTo((float) (center - Math.sin(Math.toRadians(360 /5 + 360 / 5 / 2)) * one_radius),
//                (float) (Math.cos(Math.toRadians(360 / 5 + 360 /5 / 2)) * one_radius) + center);

//        path.close();
//        canvas.drawPath(path,one_paint);
            canvas.drawCircle(center,center,one_radius-20,one_paint);
        canvas.drawCircle(center,center,one_radius-80,three_paint);
        canvas.drawCircle(center,center,one_radius-140,four_paint);
    }

    //利用循环绘制第2,3,4层多边形
    private void twoPolygons(Canvas canvas){


            distance = 1*one_radius/7;
            Path path = new Path();
            path.moveTo(center, getPaddingTop() + 2 * str_rect.height() + distance);                           //起始点
            path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 5)) * (one_radius - distance)), (float) (getPaddingTop() + 2 * str_rect.height() + (one_radius) - Math.abs(Math.cos(Math.toRadians(360 / 5)) * (one_radius - 20))));
            path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 5/ 2)) * (one_radius - distance)), (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * (one_radius - distance)) + center);
            path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 5 / 2)) * (one_radius - distance)), (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * (one_radius - distance)) + center);
            path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 5)) * (one_radius - distance)), (float) (getPaddingTop() + 2 * str_rect.height() + (one_radius) - Math.abs(Math.cos(Math.toRadians(360 / 5)) * (one_radius - 20))));            path.close();
            canvas.drawPath(path,two_paint);

    }

    //绘制等级进度
    private void paintRank(Canvas canvas) {

        Path path = new Path();
        path.moveTo(center, getPaddingTop() + 2 * str_rect.height() + f1);                           //起始点
        path.moveTo(center, getPaddingTop() + 2 * str_rect.height() + f1);                           //起始点
        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 5)) * (one_radius - f2)), (float) (getPaddingTop() + 2 * str_rect.height() + (one_radius) - Math.abs(Math.cos(Math.toRadians(360 / 5)) * (one_radius - f2))));
        path.lineTo((float) (center + Math.sin(Math.toRadians(360 /5/ 2)) * (one_radius - f3)), (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * (one_radius - f4)) + center);
        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 5 / 2)) * (one_radius - f4)), (float) (Math.cos(Math.toRadians(360 / 5 / 2)) * (one_radius - f5)) + center);
        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 5)) * (one_radius - f5)), (float) (getPaddingTop() + 2 * str_rect.height() + (one_radius) - Math.abs(Math.cos(Math.toRadians(360 / 5)) * (one_radius - f2))));
        path.close();
        canvas.drawPath(path, rank_Paint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode=MeasureSpec.getMode(widthMeasureSpec);
        int wSize=MeasureSpec.getSize(widthMeasureSpec);
        int hMode=MeasureSpec.getMode(heightMeasureSpec);
        int hSize=MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if(wMode==MeasureSpec.EXACTLY){
            width=wSize;
        }else {
            width=Math.min(wSize,defaultSize);
        }

        if(hMode==MeasureSpec.EXACTLY){
            height=hSize;
        }else {
            height=Math.min(hSize,defaultSize);
        }
        center = width/2;  //中心点
        one_radius = center-getPaddingTop()-2*str_rect.height();

        f1 = one_radius-one_radius / 4 * 1;
        f2 = one_radius-one_radius / 4 * 1;
        f3 = one_radius-one_radius / 4 * 1;
        f4 = one_radius-one_radius / 4 * 1;
        f5 = one_radius-one_radius / 4 * 1;

        setMeasuredDimension(width,height);
    }

    //dp转px
    public int dp_px(int values){
        float density=getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

}
