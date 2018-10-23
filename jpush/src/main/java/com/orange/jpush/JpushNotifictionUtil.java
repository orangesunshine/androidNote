package com.orange.jpush;

import android.content.Context;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class JpushNotifictionUtil {
    /**
     * 自定义通知栏
     * @param context
     * @param number     自定义样式编号
     * @param layoutId   布局Id
     * @param iconTipId  指定最顶层状态栏小图标
     * @param iconShowId 指定下拉状态栏时显示的通知图标
     */
    public static void customPushNotification(Context context, int number, int layoutId, int iconTipId, int iconShowId) {
        // 指定定制的 Notification Layout
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(context, layoutId, R.id.custom_push_notification_icon, R.id.custom_push_notification_title, R.id.custom_push_notification_text);
        // 指定最顶层状态栏小图标
        builder.statusBarDrawable = iconTipId;
        // 指定下拉状态栏时显示的通知图标
        builder.layoutIconDrawable = iconShowId;
        JPushInterface.setPushNotificationBuilder(number, builder);
    }
}
