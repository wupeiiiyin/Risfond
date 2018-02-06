package com.risfond.rnss.home.commonFuctions.workorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.WorkOrderList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 */

public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.OrderViewHolder> {

    private Context context;
    private List<WorkOrderList> data;

    public WorkOrderAdapter(Context context, List<WorkOrderList> data) {
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
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_order, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        WorkOrderList workOrder = data.get(position);
        holder.tvName.setText(workOrder.getCompanyName());
        holder.tvIndustry.setText(workOrder.getIndustryDefinition());
        holder.tvPeriod.setText(workOrder.getLocation());
        holder.tvTime.setText(workOrder.getAddTimeStr());

        if (workOrder.getIsVerify() == 1) {
            holder.ivState.setImageResource(R.mipmap.iconyirenzheng);
        } else {
            holder.ivState.setImageResource(R.mipmap.iconweirenzheng);
        }

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

    public void updateData(List<WorkOrderList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
