package com.risfond.rnss.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.risfond.rnss.R;
import com.risfond.rnss.common.activity.URLWebViewActivity;
import com.risfond.rnss.common.constant.Constant;

import com.risfond.rnss.common.utils.UmengUtil;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;

import com.risfond.rnss.home.resume.activity.ResumeDetailActivity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Chat2Activity extends FragmentActivity implements EaseChatFragment.EaseChatFragmentHelper {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.fl_chat)
    FrameLayout flChat;

    private Context context;
    private EaseChatFragment easeChatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        ButterKnife.bind(this);
        context = Chat2Activity.this;
        initData(getIntent());
        easeChatFragment.setChatFragmentListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EaseUI.getInstance().getNotifier().reset();
        //友盟统计
        //友盟统计
        UmengUtil.onResumeToActivity(this);
        EaseUI.getInstance().chattingId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        UmengUtil.onPauseToActivity(this);
        EaseUI.getInstance().chattingId = "";
    }

    @Override
    protected void onNewIntent(Intent intent) {
        initData(intent);
    }

    private void initData(Intent intent) {

        //创建一个会话的fragment
        easeChatFragment = new EaseChatFragment();

        easeChatFragment.setArguments(intent.getExtras());//传递参数给EaseChatFragment

        //替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, easeChatFragment).commitAllowingStateLoss();

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
        Intent intent = new Intent(context, Chat2Activity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, easemobAccount);//id
        intent.putExtra("fromNickName", fromNickName);
        intent.putExtra("fromNickUrl", fromNickUrl);
        intent.putExtra("toNickName", toNickName);
        intent.putExtra("toNickUrl", toNickUrl);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        easeChatFragment.onBackPressed();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        ContactsInfoActivity.startAction(context, username);
    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        if (message.getType() == EMMessage.Type.TXT) {
            String msg = ((EMTextMessageBody) message.getBody()).getMessage();
            checkHasUrl(msg, message);
        }
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

    private void checkHasUrl(String content, EMMessage message) {
        //又碰上一个坑，在Android中的\p{Alnum}和Java中的\p{Alnum}不是同一个值，非得要我换成[a-zA-Z0-9]才行
        Pattern pattern = Pattern.compile("(http|https|ftp|svn|www)(://|.)([a-zA-Z0-9]+[/?.?])" +
                "+[a-zA-Z0-9]*\\??([a-zA-Z0-9]*=[a-zA-Z0-9]*&?)*");
        Matcher matcher = pattern.matcher(content);

        try {
            while (matcher.find()) {
                String url = matcher.group();
                if (url.startsWith(Constant.SPLIT_RESUME_SHARE)) {
                    ResumeDetailActivity.startAction(context, url.split(Constant.SPLIT_RESUME_SHARE)[1]);
                } else if (url.startsWith(Constant.SPLIT_RESUME_FROM_RNSS)) {
                    ResumeDetailActivity.startAction(context, url.split("=")[1]);
                } else {
                    URLWebViewActivity.startAction(context, url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
