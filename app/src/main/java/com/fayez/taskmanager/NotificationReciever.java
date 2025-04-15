package com.fayez.taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId=intent.getStringExtra("CHANNELID");
        String name=intent.getStringExtra("Task_Name");
        String notificationtitle="A New Task has added";
        Intent i=new Intent(context,MainActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        if(channelId.equals("NewTaskCreated")) {
            notificationtitle = "A New Task " + name + " has added";
        }
        NotificationManager nm=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.task)
                    .setChannelId(channelId).setContentText(notificationtitle)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        nm.notify(channelId.hashCode(),notification);
    }
}

