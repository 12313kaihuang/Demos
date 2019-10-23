package com.example.notificationtest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.notificationtest.R;
import com.example.notificationtest.ui.DownLoadTestActivity;

/**
 * @author hy
 * @Date 2019/10/22 0022
 **/
public class DownloadService extends Service {

    private static final int ID = 1112;
    private static final String CHANNEL_ID = "channel_id";
    private NotificationManager mNotificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //适配Android 8.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //注意CHANNEL_ID要与创建Notification时传入的id一致
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "channel1", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);//是否在桌面icon右上角展示小红点
            notificationChannel.setLightColor(Color.RED);//小红点颜色
            notificationChannel.setShowBadge(true);//是否在久按桌面图标时显示此渠道的通知

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int progress = intent.getIntExtra("progress", 0);
        if (progress >= 100) {
            mNotificationManager.cancel(ID);
        } else {
            Notification notification = createNotification("正在下载 " + progress + "%", progress);
            showNotification(notification);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification(Notification notification) {
        mNotificationManager.notify(ID, notification);
    }

    private Notification createNotification(String content, int progress) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        Intent intent = new Intent(this, DownLoadTestActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, flags);

        //noinspection UnnecessaryLocalVariable
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(content)  //设置标题
                .setContentText("剩余时间" + (100 - progress) / 10 + "秒") //设置内容
                .setSmallIcon(R.drawable.ic_notification) //设置图标 小米等部分手机没用，会默认使用app的icon
                .setLargeIcon(largeIcon) //大图标，未设置时使用小图标代替  设置这个在小米手机上也能显示出icon图标
                .setSubText("次要内容") //次要内容
                .setContentInfo("附加内容") //附加内容
                .setNumber(5) //附加数字，等价于 setContentInfo, 为了显示效果用一个不同的字体尺寸显示数字
                .setWhen(System.currentTimeMillis()) //设置时间
                .setShowWhen(true) //是否显示时间  api 17 被添加
                .setUsesChronometer(true) //设置是否显示时钟表示时间(count up)
                //当使用了 setSubText() 后，进度条将不显示,api 24 之后，setSubText() 不再影响进度条
                .setProgress(100, progress, false)  //设置进度条 最大值/当前值/是否是不明确的进度条
                .setTicker("this is ticker")  //状态栏摘要  在 api 21 后不再显示，仅用于辅助服务。

                .setOnlyAlertOnce(true)  //设置提醒只执行一次
                //.setAutoCancel(true)  //设置自动取消 需要同时设置了 setContentIntent() 才有效
                //.setContentIntent(pendingIntent)
                //.setOngoing(true) //用户不能取消，效果类似FLAG_NO_CLEAR,用户点击通知且设置了自动取消时会被删除

                .setDefaults(Notification.DEFAULT_SOUND)  //设置默认声音提醒
                //.setDefaults(Notification.DEFAULT_LIGHTS) //添加默认呼吸灯提醒

                .setContentIntent(pi)  //点击事件
                .build();

        return notification;
    }
}
