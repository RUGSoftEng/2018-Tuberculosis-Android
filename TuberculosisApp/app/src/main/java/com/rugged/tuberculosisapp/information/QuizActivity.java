package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    static final int nrOfQuestions = 4;
    boolean[] correct = new boolean[nrOfQuestions];
    private static Urls videoUrl;
    private List <Quiz> quizList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        // Get intent that started this activity
        Intent intent = getIntent();
        // Set video url
        videoUrl = (Urls) intent.getSerializableExtra(VideoGridActivity.VIDEO_URL_MESSAGE);
        // Get the list of questions
        quizList = videoUrl.getQuiz();
        listView = findViewById(R.id.quizList);
        QuizAdapter adapter = new QuizAdapter(this, R.layout.activity_quiz, quizList);

        // Set list adapter
        listView.setAdapter(adapter);
        // TODO Get list from api

        // Initialize youtube player fragment with the correct video url
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.videoViewFragment);

        youtubeFragment.initialize(TabInformation.DEVELOPER_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // Cue video (can also choose to instantly play it with .playVideo)
                        youTubePlayer.cueVideo(videoUrl.getUrl());
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        //TODO: error handling
                    }
                });

        Arrays.fill(correct, false);
    }


}
