package com.risfond.rnss.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.entry.TimeArrival;
import com.risfond.rnss.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/5/15.
 * 实时到账榜
 */

public class RealTimeArrivalListAdapter extends RecyclerView.Adapter<RealTimeArrivalListAdapter.RealTimeArrivalViewHolder> {

    private Context context;
    private List<TimeArrival> data;

    public RealTimeArrivalListAdapter(Context context, List<TimeArrival> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RealTimeArrivalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_real_time_arrival, parent, false);
        RealTimeArrivalViewHolder holder = new RealTimeArrivalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RealTimeArrivalViewHolder holder, int position) {
        TimeArrival realTimeArrival = data.get(position);

        holder.name.setText(realTimeArrival.getConsultantStaffName());
        holder.position.setText(realTimeArrival.getCompanyName());
        holder.companyName.setText(realTimeArrival.getClientExhibitionName());
        holder.date.setText(realTimeArrival.getRecordTime());
        holder.money.setText(NumberUtil.formatString(realTimeArrival.getAmount()));
        GlideUtil.downLoadHeadImage(context, realTimeArrival.getMiddlePictureUrl(), holder.headPortrait, new CropCircleTransformation(context));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<TimeArrival> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class RealTimeArrivalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.position)
        TextView position;
        @BindView(R.id.company_name)
        TextView companyName;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.head_portrait)
        CircleImageView headPortrait;

        public RealTimeArrivalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
