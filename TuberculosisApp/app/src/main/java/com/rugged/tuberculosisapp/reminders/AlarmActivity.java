package com.rugged.tuberculosisapp.reminders;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.settings.UserData;

public class AlarmActivity extends AppCompatActivity {
    private static Ringtone alarm;
    private static Vibrator vibrator;

    private static final int TAB_CALENDAR_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        setContentView(R.layout.activity_alarm);

        //Set the alarm title
        TextView textView = findViewById(R.id.alarmTitle);
        Bundle extras = getIntent().getExtras();
        String title = textView.getText().toString();
        if (extras != null) {
            String medName = extras.getString("EXTRA_MED", "Unknown");
            title = textView.getText() + ": " + medName;
        }
        title += "!";
        textView.setText(title);

        //Play sound during alarm
        Uri alarmUri = Uri.parse(UserData.getAlarmSound());
        alarm = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            alarm.setAudioAttributes(audioAttributes);
        } else {
            alarm.setStreamType(AudioManager.STREAM_ALARM);
        }
        if (!UserData.getAlarmSilent()) {
            alarm.play();
        }

        //Vibrate during alarm
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (UserData.getAlarmVibrate() && vibrator != null) {
            vibrator.vibrate(new long[] { 1000, 1000, 1000, 1000, 1000, 1000 }, 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        alarm.stop();
        vibrator.cancel();
        finish();
    }

    public void dismiss(View view) {
        alarm.stop();
        vibrator.cancel();
        finish();
    }

    public void toCalendar(View view) {
        if (MainActivity.isActive) {
            MainActivity.setTab(TAB_CALENDAR_INDEX);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        dismiss(view);
    }
}
