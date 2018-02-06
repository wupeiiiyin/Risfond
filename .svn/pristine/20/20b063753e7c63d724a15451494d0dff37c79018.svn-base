package com.risfond.rnss.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.DialogUtil;


/**
 * Created by Abbott on 2017/3/24.
 * 忘记密码对话框
 */

public class ShowRegisterSuccessDialog extends Dialog {
    private TextView tvKnowBtn;
    private DialogUtil.PressCallBack callBack;

    public ShowRegisterSuccessDialog(@NonNull Context context, DialogUtil.PressCallBack callBack) {
        super(context);
        this.callBack = callBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_register_success);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        tvKnowBtn = (TextView) findViewById(R.id.tv_know_btn);

        tvKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                callBack.onPressButton(0);
            }
        });
    }
}

