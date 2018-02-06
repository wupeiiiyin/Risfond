package com.risfond.rnss.common.em;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.ResumeSearch;

/**
 * Created by Abbott on 2017/9/19.
 */

public class EMUtil {


    /**
     * 发送简历卡片信息
     * @param context
     * @param chatType
     * @param toChatId
     * @param toNickName
     * @param toNickUrl
     * @param content
     * @param resumeSearch
     */
    public static void sendResumeMessage(Context context, int chatType, String toChatId, String toNickName, String toNickUrl,
                                         String content, ResumeSearch resumeSearch) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatId);
        // 增加自己特定的属性
        message.setAttribute(EaseConstant.MESSAGE_TXT_TYPE, EaseConstant.MESSAGE_TXT_TYPE_RESUME_VALUE);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatId,
                EaseCommonUtils.getConversationType(chatType), true);
        // 增加自己特定的属性
        message.setAttribute("nickName", SPUtil.loadName(context));//自己的昵称
        message.setAttribute("nickUrl", SPUtil.loadHeadPhoto(context));//自己的头像
        message.setAttribute("resumeData", JsonUtil.toJson(resumeSearch));

        conversation.setExtField(toNickName + " " + toNickUrl);//好友的昵称,好友的头像

        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送职位卡片信息
     * @param context
     * @param chatType
     * @param toChatId
     * @param toNickName
     * @param toNickUrl
     * @param content
     * @param resumeSearch
     */
    public static void sendPositionMessage(Context context, int chatType, String toChatId, String toNickName, String toNickUrl,
                                         String content, ResumeSearch resumeSearch) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatId);
        // 增加自己特定的属性
        message.setAttribute(EaseConstant.MESSAGE_TXT_TYPE, EaseConstant.MESSAGE_TXT_TYPE_POSITION_VALUE);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatId,
                EaseCommonUtils.getConversationType(chatType), true);
        // 增加自己特定的属性
        message.setAttribute("nickName", SPUtil.loadName(context));//自己的昵称
        message.setAttribute("nickUrl", SPUtil.loadHeadPhoto(context));//自己的头像
        message.setAttribute("PositionData", JsonUtil.toJson(resumeSearch));//自己的头像

        conversation.setExtField(toNickName + " " + toNickUrl);//好友的昵称,好友的头像

        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
    }

}
