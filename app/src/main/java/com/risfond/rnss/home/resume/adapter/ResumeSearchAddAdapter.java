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
import com.risfond.rnss.entry.ResumeSearchAdd;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/5/15.
 * 简历搜索
 */

public class ResumeSearchAddAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ResumeSearchAdd> data;

    public ResumeSearchAddAdapter(Context context, List<ResumeSearchAdd> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_resume_list, parent, false);
        ResumeViewHolder holder = new ResumeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResumeViewHolder) {
            ResumeViewHolder mholder = (ResumeViewHolder) holder;
            ResumeSearchAdd search = data.get(position);
            GlideUtil.loadResumeImage(context, search.getPhoto(), mholder.ivHead, new CropCircleTransformation(context));
            mholder.tvName.setText(search.getName());
            resumeState(mholder.tvRecommendState, search.getStatusTxt());
            mholder.ivSex.setImageResource(sex(search.getGenderId()));
            mholder.tvPosition.setText(search.getJobTitle());
            mholder.tvCompany.setText(search.getCompanyFullName());
            mholder.tvCity.setText(search.getLiveLocationTxt());
            mholder.tvEducation.setText(search.getEducationLevelTxt());
            mholder.tvAge.setText(search.getAge() + "岁");
            mholder.tvExperience.setText(search.getWorkExperience() + "年经验");
            mholder.tvResumeNumber.setText(search.getResumeCode());
            mholder.tvUpdateTime.setText(search.getUpdateDate());

            OnItemClickListener(holder, position);
        }

    }

    private void resumeState(ImageView view, String text) {
        if (text.equals("可推荐")) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(R.mipmap.ketui);
        } else if (text.equals("勿扰")) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(R.mipmap.wurao);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    private int sex(String sexCode) {
        if (sexCode.equals("1")) {
            return R.mipmap.man;
        } else {
            return R.mipmap.woman;
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

    public void updateData(List<ResumeSearchAdd> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
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

        public ResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
