package com.rugged.tuberculosisapp.reminders;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.settings.UserData;

public class ReminderHandler extends BroadcastReceiver {

    private static final int NOTIFICATION = 0;
    private static final int ALARM = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        int reminderType = extras.getInt("EXTRA_TYPE");
        String medName = extras.getString("EXTRA_MED");

        if (reminderType == NOTIFICATION) {
            showNotification(context, medName);
        }

        if (reminderType == ALARM) {
            showAlarm(context, medName);
        }
    }

    public void showNotification(Context context, String medName) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_red_pill);

        String notificationText = context.getString(R.string.notification_text) + ": " + medName;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminders")
                .setSmallIcon(R.drawable.ic_medication)
                .setLargeIcon(icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if (!UserData.getNotificationSilent()) {
            builder.setSound(Uri.parse(UserData.getNotificationSound()));
        }
        /*if (UserData.getNotificationVibrate()) {
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }*/

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    public void showAlarm(Context context, String medName) {
        Intent i = new Intent(context, AlarmActivity.class);
        i.putExtra("EXTRA_MED", medName);
        context.startActivity(i);
    }
}
