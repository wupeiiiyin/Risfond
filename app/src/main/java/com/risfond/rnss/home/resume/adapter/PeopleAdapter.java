package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.Province;
import com.risfond.rnss.entry.RecommendPeople;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/8/10.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ProvinceViewHolder> {

    private Context context;
    private List<RecommendPeople> data;

    public PeopleAdapter(Context context, List<RecommendPeople> data) {
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
    public ProvinceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProvinceViewHolder holder = new ProvinceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_people, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ProvinceViewHolder holder, int position) {
        RecommendPeople people = data.get(position);

        holder.cbPeople.setChecked(people.isChecked());
        holder.tvPosition.setText(people.getJobName());
        holder.tvCompany.setText(people.getJobCompany());
        holder.tvNumber.setText("#" + people.getJobNum());
        holder.tvTime.setText(people.getDateTime());

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
        return data != null ? data.size() : 0;
    }

    public void update(List<RecommendPeople> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ProvinceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_people)
        CheckBox cbPeople;
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        public ProvinceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
