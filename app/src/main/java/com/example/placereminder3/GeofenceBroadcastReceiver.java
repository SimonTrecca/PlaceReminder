package com.example.placereminder3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private final String channelId = "PlaceReminderChannel";
    private final String channelName="PlaceReminder_channelname";
    private int notificationCount = 0;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        int importance= NotificationManager.IMPORTANCE_DEFAULT;

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel= notificationManager.getNotificationChannel(channelId);
        if(channel==null){
            channel=new NotificationChannel(channelId,channelName,importance);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle("Geofencer notification")
                .setContentText("You're close to a placemark")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(notificationCount, builder.build());
        notificationCount++;
    }

}