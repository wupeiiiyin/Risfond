/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.risfond.rnss.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.ImageUtils;
import com.risfond.rnss.R;
import com.risfond.rnss.common.utils.EaseImageCache;
import com.risfond.rnss.common.utils.EaseImageUtils;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.image.CircleTransformation;
import com.risfond.rnss.common.utils.image.ImageUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final int TYPE_SEND_TXT = 0;//发送文本消息
    private final int TYPE_RECEIVE_TXT = 1;//接收文本消息
    private final int TYPE_SEND_VOICE = 2;//发送语音消息
    private final int TYPE_RECEIVE_VOICE = 3;//接收语音消息
    private final int TYPE_SEND_IMAGE = 5;//发送文件消息
    private final int TYPE_RECEIVE_IMAGE = 6;//接收文件消息

    private int mMinItemWith;// 设置语音对话框的最大宽度
    private int mMaxItemWith;// 设置语音对话框的最小宽度
    private List<EMMessage> data;

    public ChatAdapter(Context context, List<EMMessage> data) {
        this.context = context;
        this.data = data;
        // 获取系统宽度
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.3f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
    }

    public interface OnReSendClickListener {
        void onReSendClick(View view, int position);
    }

    public interface OnTextClickListener {
        void onTextClick(View view, int position);
    }

    public interface OnVoiceClickListener {
        void onVoiceClick(View view, int position);
    }

    public interface OnUserClickListener {
        void onUserClick(View view, int position);
    }

    private OnReSendClickListener mOnReSendClickListener;
    private OnTextClickListener mOnTextClickListener;
    private OnUserClickListener mOnUserClickListener;
    private OnVoiceClickListener mOnVoiceClickListener;

    public void setOnReSendClickListener(OnReSendClickListener mOnReSendClickListener) {
        this.mOnReSendClickListener = mOnReSendClickListener;
    }

    public void setOnTextClickListener(OnTextClickListener mOnTextClickListener) {
        this.mOnTextClickListener = mOnTextClickListener;
    }

    public void setOnUserClickListener(OnUserClickListener mOnUserClickListener) {
        this.mOnUserClickListener = mOnUserClickListener;
    }

    public void setOnVoiceClickListener(OnVoiceClickListener mOnVoiceClickListener) {
        this.mOnVoiceClickListener = mOnVoiceClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = data.get(position);
        if (emMessage.getFrom().equals(SPUtil.loadEaseMobAccount(context))) {
            if (emMessage.getType() == EMMessage.Type.TXT) {
                return TYPE_SEND_TXT;
            } else if (emMessage.getType() == EMMessage.Type.VOICE) {
                return TYPE_SEND_VOICE;
            } else {
                return TYPE_SEND_IMAGE;
            }

        } else {
            if (emMessage.getType() == EMMessage.Type.TXT) {
                return TYPE_RECEIVE_TXT;
            } else if (emMessage.getType() == EMMessage.Type.VOICE) {
                return TYPE_RECEIVE_VOICE;
            } else {
                return TYPE_RECEIVE_IMAGE;
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(parent, viewType);
        return holder;
    }

    private RecyclerView.ViewHolder getViewHolderByViewType(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View sendTxtView = LayoutInflater.from(context).inflate(R.layout.item_chat_send_text, parent, false);
        View receiveTxtView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_text, parent, false);
        View sendVoiceView = LayoutInflater.from(context).inflate(R.layout.item_chat_send_voice, parent, false);
        View receiveVoiceView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_voice, parent, false);
        View sendImageView = LayoutInflater.from(context).inflate(R.layout.item_chat_send_image, parent, false);
        View receiveImageView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_image, parent, false);

        switch (viewType) {
            case TYPE_SEND_TXT:
                holder = new SendTxtViewHolder(sendTxtView);
                break;
            case TYPE_RECEIVE_TXT:
                holder = new ReceiveTxtViewHolder(receiveTxtView);
                break;
            case TYPE_SEND_VOICE:
                holder = new SendVoiceViewHolder(sendVoiceView);
                break;
            case TYPE_RECEIVE_VOICE:
                holder = new ReceiveVoiceViewHolder(receiveVoiceView);
                break;
            case TYPE_SEND_IMAGE:
                holder = new SendImageViewHolder(sendImageView);
                break;
            case TYPE_RECEIVE_IMAGE:
                holder = new ReceiveImageViewHolder(receiveImageView);
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EMMessage emMessage = data.get(position);
        switch (getItemViewType(position)) {
            case TYPE_SEND_TXT:
                bindSendTxtMessage(holder, emMessage);
                bindSendTxtClickListener(holder, position);
                break;
            case TYPE_RECEIVE_TXT:
                bindReceiveTxtMessage(holder, emMessage);
                bindReceiveTxtClickListener(holder, position);
                break;
            case TYPE_SEND_VOICE:
                bindSendVoiceMessage(holder, emMessage);
                bindSendVoiceClickListener(holder, position);
                break;
            case TYPE_RECEIVE_VOICE:
                bindReceiveVoiceMessage(holder, emMessage);
                bindReceiveVoiceClickListener(holder, position);
                break;
            case TYPE_SEND_IMAGE:
                bindSendImageMessage(holder, emMessage);
                break;
            case TYPE_RECEIVE_IMAGE:
                bindReceiveImageMessage(holder, emMessage);
                break;
        }

    }

    private void bindReceiveTxtClickListener(RecyclerView.ViewHolder holder, final int position) {
        if (mOnTextClickListener != null) {
            ((ReceiveTxtViewHolder) holder).tvReceiveContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnTextClickListener.onTextClick(v, position);
                    return false;
                }
            });
        }

        if (mOnUserClickListener != null) {
            ((ReceiveTxtViewHolder) holder).ivChatPersonPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnUserClickListener.onUserClick(v, position);
                }
            });
        }
    }

    private void bindSendTxtClickListener(RecyclerView.ViewHolder holder, final int position) {
        if (mOnReSendClickListener != null) {
            ((SendTxtViewHolder) holder).ivSendFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnReSendClickListener.onReSendClick(v, position);
                }
            });
        }

        if (mOnTextClickListener != null) {
            ((SendTxtViewHolder) holder).tvSendContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnTextClickListener.onTextClick(v, position);
                    return false;
                }
            });
        }

        if (mOnUserClickListener != null) {
            ((SendTxtViewHolder) holder).ivChatPersonPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnUserClickListener.onUserClick(v, position);
                }
            });
        }
    }

    /**
     * 绑定接收文本消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindReceiveTxtMessage(final RecyclerView.ViewHolder holder, EMMessage emMessage) {
        ((ReceiveTxtViewHolder) holder).tvReceiveContent.setText(((EMTextMessageBody) emMessage.getBody()).getMessage());
        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), ((ReceiveTxtViewHolder) holder).ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }
    }

    /**
     * 绑定发文本送消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindSendTxtMessage(final RecyclerView.ViewHolder holder, EMMessage emMessage) {
        ((SendTxtViewHolder) holder).tvSendContent.setText(((EMTextMessageBody) emMessage.getBody()).getMessage());
        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), ((SendTxtViewHolder) holder).ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }
        if (emMessage.status() == EMMessage.Status.SUCCESS) {
            ((SendTxtViewHolder) holder).pbChat.setVisibility(View.GONE);
            ((SendTxtViewHolder) holder).ivSendFailed.setVisibility(View.GONE);
        } else if (emMessage.status() == EMMessage.Status.INPROGRESS) {
            ((SendTxtViewHolder) holder).pbChat.setVisibility(View.VISIBLE);
            ((SendTxtViewHolder) holder).ivSendFailed.setVisibility(View.GONE);
        } else if (emMessage.status() == EMMessage.Status.FAIL) {
            ((SendTxtViewHolder) holder).pbChat.setVisibility(View.GONE);
            ((SendTxtViewHolder) holder).ivSendFailed.setVisibility(View.VISIBLE);
        }

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ((SendTxtViewHolder) holder).pbChat.setVisibility(View.GONE);
                        ((SendTxtViewHolder) holder).ivSendFailed.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ((SendTxtViewHolder) holder).pbChat.setVisibility(View.GONE);
                        ((SendTxtViewHolder) holder).ivSendFailed.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 绑定发文语音消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindSendVoiceMessage(final RecyclerView.ViewHolder holder, EMMessage emMessage) {
        final SendVoiceViewHolder sendVoiceViewHolder = (SendVoiceViewHolder) holder;

        int voiceLength = Math.round(((EMVoiceMessageBody) emMessage.getBody()).getLength());
        sendVoiceViewHolder.tvChatVoiceLength.setText(String.valueOf(voiceLength + "\""));

        ViewGroup.LayoutParams lParams = sendVoiceViewHolder.llVoice.getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f * voiceLength);
        sendVoiceViewHolder.llVoice.setLayoutParams(lParams);

        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), sendVoiceViewHolder.ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }
        if (emMessage.status() == EMMessage.Status.SUCCESS) {
            sendVoiceViewHolder.pbChat.setVisibility(View.GONE);
            sendVoiceViewHolder.ivSendFailed.setVisibility(View.GONE);
        } else if (emMessage.status() == EMMessage.Status.INPROGRESS) {
            sendVoiceViewHolder.pbChat.setVisibility(View.VISIBLE);
            sendVoiceViewHolder.ivSendFailed.setVisibility(View.GONE);
        } else if (emMessage.status() == EMMessage.Status.FAIL) {
            sendVoiceViewHolder.pbChat.setVisibility(View.GONE);
            sendVoiceViewHolder.ivSendFailed.setVisibility(View.VISIBLE);
        }

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        sendVoiceViewHolder.pbChat.setVisibility(View.GONE);
                        sendVoiceViewHolder.ivSendFailed.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        sendVoiceViewHolder.pbChat.setVisibility(View.GONE);
                        sendVoiceViewHolder.ivSendFailed.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private void bindSendVoiceClickListener(RecyclerView.ViewHolder holder, final int position) {
        final SendVoiceViewHolder voiceViewHolder = (SendVoiceViewHolder) holder;
        if (mOnReSendClickListener != null) {
            voiceViewHolder.ivSendFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnReSendClickListener.onReSendClick(v, position);
                }
            });
        }

        if (mOnVoiceClickListener != null) {
            voiceViewHolder.llVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnVoiceClickListener.onVoiceClick(v, position);
                }
            });
        }

        if (mOnUserClickListener != null) {
            voiceViewHolder.ivChatPersonPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnUserClickListener.onUserClick(v, position);
                }
            });
        }
    }

    /**
     * 绑定接收语音消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindReceiveVoiceMessage(final RecyclerView.ViewHolder holder, EMMessage emMessage) {
        final ReceiveVoiceViewHolder receiveVoiceViewHolder = (ReceiveVoiceViewHolder) holder;

        int voiceLength = Math.round(((EMVoiceMessageBody) emMessage.getBody()).getLength());
        receiveVoiceViewHolder.tvChatVoiceLength.setText(String.valueOf(voiceLength + "\""));

        ViewGroup.LayoutParams lParams = receiveVoiceViewHolder.llVoice.getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f * voiceLength);
        receiveVoiceViewHolder.llVoice.setLayoutParams(lParams);

        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), receiveVoiceViewHolder.ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }
        if (emMessage.isListened()) {
            receiveVoiceViewHolder.tvChatVoiceUnread.setVisibility(View.GONE);
        } else {
            receiveVoiceViewHolder.tvChatVoiceUnread.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 绑定发送图片消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindSendImageMessage(final RecyclerView.ViewHolder holder, final EMMessage emMessage) {
        final SendImageViewHolder sendImageViewHolder = (SendImageViewHolder) holder;
        EMImageMessageBody imgBody = ((EMImageMessageBody) emMessage.getBody());
        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), sendImageViewHolder.ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }

        String filePath = imgBody.getLocalUrl();
        String thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
        showImageView(thumbPath, sendImageViewHolder.ivChatImage, filePath, emMessage);

        switch (emMessage.status()) {
            case SUCCESS:
                sendImageViewHolder.pbChat.setVisibility(View.GONE);
                sendImageViewHolder.tvImageProgress.setVisibility(View.GONE);
                break;
            case FAIL:
                break;
            case INPROGRESS:
                sendImageViewHolder.tvImageProgress.setText(emMessage.progress() + "%");
                break;
            default:
                break;
        }

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendImageViewHolder.pbChat.setVisibility(View.GONE);
                        sendImageViewHolder.tvImageProgress.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendImageViewHolder.tvImageProgress.setText(emMessage.progress() + "%");
                    }
                });
            }
        });

    }

    private boolean showImageView(final String thumbernailPath, final ImageView iv, final String localFullSizePath, final EMMessage message) {
        final EMImageMessageBody imgBody = ((EMImageMessageBody) message.getBody());
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            return true;
        } else {
            new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    int reqWidth = 200;
                    int reqHeight = 350;
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return ImageUtils.decodeScaleImage(thumbernailPath, reqWidth, reqHeight);
                    } else if (new File(imgBody.thumbnailLocalPath()).exists()) {
                        return ImageUtils.decodeScaleImage(imgBody.thumbnailLocalPath(), reqWidth, reqHeight);
                    } else {
                        if (message.direct() == EMMessage.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return ImageUtils.decodeScaleImage(localFullSizePath, reqWidth, reqHeight);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }

                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        iv.setImageBitmap(image);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    } else {
                        if (message.status() == EMMessage.Status.FAIL) {
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    EMClient.getInstance().chatManager().downloadThumbnail(message);
                                }
                            }).start();
                        }

                    }
                }
            }.execute();

            return true;
        }
    }

    /**
     * 绑定接收图片消息
     *
     * @param holder
     * @param emMessage
     */
    private void bindReceiveImageMessage(final RecyclerView.ViewHolder holder, final EMMessage emMessage) {
        final ReceiveImageViewHolder receiveImageViewHolder = (ReceiveImageViewHolder) holder;
        final EMImageMessageBody imgBody = ((EMImageMessageBody) emMessage.getBody());

        try {
            ImageUtil.loadImage(context, emMessage.getStringAttribute("nickUrl"), receiveImageViewHolder.ivChatPersonPhoto, new CircleTransformation());
        } catch (HyphenateException e) {
            Log.e("ChatAdapter", e.toString());
        }

            if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                emMessage.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String filePath = imgBody.getLocalUrl();
                                String thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                                showImageView(thumbPath, receiveImageViewHolder.ivChatImage, filePath, emMessage);
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {

                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }
                });
            } else {
                String thumbPath = imgBody.thumbnailLocalPath();
                if (!new File(thumbPath).exists()) {
                    thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                }
                showImageView(thumbPath, receiveImageViewHolder.ivChatImage, imgBody.getLocalUrl(), emMessage);
            }
            return;

    }

    private void bindReceiveVoiceClickListener(RecyclerView.ViewHolder holder, final int position) {
        final ReceiveVoiceViewHolder voiceViewHolder = (ReceiveVoiceViewHolder) holder;

        if (mOnVoiceClickListener != null) {
            voiceViewHolder.llVoiceParen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(position).setListened(true);
                    EMClient.getInstance().chatManager().setVoiceMessageListened(data.get(position));
                    mOnVoiceClickListener.onVoiceClick(v, position);
                }
            });
        }

        if (mOnUserClickListener != null) {
            voiceViewHolder.ivChatPersonPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnUserClickListener.onUserClick(v, position);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<EMMessage> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class SendTxtViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_send_content)
        TextView tvSendContent;
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;
        @BindView(R.id.iv_send_failed)
        ImageView ivSendFailed;
        @BindView(R.id.pb_chat)
        ProgressBar pbChat;

        public SendTxtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ReceiveTxtViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_receive_content)
        TextView tvReceiveContent;
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;

        public ReceiveTxtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SendVoiceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_chat_voice_length)
        TextView tvChatVoiceLength;
        @BindView(R.id.v_chat_voice)
        View vChatVoice;
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;
        @BindView(R.id.iv_send_failed)
        ImageView ivSendFailed;
        @BindView(R.id.pb_chat)
        ProgressBar pbChat;
        @BindView(R.id.ll_voice)
        LinearLayout llVoice;


        public SendVoiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ReceiveVoiceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_chat_voice_length)
        TextView tvChatVoiceLength;
        @BindView(R.id.v_chat_voice)
        View vChatVoice;
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;
        @BindView(R.id.iv_chat_voice_unread)
        ImageView tvChatVoiceUnread;
        @BindView(R.id.ll_voice)
        LinearLayout llVoice;
        @BindView(R.id.ll_voice_parent)
        LinearLayout llVoiceParen;

        public ReceiveVoiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SendImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;
        @BindView(R.id.iv_send_failed)
        ImageView ivSendFailed;
        @BindView(R.id.iv_chat_image)
        ImageView ivChatImage;
        @BindView(R.id.pb_chat)
        ProgressBar pbChat;
        @BindView(R.id.tv_image_progress)
        TextView tvImageProgress;


        public SendImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ReceiveImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_chat_person_photo)
        ImageView ivChatPersonPhoto;
        @BindView(R.id.iv_chat_image)
        ImageView ivChatImage;


        public ReceiveImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
