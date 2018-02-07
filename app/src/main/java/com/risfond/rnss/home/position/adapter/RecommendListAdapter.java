package com.risfond.rnss.home.position.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.RecommendList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.RecommendListViewHolder> {

    private Context context;
    private List<RecommendList> data;

    public RecommendListAdapter(Context context, List<RecommendList> data) {
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
    public RecommendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_list, parent, false);
        RecommendListViewHolder holder = new RecommendListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendListViewHolder holder, int position) {
        RecommendList recommendList = data.get(position);
        GlideUtil.loadResumeImage(context, recommendList.getPhoto(), holder.ivHead, new CropCircleTransformation(context));
        holder.ivSex.setImageResource(sex(recommendList.getGender()));
        holder.ivStatus.setImageResource(status(recommendList.getStatus()));
        holder.tvName.setText(recommendList.getName());
        holder.tvCompany.setText(recommendList.getCompanyname());
        holder.tvPosition.setText(recommendList.getJobtitle());
        holder.tvPhone.setText(recommendList.getMobile());
        holder.tvCommunicateNumber.setText(String.valueOf(recommendList.getTalktotal()));
        holder.tvUpdateTime.setText(recommendList.getUpdatetime());

        OnItemClickListener(holder, position);
    }

    private int sex(String sexCode) {
        if (sexCode.equals("1")) {
            return R.mipmap.man;
        } else {
            return R.mipmap.woman;
        }
    }

    private int status(int status) {
        int resId = 0;
        switch (status) {
            case 1:
                resId = R.mipmap.tag1;
                break;
            case 2:
                resId = R.mipmap.tag2;
                break;
            case 3:
                resId = R.mipmap.tag12;
                break;
            case 4:
                resId = R.mipmap.tag7;
                break;
            case 5:
                resId = R.mipmap.tag10;
                break;
            case 6:
                resId = R.mipmap.tag4;
                break;
            case 7:
                resId = R.mipmap.tag11;
                break;
            case 8:
                resId = R.mipmap.tag5;
                break;
            case 9:
                resId = R.mipmap.tag6;
                break;
            case 10:
                resId = R.mipmap.tag3;
                break;
            case 11:
                resId = R.mipmap.tag8;
                break;
            case 12:
                resId = R.mipmap.tag9;
                break;
            default:
                break;
        }
        return resId;
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

    public void updateData(List<RecommendList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class RecommendListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_communicate_number)
        TextView tvCommunicateNumber;
        @BindView(R.id.tv_update_time)
        TextView tvUpdateTime;

        public RecommendListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
