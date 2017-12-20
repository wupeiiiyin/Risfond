package com.risfond.rnss.home.commonFuctions.publicCustomer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/5/15.
 * 简历搜索
 */

public class ClientApplationAdapter extends RecyclerView.Adapter {

    private Context context;
    private String[] data;
    private int seleted = -1;//选中条目id;

    private final int CONTENT = 0;
    private final int FEET = 1;

    public ClientApplationAdapter(Context context, String[] data) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_client_applation, parent, false);
                holder = new ClientViewHolder(view);
                break;
            case FEET:
                view = LayoutInflater.from(context).inflate(R.layout.item_client_applation_feet, parent, false);
                holder = new FeetHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case CONTENT:
                if (holder instanceof ClientViewHolder) {
                    final ClientViewHolder mholder = (ClientViewHolder) holder;
                    //        强行关闭复用
                    mholder.setIsRecyclable(false);
                    mholder.mText.setText(data[position]);
                    if(position == seleted){
                        mholder.mImg.setImageResource(R.mipmap.giconright1);
                        mholder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_little_grey));
                    }else {
                        mholder.mImg.setImageResource(R.mipmap.giconright);
                        mholder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            seleted = position;
                            mholder.mImg.setImageResource(R.mipmap.giconright1);
                            mOnItemClickListener.onItemClick(v, position);
                            notifyDataSetChanged();
                        }
                    });
                }
                break;
            case FEET:
                if (holder instanceof FeetHolder) {
                    FeetHolder mholder = (FeetHolder) holder;
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(data != null && position != data.length){
            return CONTENT;
        }else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public void updateData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client_applation_img)
        ImageView mImg;
        @BindView(R.id.client_applation_text)
        TextView mText;

        public ClientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FeetHolder extends RecyclerView.ViewHolder {

        public FeetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
