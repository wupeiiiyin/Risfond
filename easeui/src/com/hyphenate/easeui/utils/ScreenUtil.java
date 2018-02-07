package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextPaint;

/**
 * Created by Abbott on 2017/3/22.
 *屏幕工具类
 */

public class ScreenUtil {

    /**
     * px转dp
     *@param context
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *
     * @param context
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取根据文本的宽度
     *
     * @param text
     * @param textSize
     * @return
     */
    public static float getTextWidth(String text, float textSize) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        return textPaint.measureText(text);
    }

}
