package com.risfond.rnss.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.risfond.rnss.R;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.AppUpdateInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager implements ResponseCallBack {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    private static final int CHECK_FAILED = 3;
    private static final int CHECK_ERROR = 4;
    private static final int CHECK_FINISH = 5;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    public int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    public AlertDialog dialog;

    private boolean isShowToast;
    private Map<String, String> request = new HashMap<>();
    private IAppUpdate iAppUpdate;
    private int versionCode;
    private int serviceCode;
    private String serviceDesc = "";
    private String servicedate = "";
    private AppUpdateInfo appUpdateInfo;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    mProgress.setProgress(100);
                    // 安装文件
                    installApk();
                    break;
                case CHECK_FINISH:
                    DialogUtil.getInstance().closeLoadingDialog();
                    update();
                    break;
                case CHECK_FAILED:
                    if (isShowToast) {
                        DialogUtil.getInstance().closeLoadingDialog();
                        ToastUtil.showShort(mContext, msg.obj.toString());
                    }
                    break;
                case CHECK_ERROR:
                    if (isShowToast) {
                        DialogUtil.getInstance().closeLoadingDialog();
                        ToastUtil.showShort(mContext, msg.obj.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public UpdateManager(Context context, boolean isShowToast) {
        this.mContext = context;
        this.isShowToast = isShowToast;
        iAppUpdate = new AppUpdateImpl();
    }

    private void update() {
        if (serviceCode > versionCode) {
            //            showNoticeDialog(mContext,"V1.11   06-17  18:00","· RNSS系统扫码登陆：\n" +
            //                    "通过APP扫描二维码登陆RNSS系统\n" +
            //                    "· 发票管理：\n" +
            //                    "通过APP查看发票申请的审核状况，以及发票详情\n" +
            //                    "· 绩效审核：\n" +
            //                    "通过APP查看绩效申请的审核情况");
            showNoticeDialog(mContext, servicedate, serviceDesc);
        } else {
            if (isShowToast) {
                ToastUtil.showShort(mContext, "当前已是最新版本");
            }
        }
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        // 获取当前软件版本
        versionCode = getVersionCode(mContext);
        request.put("Id", "1");
        if (isShowToast) {
            DialogUtil.getInstance().showLoadingDialog(mContext, "检查更新中...");
        }
        iAppUpdate.update(request, SPUtil.loadToken(mContext), this);
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo("com.risfond.rnss", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog(Context context, String newVersionName, String updateContent) {
        DialogUtil.getInstance().showNoticeDialog(context, newVersionName, updateContent, new DialogUtil.PressCallBack() {
            @Override
            public void onPressButton(int buttonIndex) {
                if (buttonIndex == DialogUtil.BUTTON_OK) {
                    showDownloadDialog();
                    cancelUpdate = false;
                    progress = 0;
                }
            }
        });
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        Builder builder = new Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });

        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.color_title_in));

//        DisplayMetrics metric = new DisplayMetrics();
//        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int widthPixels = (int)(metric.widthPixels * 0.8);  // 屏幕宽度（像素）
//        //设置弹出框的长宽
//        dialog.getWindow().setLayout(widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);

        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    private void checkResult(String str, int what) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = str;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onSuccess(Object obj) {
        appUpdateInfo = (AppUpdateInfo) obj;
        try {
            serviceCode = Integer.parseInt(appUpdateInfo.getVersion());
        } catch (NumberFormatException e) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort(mContext, "服务器版本错误");
                }
            });
        }
        try {
            serviceDesc = appUpdateInfo.getDesc();
            servicedate = appUpdateInfo.getDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkResult("", CHECK_FINISH);
    }

    @Override
    public void onFailed(String str) {
        checkResult(str, CHECK_FAILED);
    }

    @Override
    public void onError(String str) {
        checkResult(str, CHECK_ERROR);
    }

    /**
     * map转url参数
     */
    public static String map2Url(Map<String, String> paramToMap) {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuffer url = new StringBuffer();
        boolean isfist = true;
        for (Map.Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                try {
                    url.append(URLEncoder.encode(value, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url.toString();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdPath = Environment.getExternalStorageDirectory() + "/Risfond/";
                    mSavePath = sdPath;
                    URL url = new URL(URLConstant.URL_APP_DOWNLOAD + "?apptype=1");
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Token", SPUtil.loadToken(mContext));

                    OutputStream out = conn.getOutputStream();
                    //                    out.write(map2Url(request).getBytes("utf-8"));
                    out.flush();
                    out.close();

                    if (conn.getResponseCode() == 200) {
                        // 获取文件大小
                        int length = conn.getContentLength();
                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File apkFile = new File(mSavePath, "锐仕方达猎头版.apk");
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024];
                        // 写入到文件中
                        do {
                            int numRead = is.read(buf);
                            count += numRead;
                            // 计算进度条位置
                            progress = (int) (((float) count / length) * 100);
                            // 更新进度
                            mHandler.sendEmptyMessage(DOWNLOAD);
                            if (numRead <= 0) {
                                // 下载完成
                                mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            // 写入文件
                            fos.write(buf, 0, numRead);
                        } while (!cancelUpdate);// 点击取消就停止下载.
                        fos.close();
                        is.close();
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装APK文件
     */
    public void installApk() {
        File apkfile = new File(mSavePath);
        if (!apkfile.exists()) {
            return;
        }
        //        // 通过Intent安装APK文件
        File file = new File(mSavePath + "锐仕方达猎头版.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
