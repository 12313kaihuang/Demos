package com.example.notificationtest.intercept;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * @author hy
 * @Date 2019/10/29 0029
 * <p>
 * {@link NotificationListenerService}API 18之后才有的
 **/
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationMonitorService extends NotificationListenerService {

    private static final String TAG = NotificationListenerService.class.getSimpleName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //api 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            //包名
            String packageName = sbn.getPackageName();
            // 获取接收消息的抬头
            String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
            // 获取接收消息的内容
            String notificationText = extras.getString(Notification.EXTRA_TEXT);

            Log.d(TAG, " Notification posted " + packageName + "," + notificationTitle + " & " + notificationText);
        }
    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        //api 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            //包名
            String packageName = sbn.getPackageName();
            // 获取接收消息的抬头
            String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
            // 获取接收消息的内容
            String notificationText = extras.getString(Notification.EXTRA_TEXT);

            Log.d(TAG, " Notification posted " + packageName + "," + notificationTitle + " & " + notificationText);
        }
    }
}
