package com.risfond.rnss.home.call.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.risfond.rnss.R;


public class CallDialog extends Dialog {

    /**
     * 上下文对象 *
     */
    Context context;

    private Button btn_save;

    public EditText text_name;

    public EditText text_mobile;

    public EditText text_info;


    private View.OnClickListener mClickListener;

    public CallDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public CallDialog(Context context, int theme/*, View.OnClickListener clickListener*/) {
        super(context, theme);
        this.context = context;
//        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_call);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        DisplayMetrics outMetrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(outMetrics);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //         p.height = (int) (outMetrics.heightPixels * 0.6); // 高度设置为屏幕的0.6
        //        p.width = (int) (outMetrics.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
    }
}
