package com.risfond.rnss.home.call.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.home.call.widget.Area;

import java.util.List;



/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.ViewHolder> {
    protected Context mContext;
    protected List<Area> mDatas;
    protected LayoutInflater mInflater;

    public AreaListAdapter(Context mContext, List<Area> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public List<Area> getDatas() {
        return mDatas;
    }

    public AreaListAdapter setDatas(List<Area> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public AreaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_area_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final AreaListAdapter.ViewHolder holder, final int position) {
        final Area cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getAreacode());
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
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tv_area);
        }
    }
}
