package com.risfond.rnss.common.em;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EmojiconAddGroupData;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.risfond.rnss.BuildConfig;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.message.activity.MainActivity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Abbott on 2017/5/17.
 */

public class EMHelper {
    private Context appContext;
    private static EMHelper instance = null;
    private EMConnectionListener connectionListener;
    private EaseUI easeUI;

    /**
     * 初始化环信IM
     */
    public void initEMChat(Context context) {
        appContext = context;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid, context);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        options.setAppKey(BuildConfig.EASEMOB_APPKEY);
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EMClient.getInstance().init(context, options);
        EMClient.getInstance().setDebugMode(true);

        if (EaseUI.getInstance().init(appContext, options)) {//初始化EaseUi

            easeUI = EaseUI.getInstance();
            globalListener();
            notification();
            setEaseUIProviders();
        }

    }

    /**
     * 设置自定义表情
     */
    protected void setEaseUIProviders() {
        easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data = EmojiconAddGroupData.getData();
                for (EaseEmojicon emojicon : data.getEmojiconList()) {
                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                return null;
            }
        });
    }

    /**
     * 设置通知
     */
    private void notification() {
        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                String title = "";
                if (message.getChatType() == EMMessage.ChatType.Chat) {
                    try {
                        title = message.getStringAttribute("nickName");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                } else if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                    if (message.getTo().equals(SPUtil.loadCompanyGroupId(appContext))) {
                        title = "全国聊天";
                    } else {
                        title = SPUtil.loadCompanyName(appContext);
                    }

                }

                return title;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String content = "";
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                    if (ticker.equals("")) {
                        ticker = "[动态表情]";
                    }
                }
                try {
                    content = message.getStringAttribute("nickName") + ": " + ticker;
                    return content;
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                return content;
            }

            @Override
            public String getNotificationText(EMMessage message) {
                String content = "";
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                    if (ticker.equals("")) {
                        ticker = "[动态表情]";
                    }
                }

                if (message.getChatType() == EMMessage.ChatType.Chat) {
                    content = ticker;
                } else {
                    try {
                        content = message.getStringAttribute("nickName") + ": " + ticker;
                        return content;
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }

                return content;
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click the notification
                Intent intent = new Intent(appContext, Chat2Activity.class);

                intent.putExtra("fromNickName", SPUtil.loadName(appContext));
                intent.putExtra("fromNickUrl", SPUtil.loadHeadPhoto(appContext));

                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { // single chat message
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getFrom());//id
                    intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                    try {
                        intent.putExtra("toNickName", message.getStringAttribute("nickName"));
                        intent.putExtra("toNickUrl", message.getStringAttribute("nickUrl"));
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }

                } else { // group chat message
                    // message.getTo() is the group id
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getTo());
                    if (chatType == EMMessage.ChatType.GroupChat) {
                        if (message.getTo().equals(SPUtil.loadCompanyGroupId(appContext))) {
                            intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                            intent.putExtra("toNickName", "全国聊天");
                            intent.putExtra("toNickUrl", Constant.IMG_COUNTRY);
                        } else {
                            intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                            intent.putExtra("toNickName", SPUtil.loadCompanyName(appContext));
                            intent.putExtra("toNickUrl", Constant.IMG_COMPANY);
                        }

                    } else {
                        intent.putExtra("chatType", EaseConstant.CHATTYPE_CHATROOM);
                    }

                }
                return intent;
            }
        });
    }

    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    protected void globalListener() {
        connectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int errorCode) {
                if (errorCode == EMError.USER_REMOVED) {
                    onIMLoginState(Constant.ACCOUNT_REMOVED);
                } else if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onIMLoginState(Constant.ACCOUNT_CONFLICT);
                } else if (errorCode == EMError.SERVER_SERVICE_RESTRICTED) {
                    onIMLoginState(Constant.ACCOUNT_FORBIDDEN);
                }
            }
        };
        EMClient.getInstance().addConnectionListener(connectionListener);

    }

    private void onIMLoginState(String exception) {
        EMClient.getInstance().logout(true);
        SPUtil.clearIMLoginData(appContext);
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(exception, true);
        appContext.startActivity(intent);
    }

    private String getAppName(int pID, Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    /**
     * 是否已登录
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 更新群组和联系人
     */
    public void updateChatInfo() {
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
    }

    public void removeConnectionListener() {
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    /**
     * 单一实例
     */
    public static EMHelper getInstance() {
        if (instance == null) {
            synchronized (EMHelper.class) {
                if (instance == null) {
                    instance = new EMHelper();
                    return instance;
                }
            }
        }
        return instance;
    }

}
