package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.City;

import java.util.List;

/**
 * Created by Abbott on 2017/8/10.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private Context context;
    private List<City> data;
    private List<String> selected;

    public CityAdapter(Context context, List<City> data, List<String> selected) {
        this.context = context;
        this.data = data;
        this.selected = selected;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityViewHolder holder = new CityViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.tv_city.setText(data.get(position).getName());
        if (selected.contains(data.get(position).getId())) {
            holder.cb_city.setChecked(true);
        } else {
            holder.cb_city.setChecked(false);
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

    public void update(List<City> data, List<String> selected) {
        this.data = data;
        this.selected = selected;
        notifyDataSetChanged();

    }

    public void update(List<City> data, List<String> selected, int position, String provinceId) {
        this.data = data;
        this.selected = selected;
        notifyDataSetChanged();
        if (!provinceId.equals("99999")) {
            change(position);
        }
    }

    private void change(int position) {
        for (int i = 0; i < data.size(); i++) {
            if (selected.contains(data.get(i).getId())) {
                if (i == 0 && position == 0) {
                    for (int j = 1; j < data.size(); j++) {
                        selected.remove(data.get(j).getId());
                        data.get(j).setChecked(false);
                    }
                    break;
                } else {
                    data.get(0).setChecked(false);
                    selected.remove(data.get(0).getId());
                }
            }
        }
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tv_city;
        CheckBox cb_city;

        public CityViewHolder(View itemView) {
            super(itemView);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            cb_city = (CheckBox) itemView.findViewById(R.id.cb_city);
        }
    }
}
