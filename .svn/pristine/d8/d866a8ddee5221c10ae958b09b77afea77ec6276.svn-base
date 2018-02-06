package com.risfond.rnss.common.share;

import android.content.Context;

import com.risfond.rnss.R;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Abbott on 2017/7/26.
 */

public class ShareUtil {

    public static void showShare(Context context, String title, String content, String imgUrl, String webUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(webUrl);
        oks.setImageUrl(imgUrl); //分享缩略图url
        // site是分享此内容的网站名称
        oks.setSite(context.getString(R.string.app_name));

        // 启动分享GUI
        oks.show(context);
    }

}
