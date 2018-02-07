package com.risfond.rnss.home.position.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.PositionSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 *
 */

public class PositionSearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PositionSearch> data;

    public PositionSearchAdapter(Context context, List<PositionSearch> data) {
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
    public PositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_position, parent, false);
        PositionViewHolder holder = new PositionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PositionViewHolder) {
            PositionViewHolder mholder = (PositionViewHolder) holder;
            PositionSearch positionSearch = data.get(position);
            mholder.tvName.setText(positionSearch.getTitle());
            mholder.tvTime.setText(positionSearch.getLastCommunicationTime());
            mholder.tvSalary.setText(positionSearch.getSalary());
            mholder.ivState.setImageResource(stateResource(Integer.parseInt(positionSearch.getStatus())));
            mholder.tvLocation.setText(positionSearch.getLocations());
            mholder.tvPeriod.setText(String.valueOf(positionSearch.getRunDay()));
            mholder.tvNumber.setText(positionSearch.getCode());
            mholder.tvCompany.setText(positionSearch.getClientName());
            OnItemClickListener(holder, position);
        }

    }

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 1:
                id = R.mipmap.job_state1;
                break;
            case 2:
                id = R.mipmap.job_state2;
                break;
            case 3:
                id = R.mipmap.job_state3;
                break;
            case 4:
                id = R.mipmap.job_state4;
                break;
            default:
                id = R.mipmap.job_state2;
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

    public void updateData(List<PositionSearch> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class PositionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_salary)
        TextView tvSalary;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_company)
        TextView tvCompany;

        public PositionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
