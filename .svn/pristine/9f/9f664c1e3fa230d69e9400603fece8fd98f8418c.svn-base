package com.risfond.rnss.common.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.risfond.rnss.R;
import com.risfond.rnss.login.activity.LoginActivity;
import com.risfond.rnss.widget.ShowUpdateDialog;

/**
 * Created by Abbott on 2017/3/24.
 * 对话框工具类
 */

public class DialogUtil {
    public static DialogUtil mInstance;
    public static final int BUTTON_OK = 0;
    public static final int BUTTON_CANCEL = 1;
    private static AlertDialog dlg;
    private static AlertDialog loginDlg;
    private static ShowUpdateDialog customDialog;
    private static Animation operatingAnim;
    private static ImageView imageView;

    private DialogUtil() {
    }

    /*
* 版本更新对话框
* */
    public void showNoticeDialog(Context context, String newVersionName, String updateContent, final PressCallBack callback) {
        ShowUpdateDialog.Builder builder = new ShowUpdateDialog.Builder(context);
        String current = updateContent.replace("\\n", "\n");
        builder.setMessage(current + "");
        builder.setTitle("新版本升级");
        builder.setTitleTime(newVersionName + "");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    dialog.dismiss();
                    callback.onPressButton(BUTTON_CANCEL);
                }
            }
        });

        builder.setNegativeButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        //设置你的操作事项
                        if (callback != null) {
                            dialog.dismiss();
                            callback.onPressButton(BUTTON_OK);
                        }
                    }
                });

        customDialog = builder.create();
        customDialog.show();
        customDialog.setCanceledOnTouchOutside(true);
    }

    public void showConfigDialog(Context context, String msg, String positiveStr, String negativeStr, final PressCallBack callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    dialog.dismiss();
                    callback.onPressButton(BUTTON_OK);
                }
            }
        });
        builder.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    dialog.dismiss();
                    callback.onPressButton(BUTTON_CANCEL);
                }
            }
        });
        AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.color_title_in));

    }

    public void showConfigMsgDialog(Context context, int titleId, int msgId, final PressCallBack callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(msgId);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    dialog.dismiss();
                    callback.onPressButton(BUTTON_OK);
                }
            }
        });
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_title_in));

    }

    public void showMsgDialog(Context context, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_title_in));

    }

    public void showToLoginDialog(final Context context) {
        if (loginDlg == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.connect_exit);
            builder.setMessage(R.string.connect_time_out);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtil.clearIMLoginData(context);
                    //                    SPUtil.saveCloseMsg(context, false);
                    EMClient.getInstance().logout(true);
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    AppManager.getInstance().finishAllActivity();
                }
            });
            loginDlg = builder.show();
            loginDlg.setCanceledOnTouchOutside(false);
            loginDlg.setCancelable(false);
            loginDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
            loginDlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
        }

    }

    /**
     * 加载话框
     *
     * @param context
     * @param msg
     */
    public void showLoadingDialog(Context context, String msg) {
        dlg = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_loading_text);
        imageView = (ImageView) view.findViewById(R.id.dialog_loading_img);
        text.setText(msg);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlg.show();
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(view);

        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.loading);
        operatingAnim.setInterpolator(new LinearInterpolator());
        openAnim();
    }

    /**
     * 拨打电话返回信息对话框
     */
    public void showCallMsg(Context context) {
        dlg = new AlertDialog.Builder(context, R.style.dialogStyle).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_call_msg, null);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlg.show();
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(view);
    }

    public void showCall(Context context, final PressCallBack callBack) {
        final Dialog bottomDialog = new Dialog(context, com.hyphenate.easeui.R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_call, null);
        contentView.findViewById(R.id.system_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onPressButton(DialogUtil.BUTTON_OK);
                bottomDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.phone_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onPressButton(DialogUtil.BUTTON_CANCEL);
                bottomDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    /**
     * 关闭拨打电话返回信息对话框
     */
    public void closeCallMsgDialog() {
        if (dlg != null) {
            dlg.dismiss();
        }
    }

    /**
     * 开始旋转
     */
    public void openAnim() {
        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }
    }

    /**
     * 停止旋转
     */
    public void closeAnim() {
        if (operatingAnim != null) {
            imageView.clearAnimation();
        }
    }

    /**
     * 关闭请求对话框
     */
    public void closeLoadingDialog() {
        if (dlg != null) {
            closeAnim();
            dlg.dismiss();
        }
    }

    public interface PressCallBack {
        void onPressButton(int buttonIndex);
    }

    /**
     * 单一实例
     *
     * @return
     */
    public static DialogUtil getInstance() {
        if (mInstance == null) {
            synchronized (DialogUtil.class) {
                if (mInstance == null) {
                    mInstance = new DialogUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }
}
