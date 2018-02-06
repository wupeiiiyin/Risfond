package com.risfond.rnss.home.commonFuctions.dynamics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.TimeUtil;
import com.risfond.rnss.entry.Dynamics;
import com.risfond.rnss.widget.expandabletextview.ExpandableTextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 */

public class DynamicsAdapter extends RecyclerView.Adapter<DynamicsAdapter.DynamicsViewHolder> {

    private Context context;
    private List<Dynamics> data;
    private String content;
    private boolean isManager;//是否是管理页面

    public DynamicsAdapter(Context context, List<Dynamics> data, boolean isManager, String content) {
        this.context = context;
        this.data = data;
        this.isManager = isManager;
        this.content = content;
    }

    public DynamicsAdapter(Context context, List<Dynamics> data, boolean isManager) {
        this.context = context;
        this.data = data;
        this.isManager = isManager;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onUpdateClick(int position);

        void onStartClick(int position);

        void onDeleteClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public DynamicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamics, parent, false);
        DynamicsViewHolder holder = new DynamicsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DynamicsViewHolder holder, int position) {
        final Dynamics dynamics = data.get(position);

        holder.tvName.setText(dynamics.getStaffName());
        holder.tvTime.setText(dynamics.getData());
        GlideUtil.downLoadHeadImage(context, data.get(position).getStaffPictureImg(),
                holder.ivUserPhoto, new RoundedCornersTransformation(context, 0, 0));

        SpannableString s = new SpannableString(dynamics.getContent());
        if (content != null && content.length() > 0) {
            for (int i = 0; i < content.split(" ").length; i++) {
                String wordReg = "(?i)" + content.split(" ")[i];//用(?i)来忽略大小写
                Pattern p = Pattern.compile(wordReg);
                Matcher m = p.matcher(s);
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    s.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        holder.tvContent.setText(s, !dynamics.isExpand());
        holder.tvContent.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                dynamics.setExpand(isExpanded);
            }
        });

        if (isManager) {
            holder.llOperate.setVisibility(View.VISIBLE);
            /*if (dynamics.getState().equals("1")) {
                holder.ivPause.setImageResource(R.mipmap.pause);
            } else {
                holder.ivPause.setImageResource(R.mipmap.start);
            }*/
        } else {
            holder.llOperate.setVisibility(View.INVISIBLE);
        }

        OnItemClickListener(holder, position);
    }

    private void OnItemClickListener(DynamicsViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            if (!isManager) {
                holder.tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                });
            }

            holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onUpdateClick(position);
                }
            });

            holder.ivPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onStartClick(position);
                }
            });

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onDeleteClick(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Dynamics> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void updateData(List<Dynamics> data, String content) {
        this.data = data;
        this.content = content;
        notifyDataSetChanged();
    }

    static class DynamicsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        ExpandableTextView tvContent;
        @BindView(R.id.iv_user_photo)
        ImageView ivUserPhoto;
        @BindView(R.id.iv_update)
        ImageView ivUpdate;
        @BindView(R.id.ll_operate)
        LinearLayout llOperate;
        @BindView(R.id.iv_pause)
        ImageView ivPause;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public DynamicsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
