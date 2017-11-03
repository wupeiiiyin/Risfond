package com.risfond.rnss.home.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.CustomerDetail;
import com.risfond.rnss.entry.CustomerRecord;
import com.risfond.rnss.widget.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/5.
 */

public class CustomerDetailAdapter extends RecyclerView.Adapter implements ExpandableTextView.OnExpandListener {

    private Context context;
    private CustomerDetail data;
    private final int TITLE = 0;
    private final int CONTENT = 1;
    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();

    public CustomerDetailAdapter(Context context, CustomerDetail data) {
        this.context = context;
        this.data = data;
    }

    public interface OnAuthenticationListener {
        void OnAuthenticationClick(View view, int position);
    }

    private OnAuthenticationListener mOnAuthenticationListener;

    public void setOnAuthenticationListener(OnAuthenticationListener mOnAuthenticationListener) {
        this.mOnAuthenticationListener = mOnAuthenticationListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.customer_header, parent, false);
                holder = new HeaderHolder(view);
                break;
            case CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_customer_detail, parent, false);
                holder = new ContentHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TITLE:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.tvName.setText(data.getName());
                headerHolder.tvCode.setText(data.getCode());
                headerHolder.tvIndustry.setText(data.getIndustry());
                headerHolder.tvAddress.setText(data.getAddress());

                headerHolder.ivAuthentication.setVisibility(View.VISIBLE);
                if (data.getCertificationStatus() == 1) {
                    headerHolder.ivAuthentication.setImageResource(R.mipmap.iconyirenzheng);
                } else {
                    headerHolder.ivAuthentication.setImageResource(R.mipmap.icondianjirenzheng);
                    headerHolder.ivAuthentication.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnAuthenticationListener != null) {
                                mOnAuthenticationListener.OnAuthenticationClick(v, position);
                            }
                        }
                    });
                }


                break;
            case CONTENT:
                final ContentHolder contentHolder = (ContentHolder) holder;
                final CustomerRecord record = data.getFHlist().get(position - 1);
                StringBuilder sb = new StringBuilder(record.getHFDate());
                if (sb.length() > 11) {
                    sb.insert(11, " ");
                }
                contentHolder.tvTime.setText(sb);
                contentHolder.tvName.setText(record.getHFSafffName());

                contentHolder.tvContent.setTag(position - 1);
                contentHolder.tvContent.setExpandListener(this);
                Integer state = mPositionsAndStates.get(position - 1);
                //第一次getview时肯定为etvWidth为0
                contentHolder.tvContent.updateForRecyclerView(record.getHFContent(), etvWidth, state == null ? 0 : state);

                if (position - 1 == data.getFHlist().size() - 1) {
                    contentHolder.tvLine.setVisibility(View.INVISIBLE);
                } else {
                    contentHolder.tvLine.setVisibility(View.VISIBLE);
                }
                if (etvWidth == 0) {
                    contentHolder.tvContent.post(new Runnable() {
                        @Override
                        public void run() {
                            etvWidth = contentHolder.tvContent.getWidth();
                        }
                    });
                }

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.getFHlist() != null) {
            return data.getFHlist().size() > 0 ? data.getFHlist().size() + 1 : 1;
        } else {
            return 0;
        }
    }

    public void updateData(CustomerDetail data) {
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

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_authentication)
        ImageView ivAuthentication;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
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
