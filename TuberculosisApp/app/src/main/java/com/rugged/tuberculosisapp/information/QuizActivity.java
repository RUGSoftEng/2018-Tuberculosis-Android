package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    static final int nrOfQuestions = 4;
    boolean[] correct = new boolean[nrOfQuestions];
    private static String videoUrl;
    private List<Quiz> quizList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get intent that started this activity
        Intent intent = getIntent();

        // Get the list of questions
        listView = findViewById(R.id.quizList);
        quizList = (ArrayList<Quiz>) intent.getSerializableExtra(VideoGridActivity.QUIZZES_MESSAGE);
        if (quizList != null) {
            for (Quiz quiz : quizList) {
                ArrayList<String> answers = quiz.getAnswers();
                quiz.setCorrectAnswer(answers.get(0));
                Collections.shuffle(answers);
            }

            QuizAdapter adapter = new QuizAdapter(this, R.layout.activity_quiz, quizList);

            // Set list adapter
            listView.setAdapter(adapter);
        }

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

        Arrays.fill(correct, false);
    }


}