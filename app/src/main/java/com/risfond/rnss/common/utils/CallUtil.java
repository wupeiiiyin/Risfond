package com.risfond.rnss.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallUtil {
    public static void call(Context context, String callNum) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
        intent.setData(Uri.parse("tel:" + callNum));
        context.startActivity(intent);
    }
    public static void mail(Context context, String mail) {
        Uri uri = Uri.parse(mail);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        context.startActivity(it);
    }

}
