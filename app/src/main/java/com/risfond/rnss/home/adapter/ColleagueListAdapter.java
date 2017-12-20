package com.risfond.rnss.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.entry.Colleague;
import com.risfond.rnss.widget.MyRoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 * 新同事
 */

public class ColleagueListAdapter extends RecyclerView.Adapter<ColleagueListAdapter.ColleagueViewHolder> {

    private Context context;
    private List<Colleague> data;

    public ColleagueListAdapter(Context context, List<Colleague> data) {
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
    public ColleagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_colleague, parent, false);
        ColleagueViewHolder holder = new ColleagueViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ColleagueViewHolder holder, int position) {
        Colleague colleague = data.get(position);
        GlideUtil.colleagueImage(context, colleague.getStaffPhoto(), holder.ivColleague);
        holder.tvMoney.setText(NumberUtil.formatString(colleague.getPerformanceAmount()));
        holder.tvName.setText(colleague.getStaffName());
        switch (colleague.getRank()) {
            case 1:
            case 2:
            case 3:
                holder.ivMedal.setVisibility(View.VISIBLE);
                holder.ivMedal.setImageResource(medalResourceId(colleague.getRank()));
                break;
            default:
                holder.ivMedal.setVisibility(View.INVISIBLE);
                break;
        }

        OnItemClickListener(holder, position);
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

    public void updateData(List<Colleague> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 设置奖牌
     *
     * @param state
     * @return
     */
    private int medalResourceId(int state) {
        if (state == 1) {
            return R.mipmap.gold_medal;
        } else if (state == 2) {
            return R.mipmap.silver_medal;
        } else if (state == 3) {
            return R.mipmap.bronze_medal;
        } else {
            return 0;
        }
    }

    public static class ColleagueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_colleague)
        MyRoundImageView ivColleague;
        @BindView(R.id.iv_medal)
        ImageView ivMedal;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ColleagueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
