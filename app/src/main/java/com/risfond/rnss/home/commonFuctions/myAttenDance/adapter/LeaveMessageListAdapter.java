package com.risfond.rnss.home.commonFuctions.myAttenDance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/8/14.
 * 我的请假申请
 */

public class LeaveMessageListAdapter extends RecyclerView.Adapter<LeaveMessageListAdapter.ProjectViewHolder> {

    private Context context;
    private List<MyAttendance> data;
    private List<String> messageId = new ArrayList<>();

    public LeaveMessageListAdapter(Context context, List<MyAttendance> data) {
        this.context = context;
        this.data = data;
        messageId.clear();
        for (int i = 0; i < data.size();i++){
            if(data.get(i).getStatus()){
                messageId.add(data.get(i).getId()+"");
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,List<String> data);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(LeaveMessageListAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public LeaveMessageListAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leave_message_picture_item, parent, false);
        LeaveMessageListAdapter.ProjectViewHolder holder = new LeaveMessageListAdapter.ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LeaveMessageListAdapter.ProjectViewHolder holder, final int position) {
        MyAttendance colleague = data.get(position);
        GlideUtil.colleagueImage(context, colleague.getStaffPhoto(), holder.ivMedal);
        holder.tvName.setText(colleague.getName());
        if (colleague.getStatus()) {
            holder.ivState.setImageResource(R.mipmap.eright1);
        } else {
            holder.ivState.setImageResource(R.mipmap.eright);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getStatus()){
                    data.get(position).setStatus(false);
                    holder.ivState.setImageResource(R.mipmap.eright);
                    messageId.remove(data.get(position).getId()+"");
                }else {
                    data.get(position).setStatus(true);
                    holder.ivState.setImageResource(R.mipmap.eright1);
                    if(!messageId.contains(data.get(position).getId()+"")){
                        messageId.add(data.get(position).getId()+"");
                    }
                }
                mOnItemClickListener.onItemClick(v, position,messageId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<MyAttendance> data) {
        this.data = data;
        notifyDataSetChanged();
        messageId.clear();
        for (int i = 0; i < data.size();i++){
            if(data.get(i).getStatus()){
                messageId.add(data.get(i).getId()+"");
            }
        }
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        CircleImageView ivMedal;
        @BindView(R.id.title)
        TextView tvName;
        @BindView(R.id.iv_seleted_state)
        ImageView ivState;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
