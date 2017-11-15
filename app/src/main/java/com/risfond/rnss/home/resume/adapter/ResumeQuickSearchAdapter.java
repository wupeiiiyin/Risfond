package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.AppSelectQuery;
import com.risfond.rnss.entry.ResumeSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/11/8.快捷搜索适配器
 */

public class ResumeQuickSearchAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<AppSelectQuery> data;

    public ResumeQuickSearchAdapter(Context context, List<AppSelectQuery> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnDeClickListener {//add
        void onDeClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnDeClickListener mOnDeClickListener;//add

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setOnDeClickListener(OnDeClickListener mOnDeClickListener) {//add
        this.mOnDeClickListener =  mOnDeClickListener;
    }

    @Override
    public ResumeQuickSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resume_quick_list, parent, false);
        ResumeQuickSearchViewHolder holder = new ResumeQuickSearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ResumeQuickSearchViewHolder) {
            ResumeQuickSearchViewHolder mholder = (ResumeQuickSearchViewHolder) holder;

            mholder.tvQuick.setText(data.get(position).getName()+data.get(position).getJobTitle());

//            mholder.imageDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    DialogUtil.getInstance().showConfigDialog(context, "是否删除？", "是", "否", new DialogUtil.PressCallBack() {
//                        @Override
//                        public void onPressButton(int buttonIndex) {
//                            if (buttonIndex == DialogUtil.BUTTON_OK) {
//
//                                data.remove(data.get(position));
//                                notifyDataSetChanged();
//                            }
//                        }
//                    });
//
//                }
//            });
            if (mOnDeClickListener != null) {//删除回调接口，供activity调用
                ((ResumeQuickSearchAdapter.ResumeQuickSearchViewHolder) holder).imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnDeClickListener.onDeClick(holder.itemView, position);
                    }
                });
            }

//            mholder.tvExperience.setText(search.getWorkExperience() + "年经验");
//            mholder.tvResumeNumber.setText(search.getResumeCode());
//            mholder.tvUpdateTime.setText(search.getUpdateDate());

            OnItemClickListener(holder, position);
//            OnClickListener(holder, position);
        }
    }

//    private void OnClickListener(RecyclerView.ViewHolder holder, final int position) {//add
//        if (mOnClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(v, position);
//                }
//            });
//        }
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<AppSelectQuery> data) {
        this.data = data;
        notifyDataSetChanged();
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

    public static class ResumeQuickSearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_quick)
        TextView tvQuick;//主要内容
        @BindView(R.id.tv_quick_time)
        TextView tvTime;//时间
        @BindView(R.id.image_quick_search_deletes)
        ImageView imageDelete;//删除图片按钮

        public ResumeQuickSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
