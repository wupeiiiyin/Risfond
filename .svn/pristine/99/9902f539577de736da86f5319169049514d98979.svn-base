package com.risfond.rnss.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyCustomerCompany;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 */

public class MyCustomerCompanyAdapter extends RecyclerView.Adapter<MyCustomerCompanyAdapter.MyCustomerViewHolder> {

    private Context context;
    private List<MyCustomerCompany> data;


    public MyCustomerCompanyAdapter(Context context, List<MyCustomerCompany> data) {
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
    public MyCustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_company, parent, false);
        MyCustomerViewHolder holder = new MyCustomerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCustomerViewHolder holder, int position) {
        holder.typeCompany.setText(data.get(position).getHrCompanyName());
        holder.typeCompanyCount.setText(data.get(position).getList().size() + "");
        OnItemClickListener(holder, position);
    }

    private void OnItemClickListener(MyCustomerViewHolder holder, final int position) {
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

    public void updateData(List<MyCustomerCompany> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class MyCustomerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_company)
        TextView typeCompany;
        @BindView(R.id.type_company_count)
        TextView typeCompanyCount;

        public MyCustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
