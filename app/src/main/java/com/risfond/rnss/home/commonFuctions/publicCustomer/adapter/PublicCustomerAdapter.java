package com.risfond.rnss.home.commonFuctions.publicCustomer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.entry.CustomerSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 * 简历搜索
 */

public class PublicCustomerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CustomerSearch> data;

    public PublicCustomerAdapter(Context context, List<CustomerSearch> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_public_customer, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            CustomerSearch customer = data.get(position);
            mholder.tvPosition.setText(customer.getName());
            mholder.tvTime.setText(customer.getPublicTime());
            mholder.tvSource.setText(customer.getClientSource());
            mholder.tvStatus.setText(customer.getClientStatus());
            mholder.tvCompany.setText(customer.getCompanyName());
            mholder.tvReason.setText(customer.getReason());
            mholder.tvNumber.setText(String.valueOf(customer.getMemoCount()));
            mholder.tvMoney.setText(NumberUtil.formatString(customer.getFirstAmount()));
            mholder.tvServiceCharge.setText(NumberUtil.formatString(customer.getServiceAmount()));
            OnItemClickListener(holder, position);
        }

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

    public void updateData(List<CustomerSearch> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_reason)
        TextView tvReason;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_service_charge)
        TextView tvServiceCharge;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
