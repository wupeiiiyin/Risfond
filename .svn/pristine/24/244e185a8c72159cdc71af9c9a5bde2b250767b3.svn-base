package com.risfond.rnss.home.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.CustomerSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 * 简历搜索
 */

public class CustomerSearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CustomerSearch> data;

    public CustomerSearchAdapter(Context context, List<CustomerSearch> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            CustomerSearch customer = data.get(position);
            mholder.tvPosition.setText(customer.getName());
            setLevelResource(customer.getLevel(), mholder.ivLevel);
            mholder.tvTime.setText(customer.getLastCommunicationTime());
            mholder.tvSource.setText(customer.getClientSource());
            mholder.tvStatus.setText(customer.getClientStatus());
            mholder.tvImportant.setText(customer.getClientImportStatus());
            mholder.tvCustomerNumber.setText(customer.getCode());
            mholder.tvRecordNumber.setText(String.valueOf(customer.getMemoCount()));
            mholder.tvJobNumber.setText(String.valueOf(customer.getJobCount()));
            OnItemClickListener(holder, position);
        }

    }

    private void setLevelResource(int level, ImageView view) {
        switch (level) {
            case 0:
                view.setVisibility(View.INVISIBLE);
                break;
            case 1:
                view.setImageResource(R.mipmap.level1);
                view.setVisibility(View.VISIBLE);
                break;
            case 2:
                view.setImageResource(R.mipmap.level2);
                view.setVisibility(View.VISIBLE);
                break;
            case 3:
                view.setImageResource(R.mipmap.level3);
                view.setVisibility(View.VISIBLE);
                break;
            case 4:
                view.setImageResource(R.mipmap.level4);
                view.setVisibility(View.VISIBLE);
                break;
            case 5:
                view.setImageResource(R.mipmap.level5);
                view.setVisibility(View.VISIBLE);
                break;
            default:
                break;
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
        @BindView(R.id.iv_level)
        ImageView ivLevel;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_important)
        TextView tvImportant;
        @BindView(R.id.tv_customer_number)
        TextView tvCustomerNumber;
        @BindView(R.id.tv_record_number)
        TextView tvRecordNumber;
        @BindView(R.id.tv_job_number)
        TextView tvJobNumber;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
