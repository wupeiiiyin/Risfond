package com.risfond.rnss.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowBigExpression;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowFile;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowText;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVideo;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoice;

import java.util.List;

/**
 * Created by Abbott on 2017/6/6.
 */

public class Chat3Adapter extends BaseAdapter {
    private Context context;
    private List<EMMessage> emMessages;

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_SENT_EXPRESSION = 12;
    private static final int MESSAGE_TYPE_RECV_EXPRESSION = 13;

    private EaseChatMessageList.MessageListItemClickListener itemClickListener;

    public Chat3Adapter(Context context, List<EMMessage> emMessages) {
        this.context = context;
        this.emMessages = emMessages;
    }

    public void setItemClickListener(EaseChatMessageList.MessageListItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public int getViewTypeCount() {
        return 14;
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = emMessages.get(position);
        if (message == null) {
            return -1;
        }

        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EXPRESSION : MESSAGE_TYPE_SENT_EXPRESSION;
            }
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.LOCATION) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION : MESSAGE_TYPE_SENT_LOCATION;
        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SENT_VOICE;
        }
        if (message.getType() == EMMessage.Type.VIDEO) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SENT_VIDEO;
        }
        if (message.getType() == EMMessage.Type.FILE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SENT_FILE;
        }

        return -1;// invalid
    }

    @Override
    public int getCount() {
        return emMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return emMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EMMessage message = (EMMessage) getItem(position);
        if (convertView == null) {
            convertView = createChatRow(context, message, position);
        }

        //refresh ui with messages
        ((EaseChatRow) convertView).setUpView(message, position, itemClickListener,emMessages);

        return convertView;
    }

    protected EaseChatRow createChatRow(Context context, EMMessage message, int position) {
        EaseChatRow chatRow = null;
        //        if(customRowProvider != null && customRowProvider.getCustomChatRow(message, position, this) != null){
        //            return customRowProvider.getCustomChatRow(message, position, this);
        //        }
        switch (message.getType()) {
            case TXT:
                if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    chatRow = new EaseChatRowBigExpression(context, message, position, this, null);
                } else {
                    chatRow = new EaseChatRowText(context, message, position, this, null);
                }
                break;
            case LOCATION:
                chatRow = new EaseChatRowLocation(context, message, position, this);
                break;
            case FILE:
                chatRow = new EaseChatRowFile(context, message, position, this, null);
                break;
            case IMAGE:
                chatRow = new EaseChatRowImage(context, message, position, this, emMessages);
                break;
            case VOICE:
                chatRow = new EaseChatRowVoice(context, message, position, this);
                break;
            case VIDEO:
                chatRow = new EaseChatRowVideo(context, message, position, this);
                break;
            default:
                break;
        }

        return chatRow;
    }

}
