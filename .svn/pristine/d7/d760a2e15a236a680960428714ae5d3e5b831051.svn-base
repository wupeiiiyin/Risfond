package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.Province;

import java.util.List;

/**
 * Created by Abbott on 2017/8/10.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {
    private Context context;
    private List<Province> data;

    public ProvinceAdapter(Context context, List<Province> data) {
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
        ProvinceViewHolder holder = new ProvinceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_provinces, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ProvinceViewHolder holder, int position) {
        holder.province.setText(data.get(position).getName());
        if (data.get(position).isSelect()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_home_back));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        if (data.get(position).isTip()) {
            holder.vTip.setVisibility(View.VISIBLE);
        } else {
            holder.vTip.setVisibility(View.INVISIBLE);
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
        return data != null ? data.size() : 0;
    }

    public void update(List<Province> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ProvinceViewHolder extends RecyclerView.ViewHolder {
        TextView province;
        View vTip;

        public ProvinceViewHolder(View itemView) {
            super(itemView);
            province = (TextView) itemView.findViewById(R.id.province);
            vTip = itemView.findViewById(R.id.v_tip);
        }
    }
}
