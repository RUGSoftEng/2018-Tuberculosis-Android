package com.rugged.tuberculosisapp.reminders;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.settings.UserData;

public class AlarmActivity extends AppCompatActivity {
    private static Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Uri alarmUri = UserData.getAlarmSound();
        r = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        r.play();

        //Vibrate during alarm
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(400);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        r.stop();
        finish();
    }

    public void dismiss(View view) {
        r.stop();
        finish();
    }

    public void toCalendar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        dismiss(view);
    }
}
