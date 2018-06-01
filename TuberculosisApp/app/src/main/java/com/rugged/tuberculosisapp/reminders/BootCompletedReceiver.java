package com.rugged.tuberculosisapp.reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.M)
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.scheduleJob(context, 2000);
    }

}
