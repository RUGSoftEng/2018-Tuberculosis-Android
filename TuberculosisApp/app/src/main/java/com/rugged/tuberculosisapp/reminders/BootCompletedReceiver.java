package com.rugged.tuberculosisapp.reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Delay is the initial delay here
            Util.scheduleJob(context, 0);
        } else {
            Util.scheduleAlarm(context);
        }
    }

}
