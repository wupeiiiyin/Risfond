package com.risfond.rnss.home.commonFuctions.invoiceManage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.CustomerDetail;
import com.risfond.rnss.entry.InvoiceDetail;
import com.risfond.rnss.entry.InvoiceDetailResponse;
import com.risfond.rnss.home.commonFuctions.invoiceManage.modelImpl.InvoiceDetailImpl;
import com.risfond.rnss.home.commonFuctions.invoiceManage.modelInterface.IInvoiceDetail;
import com.risfond.rnss.home.customer.adapter.CustomerDetailAdapter;
import com.risfond.rnss.home.customer.modelImpl.CustomerDetailImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerDetail;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 发票详情
 */
public class InvoiceDetailActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_invoice_name)
    TextView mTvInvoiceName;
    @BindView(R.id.iv_invoice_state)
    ImageView mIvInvoiceState;
    @BindView(R.id.tv_invoice_time)
    TextView mTvInvoiceTime;
    @BindView(R.id.tv_invoice_stype_num)
    TextView mTvInvoiceStypeNum;
    @BindView(R.id.tv_invoice_money)
    TextView mTvInvoiceMoney;
    @BindView(R.id.tv_invoice_stype_person_num)
    TextView mTvInvoiceStypePersonNum;
    @BindView(R.id.tv_invoice_salary_num)
    TextView mTvInvoiceSalaryNum;
    @BindView(R.id.tv_salary_person_num)
    TextView mTvSalaryPersonNum;

    @BindView(R.id.tv_salary_invoice_company_name_num)
    TextView mTvSalaryInvoiceCompanyNameNum;
    @BindView(R.id.tv_salary_invoice_account_name_num)
    TextView mtvSalaryInvoiceAccountNameNum;

    @BindView(R.id.tv_salary_invoice_remake_name_num)
    TextView mtvSalaryInvoiceRemakeNameNum;
    @BindView(R.id.tv_salary_invoice_phone_num)
    TextView mtvSalaryInvoicePhoneNum;
    @BindView(R.id.tv_salary_invoice_addresss_num)
    TextView mtvSalaryInvoiceAddresssNum;
    @BindView(R.id.tv_salary_invoice_open_account_num)
    TextView mtvSalaryInvoiceOpenAccountNum;
    @BindView(R.id.tv_salary_invoice_open_bank_num)
    TextView mtvSalaryInvoiceOpenBankNum;
    @BindView(R.id.tv_salary_bank_num_name_num)
    TextView mtvSalaryBankNumNameNum;

    @BindView(R.id.ll_invoice_spe)
    LinearLayout mllInvoiceSpe;
    @BindView(R.id.ll_invoice)
    RelativeLayout mllInvoice;


    private Context context;
    private IInvoiceDetail iCustomerDetail;
    private Map<String, String> request = new HashMap<>();
    private String id;
    private String url;
    private InvoiceDetailResponse customerDetail;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_invoice_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = InvoiceDetailActivity.this;
        tvTitle.setText("发票详情");
        iCustomerDetail = new InvoiceDetailImpl();
        id = getIntent().getStringExtra("id");
        url = getIntent().getStringExtra("url");

        customDetailRequest();
    }

    private void customDetailRequest() {
        DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        request.put("id", id);
        iCustomerDetail.positionSearchRequest(SPUtil.loadToken(context), request, url, this);
    }

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Context context, String id, String url) {
        Intent intent = new Intent(context, InvoiceDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof InvoiceDetailResponse) {
                    customerDetail = (InvoiceDetailResponse) obj;
                    if (mllInvoice != null) {
                        mllInvoice.setVisibility(View.VISIBLE);
                    }
                    initDate(customerDetail.getData());
                }
            }
        });
    }

    private void initDate(InvoiceDetail date) {
        try {

            if(mllInvoiceSpe != null){
                if (date.getType() == 1) {
                    if(mllInvoiceSpe != null){
                        mllInvoiceSpe.setVisibility(View.GONE);
                    }
                } else {
                    if(mllInvoiceSpe != null){
                        mllInvoiceSpe.setVisibility(View.VISIBLE);
                    }
                }
            }

            if(mTvInvoiceName != null){
                mTvInvoiceName.setText(date.getStaffName() + " · " + date.getCompanyName());
            }
            if(mIvInvoiceState != null){
                mIvInvoiceState.setImageResource(stateResource(date.getStatus()));
            }
            if(mTvInvoiceTime != null){
                mTvInvoiceTime.setText(date.getApplicationDate());
            }
            if(mTvInvoiceStypeNum != null){
                mTvInvoiceStypeNum.setText(stateInvoiceType(date.getType()));
            }
            if(mTvInvoiceMoney != null){
                mTvInvoiceMoney.setText(NumberUtil.formatString(date.getAmount()));
            }
            if(mTvInvoiceStypePersonNum != null){
                mTvInvoiceStypePersonNum.setText(date.getDrawerStaffName());
            }
            if(mTvInvoiceSalaryNum != null){
                mTvInvoiceSalaryNum.setText(date.getIncomeStatus());
            }
            if(mTvSalaryPersonNum != null){
                mTvSalaryPersonNum.setText(date.getAccountDay());
            }

            if(mTvSalaryInvoiceCompanyNameNum != null){
                mTvSalaryInvoiceCompanyNameNum.setText(date.getClientName());
            }
            if(mtvSalaryInvoiceAccountNameNum != null){
                mtvSalaryInvoiceAccountNameNum.setText(date.getTaxIdentityNumber());
            }

            if(mtvSalaryInvoiceRemakeNameNum != null){
                mtvSalaryInvoiceRemakeNameNum.setText(date.getMemo());
            }
            if(mtvSalaryInvoicePhoneNum != null){
                mtvSalaryInvoicePhoneNum.setText(date.getCallNumber());
            }
            if(mtvSalaryInvoiceAddresssNum != null){
                mtvSalaryInvoiceAddresssNum.setText(date.getClientAddress());
            }
            if(mtvSalaryInvoiceOpenAccountNum != null){
                mtvSalaryInvoiceOpenAccountNum.setText(date.getBankAccountName());
            }
            if(mtvSalaryInvoiceOpenBankNum != null){
                mtvSalaryInvoiceOpenBankNum.setText(date.getBankName());
            }
            if(mtvSalaryBankNumNameNum != null){
                mtvSalaryBankNumNameNum.setText(date.getBankAccount());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private String stateInvoiceType(int type) {
        String invoiceStr = "";
        switch (type) {
            case 1:
                invoiceStr = "普通发票";
                break;
            case 2:
                invoiceStr = "专用发票";
                break;
            default:
                invoiceStr = "未知发票";
                break;
        }
        return invoiceStr;
    }

    @Override
    public void onFailed(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    @Override
    public void onError(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 1:
                id = R.mipmap.l1;
                break;
            case 2:
                id = R.mipmap.l2;
                break;
            case 3:
                id = R.mipmap.l3;
                break;
            default:
                break;
        }
        return id;
    }
}
