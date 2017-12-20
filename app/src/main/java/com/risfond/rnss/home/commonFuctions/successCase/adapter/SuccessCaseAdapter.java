package com.risfond.rnss.home.commonFuctions.successCase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.SuccessCase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chh on 2017/8/16.
 * 成功案例列表
 */

public class SuccessCaseAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SuccessCase> data;

    public SuccessCaseAdapter(Context context, List<SuccessCase> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_success_case_list, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            SuccessCase search = data.get(position);
            mholder.tvPosition.setText(search.getJobTitle()+"");
            mholder.tvCompany.setText(search.getJobTitle()+"");
            mholder.tvResumeNumber.setText(search.getName()+"");
            mholder.tvUpdateTime.setText(search.getCreatedTime()+"");

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

    public void updateData(List<SuccessCase> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_resume_number)
        TextView tvResumeNumber;
        @BindView(R.id.tv_update_time)
        TextView tvUpdateTime;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
