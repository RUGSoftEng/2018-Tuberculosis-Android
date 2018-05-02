package com.rugged.tuberculosisapp.information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    ArrayList<String> array;
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
       boolean checked = ((RadioButton) view).isChecked();
       switch (view.getId()) {
           case R.id.correct_answer1:
               if (checked)
                   correct[0] = true;
                   break;
           case R.id.correct_answer2:
               if (checked)
                   correct[1] = true;
                   break;
           case R.id.correct_answer3:
               if (checked)
                   correct[2] = true;
                   break;
           case R.id.correct_answer4:
               if (checked)
                   correct[3] = true;
                   break;
           case R.id.wrong_answer1:
               if (checked)
                   correct[0] = false;
                   break;
           case R.id.wrong_answer2:
               if (checked)
                   correct[1] = false;
                   break;
           case R.id.wrong_answer3:
               if (checked)
                   correct[2] = false;
                   break;
           case R.id.wrong_answer4:
               if (checked)
                   correct[3] = false;
                   break;
       }
    }

    public void submitQuiz(View view){
        array = new ArrayList<String>();
        for (int i = 0; i < nrOfQuestions; i++) {
            if (correct[i]) {
                array.add("Your answer for question " + (i+1) + " is correct!");
            } else {
                array.add("Your answer for question " + (i+1) + " is wrong.");
            }
        }

        QuizFragment quizfragment = new QuizFragment();
        quizfragment.setChecked(array);
        quizfragment.show(getFragmentManager(), "QuizFragment");
    }
    public void retrieveQuizList(){
        quizList = new ArrayList<Quiz>();
        ArrayList<String> options = new ArrayList<String>();
        options.add("Answer 1");
        options.add("Answer 2");
        options.add("Answer 3");
        quizList.add(new Quiz("Question 1",options,"Answer 1"));
    }
}
