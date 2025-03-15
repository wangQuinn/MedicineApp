package com.example.booklibraryapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context, "medication_reminder_channel")
                .setContentTitle("Medication Reminder")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("It's time to take your medication!")
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setAutoCancel(true)
                .build();

        Log.d("notificaion working!", "notification working!");

        notificationManager.notify(1, notification); // 1 is the notification ID
    }
}
