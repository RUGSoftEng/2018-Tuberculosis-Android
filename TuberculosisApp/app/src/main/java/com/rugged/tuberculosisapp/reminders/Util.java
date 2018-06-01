package com.rugged.tuberculosisapp.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Util {

    //Delay between calls in milliseconds
    public static final int DELAY_BETWEEN_CALLS = 15 * 60 * 1000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context, int delay) {
        ComponentName serviceComponent = new ComponentName(context, ReminderService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(delay);
        builder.setOverrideDeadline(delay);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require un-metered network
        builder.setRequiresCharging(false); // we don't care if the device is charging or not

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public static void setAlarm(Context context) {
        Intent alarmIntent = new Intent(context, ReminderUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (manager != null) {
            manager.cancel(pendingIntent);
            //Set one time check after 2 minutes, then repeat every x minutes
            manager.set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent);
            manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), DELAY_BETWEEN_CALLS, pendingIntent);
        }
    }

}
