package com.risfond.rnss.home.commonFuctions.myEvaluation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.entry.InvoiceSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 * 发票管理搜索
 */

public class EvalutionManageAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<InvoiceSearch> data;

    public EvalutionManageAdapter(Context context, List<InvoiceSearch> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ResumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_evalution_page, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            InvoiceSearch customer = data.get(position);
            mholder.tvPositionName.setText(customer.getStaffName() + " · " + customer.getCompanyName());
            mholder.tvTime.setText(customer.getApplicationDate());
            mholder.tvStatus.setImageResource(stateResource(customer.getStatus()));
            mholder.tvCompany.setText(customer.getClientName());
            mholder.mTvInvoiceStypeNum.setText(stateInvoiceType(customer.getType()));
            mholder.mTvInvoiceStypePersonNum.setText(customer.getDrawerStaffName());
            mholder.mTvInvoiceSalaryNum.setText(customer.getIncomeStatus());
            mholder.mTvSalaryPersonNum.setText(customer.getAccountDay());
            mholder.mTvInvoiceMoney.setText(NumberUtil.formatString(customer.getAmount()));
            OnItemClickListener(holder, position);
        }

    }

    private String stateInvoiceType(int type) {
        String invoiceStr = "";
        switch (type){
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

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 1:
                id = R.mipmap.cl1;
                break;
            case 2:
                id = R.mipmap.cl2;
                break;
            case 3:
                id = R.mipmap.cl3;
                break;
            case 4:
                id = R.mipmap.cl4;
                break;
            default:
                break;
        }
        return id;
    }


    private void OnItemClickListener(RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<InvoiceSearch> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_invoice_name)
        TextView tvPositionName;
        @BindView(R.id.tv_invoice_time)
        TextView tvTime;
        @BindView(R.id.tv_invoice_company)
        TextView tvCompany;
        @BindView(R.id.iv_invoice_state)
        ImageView tvStatus;
        @BindView(R.id.tv_invoice_stype_num)
        TextView mTvInvoiceStypeNum;
        @BindView(R.id.tv_invoice_stype_person_num)
        TextView mTvInvoiceStypePersonNum;
        @BindView(R.id.tv_invoice_salary_num)
        TextView mTvInvoiceSalaryNum;
        @BindView(R.id.tv_salary_person_num)
        TextView mTvSalaryPersonNum;
        @BindView(R.id.tv_invoice_money)
        TextView mTvInvoiceMoney;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
