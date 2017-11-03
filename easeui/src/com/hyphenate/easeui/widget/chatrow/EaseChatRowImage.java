package com.hyphenate.easeui.widget.chatrow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.ui.EaseShowBigImageActivity;
import com.hyphenate.easeui.ui.ImageViewActivity;
import com.hyphenate.easeui.ui.ShowImageViewPagerActivity;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.utils.GlideUtil;
import com.hyphenate.easeui.widget.ChatImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EaseChatRowImage extends EaseChatRowFile {

    protected ChatImageView imageView;
    private EMImageMessageBody imgBody;

    public EaseChatRowImage(Context context, EMMessage message, int position, BaseAdapter adapter, List<EMMessage> emMessages) {
        super(context, message, position, adapter, emMessages);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ChatImageView) findViewById(R.id.image);
    }


    @Override
    protected void onSetUpView() {
        imgBody = (EMImageMessageBody) message.getBody();

        ViewGroup.LayoutParams lParams = imageView.getLayoutParams();
        int w = imgBody.getWidth();
        int h = imgBody.getHeight();
        if (w > 300 || h > 400) {
            w = 300;
            h = 400;
            lParams.width = w;
            lParams.height = h;
            imageView.setLayoutParams(lParams);
        }else {
            lParams.width = 250;
            lParams.height = 250;
            imageView.setLayoutParams(lParams);
        }


        // received messages
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                imageView.setImageResource(R.drawable.ease_default_image);
                setMessageReceiveCallback();
            } else {
                progressBar.setVisibility(View.GONE);
                percentageView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ease_default_image);
                String thumbPath = imgBody.getRemoteUrl();
                //                showImageView(thumbPath, imageView, imgBody.getLocalUrl(), message);
                GlideUtil.downLoadImage(context, thumbPath, imageView);
            }
            return;
        }

        String filePath = imgBody.getLocalUrl();
        //        String thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
        //        showImageView(thumbPath, imageView, filePath, message);
        GlideUtil.downLoadImage(context, filePath, imageView);

        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        ArrayList<String> imageList = new ArrayList<>();
        HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
        int key = 0;
        int value = 0;
        for (EMMessage emMessage : emMessages) {
            if (emMessage.getType() == EMMessage.Type.IMAGE) {
                imgBody = (EMImageMessageBody) emMessage.getBody();
                //                imageList.add(imgBody.getLocalUrl());
                imageList.add(imgBody.getRemoteUrl());
                imagePosition.put(key, value);
                value++;
            }
            key++;
        }

        Intent intent = new Intent(context, ShowImageViewPagerActivity.class);
        intent.putStringArrayListExtra("images", imageList);
        intent.putExtra("clickedIndex", imagePosition.get(position));
        context.startActivity(intent);

        /*Intent intent = new Intent(context, EaseShowBigImageActivity.class);
        File file = new File(imgBody.getLocalUrl());
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            intent.putExtra("uri", uri);
        } else {
            // The local full size pic does not exist yet.
            // ShowBigImage needs to download it from the server
            // first
            String msgId = message.getMsgId();
            intent.putExtra("messageId", msgId);
            intent.putExtra("localUrl", imgBody.getLocalUrl());
        }
        if (message != null && message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked()
                && message.getChatType() == ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        context.startActivity(intent);*/
    }

    /**
     * load image into image view
     *
     * @param thumbernailPath
     * @param iv
     * @return the image exists or not
     */
    private boolean showImageView(final String thumbernailPath, final ImageView iv, final String localFullSizePath, final EMMessage message) {
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
                    int reqWidth = 300;
                    int reqHeight = 400;
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return EaseImageUtils.decodeScaleImage(thumbernailPath, reqWidth, reqHeight);
                    } else if (new File(imgBody.thumbnailLocalPath()).exists()) {
                        return EaseImageUtils.decodeScaleImage(imgBody.thumbnailLocalPath(), reqWidth, reqHeight);
                    } else {
                        if (message.direct() == EMMessage.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return EaseImageUtils.decodeScaleImage(localFullSizePath, reqWidth, reqHeight);
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
                            if (EaseCommonUtils.isNetWorkConnected(activity)) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        EMClient.getInstance().chatManager().downloadThumbnail(message);
                                    }
                                }).start();
                            }
                        }

                    }
                }
            }.execute();

            return true;
        }
    }

}
