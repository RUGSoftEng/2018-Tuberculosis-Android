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
    private static String videoUrl;
    private List <Quiz> quizList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        // Get intent that started this activity
        Intent intent = getIntent();
        // Get the list of questions
        listView = findViewById(R.id.quizList);
        retrieveQuizList();
        QuizAdapter adapter = new QuizAdapter(this, R.layout.activity_quiz, quizList);

        // Set list adapter
        listView.setAdapter(adapter);
        // TODO Get list from api

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

    public void retrieveQuizList(){
        quizList = new ArrayList<Quiz>();
        ArrayList<String> option = new ArrayList<String>();
        option.add("A virus");
        option.add("A bacteria");
        option.add("Fungi");
        ArrayList<String> options = new ArrayList<String>();
        options.add("It destroys all the bacteria");
        options.add("It moves the bacteria to a different place");
        options.add("It builds a wall around the bacteria");
        ArrayList<String> optionss = new ArrayList<String>();
        optionss.add("Via blood");
        optionss.add("Via food");
        optionss.add("Via air");
        optionss.add("By shaking hands");
        quizList.add(new Quiz("Tuberculosis is caused by:",option,"A bacteria"));
        quizList.add(new Quiz("What does your body do to protect you from the TB?",options,"It builds a wall around the bacteria"));
        quizList.add(new Quiz("How does the bacteria enter your body:",optionss,"Via air"));


    }
}
