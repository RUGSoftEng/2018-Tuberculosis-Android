package com.rugged.tuberculosisapp.reminders;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;

public class ReminderHandler extends BroadcastReceiver {

    private static final int NOTIFICATION = 0;
    private static final int ALARM = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean switchValue = intent.getBooleanExtra(ReminderTestActivity.EXTRA_SWITCH, false);
        int reminderType = intent.getIntExtra(ReminderTestActivity.EXTRA_TYPE, 0);

        if (!switchValue) {
            if (reminderType == NOTIFICATION) {
                showNotification(context);
            }
            if (reminderType == ALARM) {
                showAlarm(context);
            }
        }
    }

    public void showNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_red_pill);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "reminders")
                .setSmallIcon(R.drawable.ic_pill)
                .setLargeIcon(icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0])
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build());
    }

    public void showAlarm(Context context) {
        Intent i = new Intent(context, AlarmActivity.class);
        context.startActivity(i);
    }
}
