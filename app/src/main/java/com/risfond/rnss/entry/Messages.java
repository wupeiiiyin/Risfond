package com.risfond.rnss.entry;

/**
 * Created by Abbott on 2017/5/16.
 */

public class Messages {
    private int chatType;
    private String groupFrom;
    private String nickUrl;
    private String nickName;
    private String easeMobAccount;
    private String content;
    private String time;
    private int tip;

    public int getChatType() {
        return chatType;
    }

    public String getGroupFrom() {
        return groupFrom;
    }

    public void setGroupFrom(String groupFrom) {
        this.groupFrom = groupFrom;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getEaseMobAccount() {
        return easeMobAccount;
    }

    public void setEaseMobAccount(String easeMobAccount) {
        this.easeMobAccount = easeMobAccount;
    }

    public String getNickUrl() {
        return nickUrl;
    }

    public void setNickUrl(String nickUrl) {
        this.nickUrl = nickUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }
}
