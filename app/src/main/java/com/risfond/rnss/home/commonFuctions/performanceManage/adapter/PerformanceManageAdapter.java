package com.risfond.rnss.home.commonFuctions.performanceManage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.PerformanceSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 * 绩效管理搜索
 */

public class PerformanceManageAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PerformanceSearch> data;
/*    private String[] positionState = new String[]{"加入项目", "推给客户", "否决人选", "推给顾问"
            , "人选放弃", "客户面试", "客户否决", "确认Offer"
            , "成功入职", "预约面试", "已发Offer", "人选离职"};*/

    public PerformanceManageAdapter(Context context, List<PerformanceSearch> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_performance_manage_page, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            try {
                PerformanceSearch customer = data.get(position);
                mholder.tvName.setText(customer.getStaffName() + " · " + customer.getCompanyName());
                mholder.mJobDate.setText(customer.getJobDate() + "");
                mholder.tvStatus.setImageResource(stateResource(customer.getCheckStatus()));
//                mholder.mJobCandidateStatus.setText(positionState[Integer.parseInt(customer.getJobCandidateStatus()) - 1]);
                mholder.mJobCandidateStatus.setText(stateJobCandidate(customer.getJobCandidateStatus()));
                mholder.mJobCandidateName.setText(customer.getJobCandidateName());
                mholder.mJobTitle.setText(customer.getJobTitle());
//            mholder.mTvInvoiceMoney.setText(NumberUtil.formatString(Integer.parseInt(customer.getJobSalary())));
                mholder.mTvInvoiceMoney.setText(customer.getJobSalary());
                OnItemClickListener(holder, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String stateJobCandidate(String jobCandidateStatus) {
        String stateStr = "";
        switch (jobCandidateStatus) {
            case "2":
                stateStr = "推给客户";
                break;
            case "6":
                stateStr = "客户面试";
                break;
            case "8":
                stateStr = "发OFFER";
                break;
            case "9":
                stateStr = "成功入职";
                break;
            default:
                stateStr = "未知状态";
                break;
        }
        return stateStr;
    }

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 0:
                id = R.mipmap.l1;
                break;
            case 1:
                id = R.mipmap.l4;
                break;
            case 2:
                id = R.mipmap.l3;
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

    public void updateData(List<PerformanceSearch> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_invoice_name)
        TextView tvName;
        @BindView(R.id.iv_invoice_state)
        ImageView tvStatus;
        @BindView(R.id.tv_invoice_stype_num)
        TextView mJobCandidateStatus;
        @BindView(R.id.tv_invoice_stype_person_num)
        TextView mJobDate;
        @BindView(R.id.tv_invoice_salary_num)
        TextView mJobCandidateName;
        @BindView(R.id.tv_salary_person_num)
        TextView mJobTitle;
        @BindView(R.id.tv_invoice_money)
        TextView mTvInvoiceMoney;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
