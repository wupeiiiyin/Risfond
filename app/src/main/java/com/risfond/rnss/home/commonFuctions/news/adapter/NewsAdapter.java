package com.risfond.rnss.home.commonFuctions.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.News;
import com.risfond.rnss.widget.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.risfond.rnss.R.mipmap.course_default;

/**
 * Created by Abbott on 2017/5/15.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<News> data;
    private int etvWidth;

    public NewsAdapter(Context context, List<News> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final News news = data.get(position);

        /*holder.tvTime.setText(news.getCreatedTime());
        holder.tvTitle.setText(news.getTitle());
        holder.tvType.setText(news.getCategory());

        //第一次getview时肯定为etvWidth为0
        holder.tvContent.updateForRecyclerView(news.getContent().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""), etvWidth);
        if (etvWidth == 0) {
            holder.tvContent.post(new Runnable() {
                @Override
                public void run() {
                    etvWidth = holder.tvContent.getWidth();
                }
            });
        }*/
        OnItemClickListener(holder, position);

        holder.mNewsTitle.setText(news.getTitle());
        holder.mNewsTypeAndTime.setText(news.getCategory() + "  " + news.getCreatedTime());
        Glide.with(context.getApplicationContext())
                .load(news.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.course_default)
                .dontAnimate()
                .into(holder.mNewsIcon);
    }

    private void OnItemClickListener(final NewsViewHolder holder, final int position) {
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

    public void updateData(List<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        ExpandableTextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.id_news_title)
        TextView mNewsTitle;
        @BindView(R.id.id_news_type_and_time)
        TextView mNewsTypeAndTime;
        @BindView(R.id.id_news_icon)
        ImageView mNewsIcon;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
