package com.rugged.tuberculosisapp.reminders;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.rugged.tuberculosisapp.medication.Medication;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Niek on 15/05/2018.
 */

public class ReminderSetter {
    private Context context;

    private static final int NOTIFICATION = 0;
    private static final int ALARM = 1;
    private static final int HOUR_IN_MILLISECONDS = 3600000;
    private static final int MINUTE_IN_MILLISECONDS = 60000;

    public ReminderSetter(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setReminders(Date date, ArrayList<Medication> meds) {
        //TODO cancel previously set alarms and find better way to have multiple alarms
        int requestCode = 0;
        for (Medication med : meds) {
            Date current = new Date();
            date.setHours(med.getTime().getHours());
            date.setMinutes(med.getTime().getMinutes());
            date.setSeconds(0);
            current.toString();
            date.toString();
            long timeDiff = date.getTime() - current.getTime();

            if (!med.isTaken() && timeDiff >= 0) {
                setReminder(date, NOTIFICATION, med.getName(), requestCode);
                requestCode++;
                long timeForAlarm = date.getTime() + MINUTE_IN_MILLISECONDS;
                date.setTime(timeForAlarm);
                setReminder(date, ALARM, med.getName(), requestCode);
                requestCode++;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setReminder(Date date, int type, String medName, int requestCode) {
        Intent intent = new Intent(context, ReminderHandler.class);

        if (type == NOTIFICATION) {
            intent.putExtra("EXTRA_TYPE", NOTIFICATION);
        }
        if (type == ALARM) {
            intent.putExtra("EXTRA_TYPE", ALARM);
        }
        intent.putExtra("EXTRA_MED", medName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        assert alarmManager != null;

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }
}
