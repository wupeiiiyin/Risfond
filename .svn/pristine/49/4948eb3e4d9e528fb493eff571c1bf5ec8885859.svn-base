package com.hyphenate.easeui.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;

/**
 * Created by Abbott on 2017/6/2.
 */

public class CMDBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ME", "进入到广播啦");
        if (intent != null) {
            //获取cmd message对象
            String msgId = intent.getStringExtra("msgid");
            EMMessage message = intent.getParcelableExtra("message");
            //获取消息body
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String aciton = cmdMsgBody.action();//获取自定义action
            System.out.println();
        }
    }

}
