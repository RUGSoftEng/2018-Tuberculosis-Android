package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoGridActivity extends AppCompatActivity {

    private GridView gridView;
    private VideoGridAdapter adapter;

    public static final String VIDEO_URL_MESSAGE = "com.rugged.tuberculosisapp.VIDEO_URL";
    public static final String QUIZZES_MESSAGE = "com.rugged.tuberculosisapp.QUIZZES";

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

        final HashMap<String, ArrayList<Quiz>> videos = (HashMap<String, ArrayList<Quiz>>) intent.getSerializableExtra(TabInformation.VIDEO_URLS_MESSAGE);
        ArrayList<String> videoUrls = new ArrayList<>(videos.keySet());
        adapter = new VideoGridAdapter(getApplicationContext(), videoUrls);

        // Get video grid view
        gridView.setAdapter(adapter);

        // OnClickListener that starts the quiz activity with the corresponding video url
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String videoUrl = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(VideoGridActivity.this, QuizActivity.class);
                intent.putExtra(VIDEO_URL_MESSAGE, videoUrl);
                intent.putExtra(QUIZZES_MESSAGE, videos.get(videoUrl));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release YoutubeThumbnailLoaders when they are no longer needed
        adapter.releaseLoaders();
    }

}