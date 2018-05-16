package com.rugged.tuberculosisapp.reminders;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;

public class AlarmActivity extends AppCompatActivity {
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        TextView textView = findViewById(R.id.alarmTitle);
        String medName = getIntent().getExtras().getString("EXTRA_MED");
        String title = textView.getText() + " " + medName + "!";
        textView.setText(title);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        this.r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        r.stop();
        finish();
    }

    public void close(View view) {
        r.stop();
        finish();
    }

    public void toCalendar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        close(view);
    }
}
