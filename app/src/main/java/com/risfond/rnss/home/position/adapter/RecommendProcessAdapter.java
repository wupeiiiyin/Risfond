package com.risfond.rnss.home.position.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.ScreenUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.RecommendProcess;
import com.risfond.rnss.entry.RecommendProcessMessage;
import com.risfond.rnss.widget.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐流程
 * Created by Abbott on 2017/7/14.
 */

public class RecommendProcessAdapter extends RecyclerView.Adapter<RecommendProcessAdapter.RecommendProcessViewHolder> implements ExpandableTextView.OnExpandListener {

    private Context context;
    private List<RecommendProcess> data;
    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();
    private LinearLayout.LayoutParams lp;
    private LinearLayout.LayoutParams lp2;
    private LinearLayout.LayoutParams lp3;

    public RecommendProcessAdapter(Context context, List<RecommendProcess> data) {
        this.context = context;
        this.data = data;
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, ScreenUtil.dip2px(context, 80));
        lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0, 0, 0, 0);
    }

    @Override
    public RecommendProcessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_process_title, parent, false);
        RecommendProcessViewHolder holder = new RecommendProcessViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendProcessViewHolder holder, int position) {
        holder.tvTitle.setText(data.get(position).getStatusText());
        holder.ivState.setImageResource(status(data.get(position).getStatus()));
        if (data.size() == 1) {
            holder.ivTopLine.setVisibility(View.INVISIBLE);
            holder.ivBottomLine.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
            holder.ivTopLine.setVisibility(View.INVISIBLE);
            holder.ivBottomLine.setVisibility(View.VISIBLE);
        } else if (position == data.size() - 1) {
            holder.llContent.setLayoutParams(lp);
            holder.ivTopLine.setVisibility(View.VISIBLE);
            holder.ivBottomLine.setVisibility(View.INVISIBLE);
        } else {
            holder.llContent.setLayoutParams(lp2);
            holder.ivTopLine.setVisibility(View.VISIBLE);
            holder.ivBottomLine.setVisibility(View.VISIBLE);
        }
        for (RecommendProcessMessage message : data.get(position).getList()) {
            View item = LayoutInflater.from(context).inflate(R.layout.item_recommend_process_content, null);
            final ExpandableTextView tvContent = ((ExpandableTextView) item.findViewById(R.id.tv_content));
            ((TextView) item.findViewById(R.id.tv_status)).setText(message.getCheckedStatus());
            ((TextView) item.findViewById(R.id.tv_time)).setText(message.getInComeTime());
            ((TextView) item.findViewById(R.id.tv_name)).setText(message.getStaffName());
            //第一次getview时肯定为etvWidth为0
            tvContent.setTag(position);
            tvContent.setExpandListener(this);
            Integer state = mPositionsAndStates.get(position);
            tvContent.updateForRecyclerView(message.getContent(), etvWidth, state == null ? 0 : state);
            if (etvWidth == 0) {
                tvContent.post(new Runnable() {
                    @Override
                    public void run() {
                        etvWidth = tvContent.getWidth();
                    }
                });
            }
            holder.llContent.addView(item);
        }
    }


    private int status(int status) {
        int resId = 0;
        switch (status) {
            case 1:
                resId = R.mipmap.circle1;
                break;
            case 2:
            case 4:
                resId = R.mipmap.circle2;
                break;
            case 11:
                resId = R.mipmap.circle6;
                break;
            case 6:
            case 10:
                resId = R.mipmap.circle3;
                break;
            case 3:
            case 5:
            case 7:
                resId = R.mipmap.circle5;
                break;
            case 8:
            case 9:
                resId = R.mipmap.circle4;
                break;
            case 12:
                resId = R.mipmap.circle7;
                break;
            default:
                break;
        }
        return resId;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<RecommendProcess> data) {
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

    public static class RecommendProcessViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_top_line)
        ImageView ivTopLine;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.iv_bottom_line)
        ImageView ivBottomLine;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        public RecommendProcessViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
