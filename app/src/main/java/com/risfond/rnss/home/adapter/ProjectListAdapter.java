package com.risfond.rnss.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.HomeProjectInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/25.
 * 首页项目
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private Context context;
    private List<HomeProjectInfo> data;

    public ProjectListAdapter(Context context, List<HomeProjectInfo> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ProjectListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ProjectListAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ProjectListAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_picture_item, parent, false);
        ProjectListAdapter.ProjectViewHolder holder = new ProjectListAdapter.ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProjectListAdapter.ProjectViewHolder holder, int position) {
        HomeProjectInfo colleague = data.get(position);
        holder.ivMedal.setImageResource(colleague.getImgId());
        holder.tvName.setText(colleague.getTitle());
        if (colleague.getNumber() < 1) {
            holder.tvUnreadNumber.setVisibility(View.INVISIBLE);
        } else {
            holder.tvUnreadNumber.setVisibility(View.VISIBLE);
            if (colleague.getNumber() > 99) {
                holder.tvUnreadNumber.setText("...");
            } else {
                holder.tvUnreadNumber.setText(String.valueOf(colleague.getNumber()));
            }
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
        return data.size();
    }

    public void updateData(List<HomeProjectInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView ivMedal;
        @BindView(R.id.title)
        TextView tvName;
        @BindView(R.id.tv_unread_number)
        TextView tvUnreadNumber;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
