package com.rugged.tuberculosisapp.reminders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        TextView textView = findViewById(R.id.alarmTitle);
        String medName = getIntent().getExtras().getString("EXTRA_MED");
        String title = textView.getText() + " " + medName + "!";
        textView.setText(title);
    }

    public void close(View view) {
        finish();
    }

    public void toCalendar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
