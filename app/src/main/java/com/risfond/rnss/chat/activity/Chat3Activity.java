package com.risfond.rnss.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.risfond.rnss.R;
import com.risfond.rnss.chat.ChatHelper;
import com.risfond.rnss.chat.adapter.Chat3Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Chat3Activity extends AppCompatActivity implements ChatHelper {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorder;
    @BindView(R.id.input_menu)
    EaseChatInputMenu inputMenu;

    protected Context context;
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;

    protected int[] itemStrings = {com.hyphenate.easeui.R.string.attach_picture, com.hyphenate.easeui.R.string.attach_take_pic};
    protected int[] itemdrawables = {com.hyphenate.easeui.R.drawable.ease_chat_image_selector, com.hyphenate.easeui.R.drawable.ease_chat_takepic_selector};
    protected int[] itemIds = {ITEM_PICTURE, ITEM_TAKE_PICTURE, ITEM_LOCATION};

    protected MyItemClickListener extendMenuItemClickListener;

    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected EMConversation conversation;
    protected int chatType;
    protected String toChatUsername;

    protected String fromNickName;
    protected String fromNickUrl;
    protected String toNickName;
    protected String toNickUrl;

    private List<EMMessage> emMessages = new ArrayList<>();
    private Chat3Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat3);
        ButterKnife.bind(this);
        context = Chat3Activity.this;
        // check if single chat or group chat
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id
        toChatUsername = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        fromNickName = getIntent().getStringExtra("fromNickName");
        fromNickUrl = getIntent().getStringExtra("fromNickUrl");
        toNickName = getIntent().getStringExtra("toNickName");
        toNickUrl = getIntent().getStringExtra("toNickUrl");


        init();
        registerExtendMenuItem();
        setChatListener(this);
        onConversationInit();
        setListItemClickListener();

        adapter = new Chat3Adapter(context, emMessages);

    }

    private void init() {
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorder.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {

                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {

            }
        });

        extendMenuItemClickListener = new MyItemClickListener();
    }

    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number
        emMessages = conversation.getAllMessages();
        int msgCount = emMessages != null ? emMessages.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (emMessages != null && emMessages.size() > 0) {
                msgId = emMessages.get(0).getMsgId();
            }
            emMessages = conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    public void setItemClickListener(EaseChatMessageList.MessageListItemClickListener listener) {
        if (adapter != null) {
            adapter.setItemClickListener(listener);
        }
    }

    protected void setListItemClickListener() {
        setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatHelper != null) {
                    chatHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if (chatHelper != null) {
                    chatHelper.onAvatarLongClick(username);
                }
            }

            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(context, com.hyphenate.easeui.R.string.resend, com.hyphenate.easeui.R.string.confirm_resend, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (!confirmed) {
                            return;
                        }

                    }
                }, true).show();
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                if (chatHelper != null) {
                    chatHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatHelper == null) {
                    return false;
                }
                return chatHelper.onMessageBubbleClick(message);
            }

        });
    }


    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }


    /**
     * handle the click event for extend menu
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            if (chatHelper != null) {
                if (chatHelper.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE://拍照
                    break;
                case ITEM_PICTURE://选择相册
                    break;
                case ITEM_LOCATION:
                    break;

                default:
                    break;
            }
        }

    }

    protected ChatHelper chatHelper;

    public void setChatListener(ChatHelper chatHelper) {
        this.chatHelper = chatHelper;
    }

    /**
     * @param context
     * @param easemobAccount 好友IM id
     * @param fromNickName   自己昵称
     * @param fromNickUrl    自己头像
     * @param toNickName     好友昵称
     * @param toNickUrl      好友头像
     * @param chatType       聊天类型
     */
    public static void startAction(Context context, String easemobAccount, String fromNickName, String fromNickUrl
            , String toNickName, String toNickUrl, int chatType) {
        Intent intent = new Intent(context, Chat3Activity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, easemobAccount);//id
        intent.putExtra("fromNickName", fromNickName);
        intent.putExtra("fromNickUrl", fromNickUrl);
        intent.putExtra("toNickName", toNickName);
        intent.putExtra("toNickUrl", toNickUrl);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        context.startActivity(intent);
    }


}
