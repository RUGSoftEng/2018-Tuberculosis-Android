package com.rugged.tuberculosisapp.reminders;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Util {

    //Delay between calls in milliseconds
    private static final int DELAY_BETWEEN_CALLS = 15 * 60 * 1000;

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, ReminderService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(DELAY_BETWEEN_CALLS);
        builder.setOverrideDeadline(DELAY_BETWEEN_CALLS);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require un-metered network
        builder.setRequiresCharging(false); // we don't care if the device is charging or not

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

}
