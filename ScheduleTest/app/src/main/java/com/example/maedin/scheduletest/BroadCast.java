package com.example.maedin.scheduletest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-12-02.
 */

public class BroadCast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Notification 구현
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.alarm)
                .setWhen(System.currentTimeMillis())
                .setTicker("Alarm")
                .setContentTitle("Alarm")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(111, builder.build());

        Log.d("Alarm","onReceive");
    }
}
