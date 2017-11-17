package com.risfond.rnss.home.resume.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.risfond.rnss.entry.AppDeleteQuery;
import com.risfond.rnss.entry.AppSelectQuery;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchAddResponse;
import com.risfond.rnss.entry.ResumeSearchAll;
import com.risfond.rnss.entry.ResumeSearchHight;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Abbott on 2017/11/8.快捷搜索适配器
 */

public class ResumeQuickSearchAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<ResumeSearchHight> data;
    private List<ResumeSearch> searchdata;

    public ResumeQuickSearchAdapter(Context context, List<ResumeSearchHight> data) {
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
        SharedPreferences king = context.getSharedPreferences("KING", context.MODE_PRIVATE);
        String etresumeSearch = king.getString("etresumeSearch", null);

        if (holder instanceof ResumeQuickSearchViewHolder) {
            ResumeQuickSearchViewHolder mholder = (ResumeQuickSearchViewHolder) holder;
//            AppSelectQuery search = data.get(position);
//            GlideUtil.loadResumeImage(context, search.getPhoto(), mholder.ivHead, new CropCircleTransformation(context));

            List<ResumeSearchHight> o =new ArrayList<>();
            ResumeSearchHight h = data.get(position);
//            ResumeSearch resumeSearch = searchdata.get(position);
            ResumeSearchSelectResponse selectResponse = new ResumeSearchSelectResponse();
            //                mholder.tvQuick.setText(search.getJobTitle() + "+"+search.getCompanyFullName() +"+"+
//                                search.getLiveLocationTxt() +"+"+ search.getEducationLevelTxt() + search.getAge() + "岁+" +
//                                search.getWorkExperience() +"+"+ "年经验+" + search.getResumeCode() +"+"+ search.getName()+"滑动就是的海景房打不开返回键使和防水砂浆的vjds111");
//            List<String> gender = h.getGender();
//            String s = gender.get(0);
//            if(s.equals(1)){
//                Log.i("TAGf",s+"--s1------");
//            }else{
//                Log.i("TAGf",s+"--s2------");
//            }
            mholder.tvQuick.setText(h.getYearfrom()+"-"+h.getYearto()+"年经验+"+h.getAgefrom()+"-"+h.getAgeto()+"岁");

//            Log.i("TAGf",gender+"--gender------");
            mholder.tvTime.setText(selectResponse.getCreatTime());//时间

            if (mOnDeClickListener != null) {//删除回调接口，供activity调用
                ((ResumeQuickSearchViewHolder)holder).imageDelete.setOnClickListener(new View.OnClickListener() {
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
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<ResumeSearchHight> data) {
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
