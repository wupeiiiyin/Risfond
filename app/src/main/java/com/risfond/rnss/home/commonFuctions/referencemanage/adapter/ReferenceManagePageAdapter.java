package com.risfond.rnss.home.commonFuctions.referencemanage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.ReferenceItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vicky on 2017/7/26.
 */

public class ReferenceManagePageAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<ReferenceItemInfo> data;

    public ReferenceManagePageAdapter(Context context, List<ReferenceItemInfo> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ReferenceManagePageAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ReferenceManagePageAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ReferenceManagePageAdapter.ReferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reference_manage_page, parent, false);
        ReferenceManagePageAdapter.ReferenceViewHolder holder = new ReferenceManagePageAdapter.ReferenceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReferenceManagePageAdapter.ReferenceViewHolder) {
            ReferenceManagePageAdapter.ReferenceViewHolder mholder = (ReferenceManagePageAdapter.ReferenceViewHolder) holder;
            ReferenceItemInfo positionSearch = data.get(position);
            mholder.tvName.setText(positionSearch.getStaffName()+"");//顾问（推荐者的姓名）
            mholder.tvRecommendName.setText(positionSearch.getName()+"");//被推荐人的姓名
            mholder.tvTime.setText(positionSearch.getUpdatetime()+"");
            mholder.tvSalary.setText(positionSearch.getSalary()+"万/年");
            mholder.ivState.setImageResource(stateResource(Integer.parseInt(positionSearch.getStatus())));
            mholder.tvLocationCallNum.setText("电话：" + positionSearch.getMobile()+"");
            mholder.tvPeriod.setText("回款：" + String.valueOf(positionSearch.getAmount()+""));
            mholder.tvNumber.setText(positionSearch.getTalktotal()+"");
            mholder.tvCompany.setText(positionSearch.getCompanyname()+"");
            mholder.tvCompanyosition.setText(positionSearch.getJobtitle()+"");
            OnItemClickListener(holder, position);
        }

    }

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 1:
                id = R.mipmap.tag1;
                break;
            case 2:
                id = R.mipmap.tag2;
                break;
            case 3:
                id = R.mipmap.tag12;
                break;
            case 4:
                id = R.mipmap.tag7;
                break;
            case 5:
                id = R.mipmap.tag10;
                break;
            case 6:
                id = R.mipmap.tag4;
                break;
            case 7:
                id = R.mipmap.tag11;
                break;
            case 8:
                id = R.mipmap.tag5;
                break;
            case 9:
                id = R.mipmap.tag6;
                break;
            case 10:
                id = R.mipmap.tag3;
                break;
            case 11:
                id = R.mipmap.tag8;
                break;
            case 12:
                id = R.mipmap.tag9;
                break;
            default:
                id = R.mipmap.tag1;
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

    public void updateData(List<ReferenceItemInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ReferenceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_recommend_name)
        TextView tvRecommendName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_salary)
        TextView tvSalary;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_location)
        TextView tvLocationCallNum;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_company_position)
        TextView tvCompanyosition;

        public ReferenceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
