package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.ResumeSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/11/8.
 */

public class ResumeQuickSearchAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<String> data;

    public ResumeQuickSearchAdapter(Context context, List<String> data) {
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
    public ResumeQuickSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resume_list, parent, false);
        ResumeQuickSearchViewHolder holder = new ResumeQuickSearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ResumeQuickSearchViewHolder) {
            ResumeQuickSearchViewHolder mholder = (ResumeQuickSearchViewHolder) holder;

            mholder.tvAge.setText(data.get(position) + "岁");
//            mholder.tvExperience.setText(search.getWorkExperience() + "年经验");
//            mholder.tvResumeNumber.setText(search.getResumeCode());
//            mholder.tvUpdateTime.setText(search.getUpdateDate());

            OnItemClickListener(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
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

    public static class ResumeQuickSearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_recommend_state)
        ImageView tvRecommendState;
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_experience)
        TextView tvExperience;
        @BindView(R.id.tv_education)
        TextView tvEducation;
        @BindView(R.id.tv_resume_number)
        TextView tvResumeNumber;
        @BindView(R.id.tv_update_time)
        TextView tvUpdateTime;

        public ResumeQuickSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
