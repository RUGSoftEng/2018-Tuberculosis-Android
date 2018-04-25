package com.rugged.tuberculosisapp.reminders;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;

import java.util.Calendar;

public class ReminderTestActivity extends AppCompatActivity {

    public static final String EXTRA_SWITCH = "com.example.test.EXTRA_SWITCH";
    public static final String EXTRA_TYPE = "com.example.test.EXTRA_TYPE";

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    private Calendar c = Calendar.getInstance();
    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;
    private int second;

    private static final int NOTIFICATION = 0;
    private static final int ALARM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_test);

        initTime();
        updateDateButton(year, month, dayOfMonth);
        updateTimeButton(hourOfDay, minute);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                updateDateButton(year, month, day);
            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                updateTimeButton(hourOfDay, minute);
            }
        };
    }

    public void initTime() {
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        this.hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.second = c.get(Calendar.SECOND);
    }

    public void updateDateButton(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        Button db = findViewById(R.id.dateButton);
        db.setText(date);
    }

    public void updateTimeButton(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        String time = hourOfDay + ":" + String.format("%02d", minute);
        Button tb = findViewById(R.id.timeButton);
        tb.setText(time);
    }

    public void setDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onDateSetListener,
                year,month,dayOfMonth);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void setTime(View view) {
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onTimeSetListener,
                hourOfDay, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void setNotification(View view) {
        setReminder(NOTIFICATION);
    }

    public void setAlarm(View view) {
        setReminder(ALARM);
    }

    public void showReminderToast(int type) {
        Button tb = findViewById(R.id.timeButton);
        Button db = findViewById(R.id.dateButton);
        CharSequence text = "set to " + db.getText() + " at " + tb.getText();
        if (type == NOTIFICATION) {
            text = "Notification " + text;
        } else {
            text = "Alarm " + text;
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setReminder(int type) {
        Intent intent = new Intent(this, ReminderHandler.class);

        Switch s = findViewById(R.id.pillSwitch);
        boolean switchValue = s.isChecked();
        intent.putExtra(EXTRA_SWITCH, switchValue);
        if (type == NOTIFICATION) {
            intent.putExtra(EXTRA_TYPE, NOTIFICATION);
        }
        if (type == ALARM) {
            intent.putExtra(EXTRA_TYPE, ALARM);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderTestActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        assert alarmManager != null;

        Calendar alarmCal = Calendar.getInstance();
        alarmCal.set(year, month, dayOfMonth, hourOfDay, minute, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pendingIntent);

        showReminderToast(type);
    }
}
