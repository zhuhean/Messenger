package com.zhuhean.messenger.helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zhuhean.messenger.R;
import com.zhuhean.messenger.ui.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public final class NotificationHelper {

    private static final int NOTIFICATION_ID = 201513;
    private static final String CHANNEL_ID = "sms_channel";
    private static final String CHANNEL_NAME = "短信";

    private NotificationHelper() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void showNotification(Context context, String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_chat)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLights(Color.WHITE, 300, 600)
                .setContentTitle(title)
                .setContentText(content);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123
                , new Intent(context, MainActivity.class), PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = getNotificationManager(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void hideNotification(Context context) {
        getNotificationManager(context).cancel(NOTIFICATION_ID);
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

}
