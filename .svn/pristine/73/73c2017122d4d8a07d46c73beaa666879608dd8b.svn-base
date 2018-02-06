package com.risfond.rnss.home.js;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.share.ShareUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.Evaluate;
import com.risfond.rnss.entry.ResumeInfo;
import com.risfond.rnss.home.customer.activity.CustomDetailActivity;
import com.risfond.rnss.home.customer.activity.CustomDetailActivity2;
import com.risfond.rnss.home.resume.activity.RecommendPeopleActivity;
import com.risfond.rnss.home.resume.activity.ResumeDetailActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * JS交互
 */
public class JsToJava {
    public static final String TAG = JsToJava.class.getSimpleName();
    private Context context;
    private String token;

    public JsToJava(Context context, String token) {
        super();
        this.context = context;
        this.token = token;
    }

    /**
     * 获取我的客户详情
     *
     * @return
     */
    @JavascriptInterface
    public void showClientDetail(String id,String isDetail) {
        CustomDetailActivity2.startAction(context, id, URLConstant.URL_CUSTOMER_DETAIL);
    }

    /**
     * 简历详情H5,推荐人选，获取简历ID
     *
     * @return
     */
    @JavascriptInterface
    public void recommendBtnClick(String id) {
        RecommendPeopleActivity.startAction(context, id);
    }

    /**
     * 获取简历详情
     *
     * @return
     */
    @JavascriptInterface
    public void showResumeDetail(String id) {
        ResumeDetailActivity.startAction(context, id);
    }

    /**
     * 分享
     *
     * @param data
     */
    @JavascriptInterface
    public void shareUrl(String data) {
        Evaluate evaluate = JsonUtil.fromJson(data, Evaluate.class);
        if (evaluate != null) {
            EventBus.getDefault().post(evaluate);
        } else {
            ToastUtil.showShort(context, "数据获取错误");
        }
    }

    /**
     * 获取简历详情
     *
     * @return
     */
    @JavascriptInterface
    public void makePhoneCall(String phoneNum) {

    }

    /**
     * 简历详情
     *
     * @param data
     */
    @JavascriptInterface
    public void shareInformation(String data) {
        ResumeInfo resumeInfo = JsonUtil.fromJson(data, ResumeInfo.class);
        if (resumeInfo != null) {
            EventBus.getDefault().post(resumeInfo);
        } else {
            ToastUtil.showShort(context, "数据获取错误");
        }
    }

}
