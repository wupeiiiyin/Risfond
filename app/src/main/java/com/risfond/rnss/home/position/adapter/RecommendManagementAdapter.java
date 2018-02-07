package com.risfond.rnss.home.position.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.ScreenUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.Recommend;
import com.risfond.rnss.entry.Recommend2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/14.
 */

public class RecommendManagementAdapter extends RecyclerView.Adapter<RecommendManagementAdapter.RecommendManagementViewHolder> {

    private Context context;
    private List<Recommend> data;
    private LinearLayout.LayoutParams lp;
    private LinearLayout.LayoutParams lp2;

    public RecommendManagementAdapter(Context context, List<Recommend> data) {
        this.context = context;
        this.data = data;
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, ScreenUtil.dip2px(context, 10), 0, ScreenUtil.dip2px(context, 10));
        lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, ScreenUtil.dip2px(context, 10), 0, 0);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecommendManagementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_management, parent, false);
        RecommendManagementViewHolder holder = new RecommendManagementViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendManagementViewHolder holder, int position) {
        Recommend2 recommend2 = source(data.get(position).getRecomtype());

        holder.tvNumbers.setText(String.valueOf(data.get(position).getTotal()));
        holder.tvDescribeCn.setText(recommend2.getNameCN());
        holder.tvDescribeEn.setText(recommend2.getNameEN());
        holder.ivPosition.setImageResource(recommend2.getRes());
        if (position == data.size() - 1) {
            holder.rlRecommendManagement.setLayoutParams(lp);
        } else {
            holder.rlRecommendManagement.setLayoutParams(lp2);
        }
        OnItemClickListener(holder, position);
    }

    private Recommend2 source(int state) {
        Recommend2 recommend2 = new Recommend2();
        switch (state) {
            case 0:
                recommend2.setRes(R.mipmap.position0);
                recommend2.setNameCN("全部");
                recommend2.setNameEN("COMPLETE");
                break;
            case 1:
                recommend2.setRes(R.mipmap.position1);
                recommend2.setNameCN("加项目");
                recommend2.setNameEN("ADD PROJECT");
                break;
            case 4:
                recommend2.setRes(R.mipmap.position4);
                recommend2.setNameCN("给顾问");
                recommend2.setNameEN("TO THE CONSULTANT");
                break;
            case 2:
                recommend2.setRes(R.mipmap.position2);
                recommend2.setNameCN("给客户");
                recommend2.setNameEN("TO THE CONNECTION");
                break;
            case 10:
                recommend2.setRes(R.mipmap.position10);
                recommend2.setNameCN("约面试");
                recommend2.setNameEN("JOB INTERVIEW");
                break;
            case 6:
                recommend2.setRes(R.mipmap.position6);
                recommend2.setNameCN("已面试");
                recommend2.setNameEN("JOB INTERVIEW");
                break;
            case 11:
                recommend2.setRes(R.mipmap.position11);
                recommend2.setNameCN("发OFFER");
                recommend2.setNameEN("GET OFFER");
                break;
            case 8:
                recommend2.setRes(R.mipmap.position8);
                recommend2.setNameCN("收OFFER");
                recommend2.setNameEN("GET OFFER");
                break;
            case 9:
                recommend2.setRes(R.mipmap.position9);
                recommend2.setNameCN("已入职");
                recommend2.setNameEN("TAKING WORK");
                break;
            case 12:
                recommend2.setRes(R.mipmap.position12);
                recommend2.setNameCN("人选离职");
                recommend2.setNameEN("LEAVING THE COMPANY");
                break;
            default:
                recommend2.setRes(R.mipmap.default_image);
                recommend2.setNameCN("");
                recommend2.setNameEN("");
                break;
        }
        return recommend2;
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

    public void updateData(List<Recommend> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class RecommendManagementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_recommend_management)
        RelativeLayout rlRecommendManagement;
        @BindView(R.id.tv_numbers)
        TextView tvNumbers;
        @BindView(R.id.tv_describe_cn)
        TextView tvDescribeCn;
        @BindView(R.id.tv_describe_en)
        TextView tvDescribeEn;
        @BindView(R.id.iv_position)
        ImageView ivPosition;

        public RecommendManagementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
