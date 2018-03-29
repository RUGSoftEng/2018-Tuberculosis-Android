package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.rugged.tuberculosisapp.R;

public class QuizActivity extends AppCompatActivity {

    private static String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get intent that started this activity
        Intent intent = getIntent();

        // Set video url
        videoUrl = intent.getStringExtra(VideoGridActivity.VIDEO_URL_MESSAGE);

        // Initialize youtube player fragment with the correct video url
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.videoViewFragment);

        youtubeFragment.initialize(TabInformation.DEVELOPER_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // Cue video (can also choose to instantly play it with .playVideo)
                        youTubePlayer.cueVideo(videoUrl);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        //TODO: error handling
                    }
                });
    }
}
