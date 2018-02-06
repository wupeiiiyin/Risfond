package com.risfond.rnss.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.chat.adapter.ChatAdapter;
import com.risfond.rnss.chat.audio.AudioRecorderButton;
import com.risfond.rnss.chat.audio.MediaPlayerManager;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class ChatActivity extends BaseActivity implements AudioRecorderButton.OnAudioFinishRecorderListener {

    private Context context;
    private ChatAdapter adapter;
    private int pagesize = 20;
    List<EMMessage> data = new ArrayList<>();
    List<EMMessage> chats = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private String easemobAccount;//好友IM id
    private String toNickName;//好友的的昵称
    private String toNickUrl;//好友的头像
    private String fromNickName;//自己的昵称
    private String fromNickUrl;//自己的头像
    private EMMessage message;
    private String content;//发送文本内容
    private String audioFilePath;//发送语音文件地址
    private int audioLength;//发送语音文时长
    private EMMessageListener msgListener;
    private EMConversation conversation;
    private ImageView ivChatVoiceUnRead;
    private View vChatVoice;

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    @BindView(R.id.et_chat_input)
    EditText etChatInput;
    @BindView(R.id.tv_chat_send)
    TextView tvChatSend;
    @BindView(R.id.cb_voice)
    CheckBox cbVoice;
    @BindView(R.id.cb_emoticon)
    CheckBox cbEmoticon;
    @BindView(R.id.tv_add_more)
    TextView tvAddMore;
    @BindView(R.id.tv_chat_audio)
    AudioRecorderButton tvChatAudio;
    @BindView(R.id.tv_select_photo)
    TextView tvSelectPhoto;
    @BindView(R.id.tv_take_photo)
    TextView tvTakePhoto;
    @BindView(R.id.ll_chat_bottom_more)
    LinearLayout llChatBottomMore;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_chat;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ChatActivity.this;
        easemobAccount = getIntent().getStringExtra("easemobAccount");

        fromNickName = SPUtil.loadName(context);
        fromNickUrl = getIntent().getStringExtra("fromNickUrl");
        toNickName = getIntent().getStringExtra("toNickName");
        toNickUrl = getIntent().getStringExtra("toNickUrl");

        tvTitle.setText(toNickName);

        tvChatAudio.setOnAudioFinishRecorderListener(this);

        initListView();
        onItemClick();
        inputChanged();
        initMessageListener();
        onConversationInit();

    }

    private void inputChanged() {
        etChatInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    tvChatSend.setVisibility(View.VISIBLE);
                    tvAddMore.setVisibility(View.GONE);
                } else {
                    tvChatSend.setVisibility(View.GONE);
                    tvAddMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initMessageListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (EMMessage emMessage : messages) {
                    chats.add(0, emMessage);
                    data.add(emMessage);
                    if (easemobAccount.equals(emMessage.getFrom())) {
                        updateReceiveData();
                        conversation.markMessageAsRead(emMessage.getMsgId());
                    }
                }

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    /**
     * 创建一条消息
     */
    private void createMessage() {
        if (cbVoice.isChecked()) {
            //audioFilePath为语音文件路径，audioLength为录音时间(秒)
            message = EMMessage.createVoiceSendMessage(audioFilePath, audioLength, easemobAccount);
        } else {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            message = EMMessage.createTxtSendMessage(content, easemobAccount);
        }
        sendMessage();
    }

    /**
     * 创建一条图片消息
     *
     * @param imagePath
     */
    private void createImageMessage(String imagePath) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        message = EMMessage.createImageSendMessage(imagePath, false, easemobAccount);
        sendMessage();
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        // 增加自己特定的属性
        message.setAttribute("nickName", fromNickName);//自己的昵称
        message.setAttribute("nickUrl", fromNickUrl);//自己的头像

        conversation.setExtField(toNickName + "," + toNickUrl);

        chats.add(0, message);
        data.add(message);
        adapter.updateData(chats);
        linearLayoutManager.smoothScrollToPosition(rvChat, new RecyclerView.State(), 0);
        etChatInput.setText("");

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }

    private void initListView() {

        adapter = new ChatAdapter(context, chats);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);//反转布局
        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setAdapter(adapter);
    }

    private void initData() {
        for (int j = data.size() - 1; j >= 0; j--) {
            chats.add(data.get(j));
        }
        adapter.updateData(chats);

    }

    /**
     * 重新发送失败的消息
     *
     * @param position
     */
    private void reSendFailedMessage(final int position) {
        DialogUtil.getInstance().showConfigDialog(context, "重发该消息？", "重发", "取消", new DialogUtil.PressCallBack() {
            @Override
            public void onPressButton(int buttonIndex) {
                if (buttonIndex == DialogUtil.BUTTON_OK) {
                    EMClient.getInstance().chatManager().sendMessage(chats.get(position));
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(easemobAccount, EMConversation.EMConversationType.Chat, true);
        conversation.markAllMessagesAsRead();

        List<EMMessage> msgs = conversation.getAllMessages();
        for (EMMessage emMessage : msgs) {
            data.add(emMessage);
        }

        initData();

    }

    private void updateReceiveData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayoutManager.smoothScrollToPosition(rvChat, new RecyclerView.State(), 0);
                adapter.updateData(chats);
            }
        });
    }

    @OnCheckedChanged({R.id.cb_voice})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_voice:
                if (isChecked) {
                    tvChatAudio.setVisibility(View.VISIBLE);
                    etChatInput.setVisibility(View.GONE);
                    ImeUtil.hideSoftKeyboard(etChatInput);
                } else {
                    tvChatAudio.setVisibility(View.GONE);
                    etChatInput.setVisibility(View.VISIBLE);
                    etChatInput.requestFocus();
                    ImeUtil.showSoftKeyboard(etChatInput);
                }
                break;
        }
    }

    private void onItemClick() {
        adapter.setOnReSendClickListener(new ChatAdapter.OnReSendClickListener() {
            @Override
            public void onReSendClick(View view, int position) {
                reSendFailedMessage(position);
            }
        });

        adapter.setOnTextClickListener(new ChatAdapter.OnTextClickListener() {
            @Override
            public void onTextClick(View view, int position) {

            }
        });

        adapter.setOnUserClickListener(new ChatAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(View view, int position) {

            }
        });

        adapter.setOnVoiceClickListener(new ChatAdapter.OnVoiceClickListener() {
            @Override
            public void onVoiceClick(final View view, int position) {
                ivChatVoiceUnRead = (ImageView) view.findViewById(R.id.iv_chat_voice_unread);
                vChatVoice = view.findViewById(R.id.v_chat_voice);

                EMMessage emMessage = chats.get(position);
                if (emMessage.isListened()) {
                    ivChatVoiceUnRead.setVisibility(View.GONE);
                }
                if (emMessage.getFrom().equals(SPUtil.loadEaseMobAccount(context))) {
                    playVoice(vChatVoice, emMessage, R.drawable.play_voice_send_anim, R.mipmap.voice_send_anim3);
                } else {
                    playVoice(vChatVoice, emMessage, R.drawable.play_voice_receive_anim, R.mipmap.voice_receive_anim3);
                }

            }
        });
    }

    private void playVoice(final View view, EMMessage emMessage, final int animId, final int mipmapId) {
        view.setBackgroundResource(animId);
        AnimationDrawable animation = (AnimationDrawable) view.getBackground();
        animation.start();
        MediaPlayerManager.playSound(((EMVoiceMessageBody) emMessage.getBody()).getLocalUrl(), new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                //播放完成后修改图片
                view.setBackgroundResource(mipmapId);
            }
        });
    }

    @OnClick({R.id.tv_chat_send, R.id.et_chat_input, R.id.tv_add_more, R.id.tv_select_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chat_send:
                content = etChatInput.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    createMessage();
                }
                break;

            case R.id.tv_add_more:
                ImeUtil.hideSoftKeyboard(etChatInput);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llChatBottomMore.setVisibility(View.VISIBLE);
                        tvChatAudio.setVisibility(View.GONE);
                        etChatInput.setVisibility(View.VISIBLE);
                        cbVoice.setChecked(false);
                    }
                }, 100);
                break;

            case R.id.et_chat_input:
                llChatBottomMore.setVisibility(View.GONE);
                break;

            case R.id.tv_select_photo://选择相册
                break;
        }
    }

    @OnTouch({R.id.rv_chat})
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.rv_chat) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ImeUtil.hideSoftKeyboard(etChatInput);
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) { // Successfully.
                // 不要质疑你的眼睛，就是这么简单。
            } else if (resultCode == RESULT_CANCELED) { // User canceled.
                // 用户取消了操作。
            }
        }
    }

    /**
     * 启动聊天页面
     *
     * @param context
     * @param easemobAccount 好友IM id
     * @param toNickName     好友昵称
     * @param toNickUrl      好友头像
     * @param fromNickName   自己昵称
     * @param fromNickUrl    自己头像
     */
    public static void startAction(Context context, String easemobAccount, String toNickName, String toNickUrl
            , String fromNickName, String fromNickUrl) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("toNickName", toNickName);
        intent.putExtra("toNickUrl", toNickUrl);
        intent.putExtra("fromNickName", fromNickName);
        intent.putExtra("fromNickUrl", fromNickUrl);
        intent.putExtra("easemobAccount", easemobAccount);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        MediaPlayerManager.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerManager.pause();
    }

    @Override
    public void onFinish(float seconds, String filePath) {
        //发送语音消息
        audioFilePath = filePath;
        audioLength = Math.round(seconds);
        createMessage();
    }

}
