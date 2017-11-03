package com.risfond.rnss.message.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.utils.GlideUtil;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.em.EMHelper;
import com.risfond.rnss.common.utils.ChatTime;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.Chat;
import com.risfond.rnss.entry.Messages;
import com.risfond.rnss.entry.UnReadMessageCountEventBus;
import com.risfond.rnss.message.adapter.MessageAdapter;
import com.risfond.rnss.search.activity.SearchActivity;
import com.risfond.rnss.widget.RecycleViewDivider;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.os.Looper.getMainLooper;

/**
 * 消息主页面
 * Created by Abbott on 2017/5/9.
 */

public class MessageFragment extends BaseFragment {
    private Context context;
    private MessageAdapter messageAdapter;
    private List<Messages> messages = new ArrayList<>();
    private List<EMConversation> emConversations = new ArrayList<>();
    private EMMessageListener msgListener;
    private EMConnectionListener connectionListener;
    private int allUnReadCount;

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_message)
    SwipeMenuRecyclerView rvMessage;
    @BindView(R.id.tv_error)
    TextView tvError;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();

        initListView();
        itemClick();
        initMessageListener();
        connectionListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        messages.clear();
        initAllConversations();
    }

    /**
     * 加载所有会话列表
     */
    private void initAllConversations() {
        emConversations.clear();
        EMHelper.getInstance().updateChatInfo();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        if (conversations.size() > 0) {
            for (String key : conversations.keySet()) {
                emConversations.add(conversations.get(key));
            }
        }

        sortEMConversations();
        messages.clear();
        for (EMConversation conversation : emConversations) {
            EMMessage emMessage = conversation.getLastMessage();
            String ext = conversation.getExtField();
            int unRead = conversation.getUnreadMsgCount();
            createMessageList(ext, unRead, emMessage);
        }
        updateMessageList();

    }

    private void sortEMConversations() {
        Collections.sort(emConversations, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {
                if (o2.getLastMessage().getMsgTime() >= o1.getLastMessage().getMsgTime()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    /**
     * 环信消息转换为自己的消息，并创建消息列表
     *
     * @param emMessage
     * @return
     */
    private void createMessageList(String ext, int unRead, EMMessage emMessage) {
        Messages message = new Messages();
        try {
            if (emMessage.getChatType() == EMMessage.ChatType.GroupChat) {
                if (emMessage.getTo().equals(SPUtil.loadCompanyGroupId(context))) {
                    message.setEaseMobAccount(emMessage.getTo());
                    message.setNickName("全国聊天");
                    message.setNickUrl(Constant.IMG_COUNTRY);
                    GlideUtil.beforeDownLoadImage(context, Constant.IMG_COUNTRY);
                    message.setChatType(EaseConstant.CHATTYPE_GROUP);
                    if (emMessage.getFrom().equals(SPUtil.loadEaseMobAccount(context))) {//最后一次是自己发的
                        message.setGroupFrom("");
                    } else {
                        message.setGroupFrom(emMessage.getStringAttribute("nickName") + ": ");
                    }
                } else {
                    message.setEaseMobAccount(emMessage.getTo());
                    message.setNickName(SPUtil.loadCompanyName(context));
                    message.setNickUrl(Constant.IMG_COMPANY);
                    GlideUtil.beforeDownLoadImage(context, Constant.IMG_COMPANY);
                    message.setChatType(EaseConstant.CHATTYPE_GROUP);
                    if (emMessage.getFrom().equals(SPUtil.loadEaseMobAccount(context))) {//最后一次是自己发的
                        message.setGroupFrom("");
                    } else {
                        message.setGroupFrom(emMessage.getStringAttribute("nickName") + ": ");
                    }
                }


            } else {
                message.setChatType(EaseConstant.CHATTYPE_SINGLE);
                if (emMessage.getFrom().equals(SPUtil.loadEaseMobAccount(context))) {//最后一次是自己发的
                    message.setEaseMobAccount(emMessage.getTo());
                    message.setNickName(ext.split(" ")[0]);
                    message.setNickUrl(ext.split(" ")[1]);
                    GlideUtil.beforeDownLoadImage(context, ext.split(" ")[1]);
                } else {
                    message.setEaseMobAccount(emMessage.getFrom());
                    message.setNickName(emMessage.getStringAttribute("nickName", ""));
                    message.setNickUrl(emMessage.getStringAttribute("nickUrl", ""));
                    GlideUtil.beforeDownLoadImage(context, emMessage.getStringAttribute("nickUrl", ""));
                }
            }

            message.setTip(unRead);

            if (emMessage.getType() == EMMessage.Type.TXT) {
                String content = ((EMTextMessageBody) emMessage.getBody()).getMessage();
                if (content.equals("")) {
                    content = "[动态表情]";
                }
                message.setContent(content);
            } else if (emMessage.getType() == EMMessage.Type.VOICE) {
                message.setContent("[语音]");
            } else if (emMessage.getType() == EMMessage.Type.IMAGE) {
                message.setContent("[图片]");
            }

            message.setTime(ChatTime.getChatTime(emMessage.getMsgTime(), emMessage.localTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        messages.add(message);

    }

    /**
     * 刷新消息列表
     */
    private void updateMessageList() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                messageAdapter.updateMessage(messages);
                allUnReadMessageCount(messages);
            }
        });

    }

    /**
     * 获取未读数量
     *
     * @param messages
     * @return
     */
    private void allUnReadMessageCount(List<Messages> messages) {
        allUnReadCount = 0;
        for (Messages message : messages) {
            allUnReadCount += message.getTip();
        }
        EventBus.getDefault().post(new UnReadMessageCountEventBus("unReadCount", allUnReadCount));
    }

    private void initMessageListener() {
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EMClient.getInstance().chatManager().importMessages(messages);
                for (EMMessage emMessage : messages) {
                    if (emMessage.getChatType() == EMMessage.ChatType.GroupChat) {
                        if (SPUtil.loadCloseMsg(context)) {//群组且屏蔽了消息

                        } else {
                            if (EaseUI.getInstance().chattingId.equals(emMessage.getTo())) {//正在聊天的人屏蔽消息

                            } else {
                                EMHelper.getInstance().getNotifier().onNewMsg(emMessage);
                            }
                        }
                    } else if (EaseUI.getInstance().chattingId.equals(emMessage.getFrom())) {//正在聊天的人屏蔽消息
                        if (EaseUI.getInstance().chattingId.equals(emMessage.getFrom())) {//正在聊天的人屏蔽消息

                        } else {
                            EMHelper.getInstance().getNotifier().onNewMsg(emMessage);
                        }
                    }
                    EMClient.getInstance().chatManager().downloadAttachment(emMessage);
                    EMClient.getInstance().chatManager().downloadThumbnail(emMessage);

                    if (emMessage.getType() == EMMessage.Type.IMAGE) {
                        GlideUtil.beforeDownLoadImage(context, ((EMImageMessageBody) emMessage.getBody()).getLocalUrl());
                    }

                    //更新会话列表
                    initAllConversations();
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println();
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

    private void initListView() {

        messageAdapter = new MessageAdapter(context, messages);
        rvMessage.setLayoutManager(new LinearLayoutManager(context));
        rvMessage.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.colo_bg_login)));
        rvMessage.setAdapter(messageAdapter);

        //单条移除时去掉动画效果
        rvMessage.getItemAnimator().setAddDuration(0);
        rvMessage.getItemAnimator().setChangeDuration(0);
        rvMessage.getItemAnimator().setMoveDuration(0);
        rvMessage.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) rvMessage.getItemAnimator()).setSupportsChangeAnimations(false);

        // 创建侧滑菜单
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.delete_item))
                        .setText("删除") // 文字。
                        .setTextSize(16)
                        .setTextColor(Color.WHITE) // 文字的颜色。
                        .setWidth(240) // 菜单宽度。
                        .setHeight(LinearLayout.LayoutParams.MATCH_PARENT); // 菜单高度。
                rightMenu.addMenuItem(deleteItem); // 在左侧添加一个菜单。
            }
        };
        // 设置监听器。
        rvMessage.setSwipeMenuCreator(swipeMenuCreator);
        rvMessage.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();
                //删除和某个user会话，如果需要保留聊天记录，传false
                EMClient.getInstance().chatManager().getConversation(messages.get(adapterPosition).getEaseMobAccount()).markAllMessagesAsRead();
                EMClient.getInstance().chatManager().deleteConversation(messages.get(adapterPosition).getEaseMobAccount(), true);
                messages.remove(adapterPosition);
                messageAdapter.updateMessage(messages);
                allUnReadMessageCount(messages);
            }
        });
    }

    private void itemClick() {
        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Messages message = messages.get(position);
                Chat2Activity.startAction(context, message.getEaseMobAccount(), SPUtil.loadName(context), SPUtil.loadHeadPhoto(context),
                        message.getNickName(), message.getNickUrl(), message.getChatType());

            }
        });

    }

    protected void connectionListener() {
        connectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        onConnectionConnected();
                    }

                });
            }

            @Override
            public void onDisconnected(int errorCode) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        };
        EMClient.getInstance().addConnectionListener(connectionListener);

    }

    private void onConnectionDisconnected() {
        tvError.setVisibility(View.VISIBLE);
        if (NetUtils.hasNetwork(context)) {
            tvError.setText("连接服务器失败");
        } else {
            tvError.setText("网络连接不可用");
        }
    }

    private void onConnectionConnected() {
        tvError.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_search, R.id.tv_error})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_search) {
            SearchActivity.startAction(context);
        } else if (v.getId() == R.id.tv_error) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    protected void lazyLoad() {

    }

}
