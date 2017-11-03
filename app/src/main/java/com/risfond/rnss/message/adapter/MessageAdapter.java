package com.risfond.rnss.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.Messages;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Abbott on 2017/3/27.
 */

public class MessageAdapter extends SwipeMenuAdapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<Messages> data;

    public MessageAdapter(Context context, List<Messages> data) {
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
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
    }

    @Override
    public MessageViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new MessageViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        Messages message = data.get(position);
        GlideUtil.downLoadHeadImage(context, message.getNickUrl(), holder.ivMessagePhoto, new RoundedCornersTransformation(context, 0, 0));
        holder.tvMessageTitle.setText(message.getNickName());
        if (message.getChatType() == EaseConstant.CHATTYPE_SINGLE) {
            holder.tvMessageContent.setText(message.getContent());
        } else if (message.getChatType() == EaseConstant.CHATTYPE_GROUP) {
            holder.tvMessageContent.setText(message.getGroupFrom() + message.getContent());
        }
        holder.tvMessageTime.setText(message.getTime());

        unReadCount(holder, message.getTip());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    /**
     * 未读消息提示
     *
     * @param holder
     * @param unRead
     */
    private void unReadCount(MessageViewHolder holder, int unRead) {
        if (unRead > 0) {
            if (unRead < 100) {
                holder.tvMessageTip.setText(String.valueOf(unRead));
            } else {
                holder.tvMessageTip.setText("...");
            }
            holder.tvMessageTip.setVisibility(View.VISIBLE);
        } else {
            holder.tvMessageTip.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateMessage(List<Messages> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_message_photo)
        ImageView ivMessagePhoto;
        @BindView(R.id.tv_message_title)
        TextView tvMessageTitle;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;
        @BindView(R.id.tv_message_tip)
        TextView tvMessageTip;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
