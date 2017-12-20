package com.risfond.rnss.common.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Abbott on 2017/9/4.
 */

public class EventBusUtil {

    /**
     * 注册
     *
     * @param o
     */
    public static void registerEventBus(Object o) {
        if (!EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().register(o);//订阅
        }
    }

    public static boolean isRegisterEventBus(Object o) {
        return EventBus.getDefault().isRegistered(o);
    }

    public static void unRegisterEventBus(Object o) {
        EventBus.getDefault().unregister(o);//解除订阅
    }
}
