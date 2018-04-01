package com.rugged.tuberculosisapp.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rugged.tuberculosisapp.R;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void close(View view) {
        finish();
    }
}
