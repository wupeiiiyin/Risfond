package com.risfond.rnss.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.risfond.rnss.R;


public class ToastUtil {

    public static void showShort(Context ctx, int resId) {
        Toast toast = Toast.makeText(ctx, resId, Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public static void showShort(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    /*
    * 位置：中心位置
    * */
    public static void showShortCent(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLong(Context ctx, int resId) {
        Toast toast = Toast.makeText(ctx, resId, Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
        //		Toast.makeText(ctx, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.show();
    }

    public static void showImgMessage(Context ctx, String str) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_prompt, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_loading_text);
        text.setText(str);
        Toast toast = new Toast(ctx);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
