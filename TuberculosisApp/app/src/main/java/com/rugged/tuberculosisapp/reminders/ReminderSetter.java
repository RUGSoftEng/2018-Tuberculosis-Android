package com.rugged.tuberculosisapp.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.rugged.tuberculosisapp.medication.Medication;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class ReminderSetter {
    private Context context;

    private ArrayList<Medication> medicationList;

    private static final int NOTIFICATION = 0;
    private static final int ALARM = 1;

    public ReminderSetter(Context context) {
        this.context = context;
    }

    public void cancelReminders() {
        int requestCode = 0;
        for (Medication med : this.medicationList) {
            cancelReminder(NOTIFICATION, med.getName(), requestCode);
            requestCode++;
            cancelReminder(ALARM, med.getName(), requestCode);
            requestCode++;
        }
    }

    public void setReminders(Date date, ArrayList<Medication> meds) {
        //TODO Find better way to have multiple alarms
        if (this.medicationList != null) {
            cancelReminders();
        }

        //Save the current medication list to cancel
        this.medicationList = meds;

        int requestCode = 0;
        for (Medication med : meds) {
            //Initialize time for interval start
            date.setHours(med.getTimeIntervalStart().getHours());
            date.setMinutes(med.getTimeIntervalStart().getMinutes());
            date.setSeconds(0);
            //Calculate time difference to determine if medication has already passed or not
            Date current = new Date();
            long timeDiff = date.getTime() - current.getTime();
            if (!med.isTaken() && timeDiff >= 0) {
                //Set a notification reminder at interval start
                setReminder(date, NOTIFICATION, med.getName(), requestCode);
                //Set an alarm reminder at interval end
                date.setHours(med.getTimeIntervalEnd().getHours());
                date.setMinutes(med.getTimeIntervalEnd().getMinutes());
                setReminder(date, ALARM, med.getName(), requestCode+1);
            }
            requestCode+=2;
        }
    }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        }
    }

    public void cancelReminder(int type, String medName, int requestCode) {
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

        alarmManager.cancel(pendingIntent);
    }
}
