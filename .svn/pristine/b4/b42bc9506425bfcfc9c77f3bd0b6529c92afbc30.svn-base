package com.risfond.rnss.home.commonFuctions.myCourse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhong.ratingbar.RatingBar;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/5/15.
 * 课程培训
 */

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyCourseViewHolder> {

    private Context context;
    private List<MyCourse> data;
    private Map<String, Integer> res = new HashMap<>();

    public MyCourseAdapter(Context context, List<MyCourse> data) {
        this.context = context;
        this.data = data;
        createRes();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_course, parent, false);
        MyCourseViewHolder holder = new MyCourseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCourseViewHolder holder, int position) {
        MyCourse myCourse = data.get(position);
        GlideUtil.downLoadPhoto(context, myCourse.getCourseBanner(), holder.ivPhoto, new RoundedCornersTransformation(context, 0, 0));
        holder.tvName.setText(myCourse.getTitle());
        holder.ivPosition.setImageResource(res.get(myCourse.getTrainingObjectName()));
        holder.ratingBarScore.setStar(myCourse.getCourseStar());
        holder.tvTeacher.setText(myCourse.getTeacher());
        holder.tvBrowse.setText(myCourse.getShowNum()+"次");
        holder.tvTime.setText(myCourse.getCreatedTime());
        OnItemClickListener(holder, position);
    }

    private void createRes() {
        res.put("分公司总", R.mipmap.dlab1);
        res.put("合伙人", R.mipmap.dlab2);
        res.put("猎头顾问", R.mipmap.dlab3);
        res.put("猎头助理", R.mipmap.dlab4);
        res.put("人力资源", R.mipmap.dlab5);
        res.put("资深顾问", R.mipmap.dlab6);
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

    public void updateData(List<MyCourse> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class MyCourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_position)
        ImageView ivPosition;
        @BindView(R.id.rating_bar_score)
        RatingBar ratingBarScore;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.tv_browse)
        TextView tvBrowse;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
