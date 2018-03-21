package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class VideoGridActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_grid);

        // Get list view
        gridView = findViewById(R.id.videoGrid);

        // Get intent that started this activity
        Intent intent = getIntent();

        // Set grid's category title
        String title = intent.getStringExtra(TabInformation.TITLE_MESSAGE);
        TextView textView = findViewById(R.id.videoCategoryTitle);
        textView.setText(title);

        // TODO: implement a grid adapter that populates grid with clickable video thumbnails (YouTube API)
        // To get video URL list: intent.getStringArrayListExtra(TabInformation.VIDEO_URLS_MESSAGE)..

        ArrayList<String> videoUrls = intent.getStringArrayListExtra(TabInformation.VIDEO_URLS_MESSAGE);
        VideoGridAdapter adapter = new VideoGridAdapter(this, R.layout.thumbnail_holder, videoUrls);

        // Get video grid view
        gridView.setAdapter(adapter);
    }
}
