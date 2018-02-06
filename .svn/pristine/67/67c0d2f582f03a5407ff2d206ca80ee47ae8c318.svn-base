package com.risfond.rnss.home.commonFuctions.workorder.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.IHttpRequest;
import com.risfond.rnss.entry.WorkOrderDetail;
import com.risfond.rnss.entry.WorkOrderForwardResponse;
import com.risfond.rnss.entry.WorkOrderReceiveResponse;
import com.risfond.rnss.entry.eventBusVo.WorkOrderEventBus;
import com.risfond.rnss.home.commonFuctions.workorder.modelimpl.WorkOrderDetailImpl;
import com.risfond.rnss.home.commonFuctions.workorder.modelimpl.WorkOrderForwardImpl;
import com.risfond.rnss.home.commonFuctions.workorder.modelimpl.WorkOrderReceiveImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkOrderDetailActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_work_order_detail)
    LinearLayout llWorkOrderDetail;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_link_man)
    TextView tvLinkMan;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_receive)
    TextView tvReceive;
    @BindView(R.id.tv_select_people)
    TextView tvSelectPeople;
    @BindView(R.id.tv_forwarded)
    TextView tvForwarded;
    @BindView(R.id.ll_forwarded)
    LinearLayout llForwarded;

    private Context context;
    private Map<String, String> detailRequest = new HashMap<>();
    private Map<String, String> receiveRequest = new HashMap<>();
    private Map<String, String> forwardRequest = new HashMap<>();
    private IHttpRequest iWorkOrderDetail, iWorkOrderReceive, iWorkOrderForward;
    private String WorkOrderId, CounselorName;
    private int CounselorId = -1;
    private WorkOrderDetail orderDetail;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_work_order_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = WorkOrderDetailActivity.this;
        tvTitle.setText("工单详情");

        iWorkOrderDetail = new WorkOrderDetailImpl();
        iWorkOrderReceive = new WorkOrderReceiveImpl();
        iWorkOrderForward = new WorkOrderForwardImpl();
        WorkOrderId = String.valueOf(getIntent().getIntExtra("WorkOrderId", -1));

        requestDetail();
    }

    /**
     * 工单详情
     */
    private void requestDetail() {
        DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        detailRequest.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        detailRequest.put("WorkOrderId", WorkOrderId);
        iWorkOrderDetail.requestService(SPUtil.loadToken(context), detailRequest, URLConstant.URL_WORK_ORDER_DETAIL, this);
    }

    /**
     * 工单接收
     */
    private void requestReceive() {
        DialogUtil.getInstance().showLoadingDialog(context, "接收中...");
        receiveRequest.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        receiveRequest.put("WorkOrderId", WorkOrderId);
        iWorkOrderReceive.requestService(SPUtil.loadToken(context), detailRequest, URLConstant.URL_WORK_ORDER_ACCEPT, this);
    }

    /**
     * 工单转发
     */
    private void requestForward() {
        DialogUtil.getInstance().showLoadingDialog(context, "转发中...");
        forwardRequest.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        forwardRequest.put("CounselorId", String.valueOf(CounselorId));
        forwardRequest.put("WorkOrderId", WorkOrderId);
        iWorkOrderForward.requestService(SPUtil.loadToken(context), forwardRequest, URLConstant.URL_WORK_ORDER_RELAY, this);
    }

    @OnClick({R.id.tv_receive, R.id.tv_select_people, R.id.tv_forwarded, R.id.tv_mobile})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_receive://接收
                requestReceive();
                break;
            case R.id.tv_select_people://选择员工
                Intent intent = new Intent(context, OrderPeopleSelectActivity.class);
                startActivityForResult(intent, 1200);
                break;
            case R.id.tv_forwarded://转发
                if (CounselorId != -1) {
                    requestForward();
                } else {
                    ToastUtil.showShort(context, "请选择员工");
                }
                break;
            case R.id.tv_mobile://电话
                if (orderDetail.getStatus() == 2) {
                    CommonUtil.call(context, tvMobile.getText().toString().trim());
                } else {
                    ToastUtil.showShort(context, "请先接收工单");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1200) {
            CounselorId = data.getIntExtra("CounselorId", -1);
            CounselorName = data.getStringExtra("CounselorName");

            tvSelectPeople.setText(CounselorName);
        }
    }

    public static void StartAction(Context context, int WorkOrderId) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        intent.putExtra("WorkOrderId", WorkOrderId);
        context.startActivity(intent);
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof WorkOrderDetail) {
                    orderDetail = (WorkOrderDetail) obj;
                    if (orderDetail != null) {
                        initData();
                    } else {
                        ToastUtil.showShort(context, "暂无数据");
                    }
                } else if (obj instanceof WorkOrderReceiveResponse) {
                    ToastUtil.showShort(context, "接收成功");
                    requestDetail();
                    tvReceive.setVisibility(View.GONE);
                    llForwarded.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new WorkOrderEventBus("workOrder"));
                } else if (obj instanceof WorkOrderForwardResponse) {
                    ToastUtil.showShort(context, "转发成功");
                    EventBus.getDefault().post(new WorkOrderEventBus("workOrder"));
                    finish();
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });
    }

    private void initData() {
        llWorkOrderDetail.setVisibility(View.VISIBLE);
        tvCompanyName.setText(orderDetail.getCompanyName());
        tvLinkMan.setText(orderDetail.getLinkMan());
        tvMobile.setText(orderDetail.getLinkPhone());

        //缩进两个字符，使用隐藏前两个字符的方法
        /*SpannableStringBuilder span = new SpannableStringBuilder("缩进" + orderDetail.getJobDescription());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
        tvMessage.setText(orderDetail.getJobDescription());

        if (orderDetail.getIsVerify() == 1) {
            ivState.setImageResource(R.mipmap.iconyirenzheng);
        } else {
            ivState.setImageResource(R.mipmap.iconweirenzheng);
        }

        if (orderDetail.getStatus() == 1) {//未接收
            tvReceive.setVisibility(View.VISIBLE);
        } else {//已接收
            llForwarded.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }
}
