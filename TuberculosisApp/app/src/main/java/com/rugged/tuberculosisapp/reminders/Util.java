package com.rugged.tuberculosisapp.reminders;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Util {

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

}
