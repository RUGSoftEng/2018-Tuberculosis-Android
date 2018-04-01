package com.rugged.tuberculosisapp.reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm", "Alarm triggered");
        boolean switchValue = intent.getBooleanExtra(ReminderTestActivity.EXTRA_SWITCH, false);
        Log.d("Pill taken", String.valueOf(switchValue));
        if (switchValue) {
            Intent i = new Intent(context, AlarmActivity.class);
            context.startActivity(i);
        }
    }
}
