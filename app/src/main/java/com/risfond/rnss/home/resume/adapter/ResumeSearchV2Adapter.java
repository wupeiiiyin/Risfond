package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.application.MyApplication;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.R.attr.data;

/**
 * Created by Abbott on 2017/5/15.
 * 简历搜索
 */

public class ResumeSearchV2Adapter extends BaseQuickAdapter<ResumeSearch,BaseViewHolder> {

    public ResumeSearchV2Adapter() {
        super(R.layout.item_resume_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ResumeSearch search) {
        ImageView ivHead = holder.getView(R.id.iv_head);
        GlideUtil.loadResumeImage(mContext, search.getPhoto(), ivHead, new CropCircleTransformation(mContext));
        holder.setText(R.id.tv_name, search.getName());
        resumeState(((ImageView) holder.getView(R.id.tv_recommend_state)), search.getStatusTxt());
        holder.setImageResource(R.id.iv_sex, sex(search.getGenderId()));
        holder.setText(R.id.tv_position, search.getJobTitle());
        holder.setText(R.id.tv_company, search.getCompanyFullName());
        holder.setText(R.id.tv_city, search.getLiveLocationTxt());
        holder.setText(R.id.tv_education, search.getEducationLevelTxt());
        holder.setText(R.id.tv_age, search.getAge() + "岁");
        holder.setText(R.id.tv_experience, search.getWorkExperience() + "年经验");
        holder.setText(R.id.tv_resume_number, search.getResumeCode());
        holder.setText(R.id.tv_update_time, search.getUpdateDate());
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
}
