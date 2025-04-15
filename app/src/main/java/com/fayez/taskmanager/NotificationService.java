package com.fayez.taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

public class NotificationService extends Service {
    private static final String CHANNEL_ID1="NewTaskCreated";
    private static final int NOTIFICATION_ID1=101;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name=intent.getStringExtra("TASK_NAME");
        MainActivity2 mainActivity2=new MainActivity2();
        Context context=mainActivity2.getApplicationContext();
        NotificationManager nm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification=new Notification.Builder(context)
                    .setSmallIcon(R.drawable.task)
                    .setCustomContentView(getRemoteView1(context,name))
                    .setChannelId(CHANNEL_ID1)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID1,"NewTaskCreated",NotificationManager.IMPORTANCE_HIGH));
        }
        else {
            notification=new Notification.Builder(context)
                    .setSmallIcon(R.drawable.task)
                    .setCustomContentView(getRemoteView1(context,name))
                    .build();
        }
        nm.notify(NOTIFICATION_ID1,notification);
        return super.onStartCommand(intent, flags, startId);
    }
    RemoteViews getRemoteView1(Context context, String name){
        RemoteViews customView=new RemoteViews(context.getPackageName(),R.layout.custom_notification_layout1);
        Bitmap drawable= BitmapFactory.decodeResource(context.getResources(),R.id.large_icon1);
        customView.setImageViewBitmap(R.id.large_icon1,drawable);
        customView.setTextViewText(R.id.textView1,"A New Task "+ name+" is Created");
        return customView;
    }
}