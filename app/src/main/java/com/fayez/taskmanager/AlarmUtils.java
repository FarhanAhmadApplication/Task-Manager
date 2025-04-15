package com.fayez.taskmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

public class AlarmUtils {
    AlarmManager alarmManager;
    AlarmUtils(Context context){
        alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    void setAlarm(long triggerinmillis, PendingIntent pendingIntent){
        alarmManager.set(AlarmManager.RTC_WAKEUP,triggerinmillis,pendingIntent);

    }
    void cancelAlarm(PendingIntent pendingIntent){
        alarmManager.cancel(pendingIntent);
    }
}