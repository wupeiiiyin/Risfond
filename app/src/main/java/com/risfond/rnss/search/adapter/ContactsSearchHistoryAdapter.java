package com.risfond.rnss.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/3/27.
 */

public class ContactsSearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> histories;
    //item类型
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private int mFooterCount = 1;//底部View个数
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnDeleteClickListener mDeleteClickListener;


    public ContactsSearchHistoryAdapter(Context context, List<String> histories) {
        this.histories = histories;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //内容长度
    public int getContentItemCount() {
        return histories.size();
    }

    //判断当前item是否是FooterView
    public boolean isFooterView(int position) {
        return mFooterCount != 0 && position >= getContentItemCount();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mDeleteClickListener = mOnDeleteClickListener;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mFooterCount != 0 && position >= dataItemCount) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (histories.size() > 0) {
            if (viewType == ITEM_TYPE_CONTENT) {
                return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_resume_search_history, parent, false));
            } else if (viewType == ITEM_TYPE_BOTTOM) {
                return new BottomViewHolder(mLayoutInflater.inflate(R.layout.item_bottom_history_clean, parent, false));
            }
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).historyName.setText(histories.get(position));

            if (mDeleteClickListener != null) {
                ((ContentViewHolder) holder).ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDeleteClickListener.onDeleteClick(holder.itemView, position);
                    }
                });
            }
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (histories.size() > 0) {
            count = getContentItemCount() + mFooterCount;
        }
        return count;
    }


    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.history_name)
        TextView historyName;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void updateHistory(List<String> data) {
        this.histories = data;
        notifyDataSetChanged();
    }

}
