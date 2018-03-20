package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

public class VideoGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_grid);

        // Get intent that started this activity
        Intent intent = getIntent();

        // Set grid's category title
        String title = intent.getStringExtra(TabInformation.TITLE_MESSAGE);
        TextView textView = findViewById(R.id.videoCategoryTitle);
        textView.setText(title);

        // TODO: implement a grid adapter that populates grid with clickable video thumbnails (YouTube API)
        // To get video URL list: intent.getStringArrayListExtra(TabInformation.VIDEO_URLS_MESSAGE)..
    }
}
