package com.risfond.rnss.home.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.ScreenUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.CustomerRecord;
import com.risfond.rnss.widget.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/5.
 */

public class CustomerDetailAdapter2 extends RecyclerView.Adapter implements ExpandableTextView.OnExpandListener {

    private Context context;
    private List<CustomerRecord> data;
    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();
    private LinearLayout.LayoutParams lp;
    private LinearLayout.LayoutParams lp2;

    public CustomerDetailAdapter2(Context context, List<CustomerRecord> data) {
        this.context = context;
        this.data = data;
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, ScreenUtil.dip2px(context, 80));
        lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, 0, 0, 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_customer_detail, parent, false);
        holder = new ContentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ContentHolder contentHolder = (ContentHolder) holder;
        CustomerRecord record = data.get(position);
        StringBuilder sb = new StringBuilder(record.getHFDate());
        if (sb.length() > 11) {
            sb.insert(11, " ");
        }
        contentHolder.tvTime.setText(sb);
        contentHolder.tvName.setText(record.getHFSafffName());

        contentHolder.tvContent.setTag(position);
        contentHolder.tvContent.setExpandListener(this);
        Integer state = mPositionsAndStates.get(position);
        //第一次getview时肯定为etvWidth为0
        contentHolder.tvContent.updateForRecyclerView(record.getHFContent(), etvWidth, state == null ? 0 : state);

        if (position == data.size() - 1) {
            contentHolder.tvLine.setVisibility(View.INVISIBLE);
            contentHolder.llTop.setLayoutParams(lp);
        } else {
            contentHolder.tvLine.setVisibility(View.VISIBLE);
            contentHolder.llTop.setLayoutParams(lp2);
        }
        if (etvWidth == 0) {
            contentHolder.tvContent.post(new Runnable() {
                @Override
                public void run() {
                    etvWidth = contentHolder.tvContent.getWidth();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void updateData(List<CustomerRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onExpand(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    @Override
    public void onShrink(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_top)
        LinearLayout llTop;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_line)
        TextView tvLine;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        ExpandableTextView tvContent;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
