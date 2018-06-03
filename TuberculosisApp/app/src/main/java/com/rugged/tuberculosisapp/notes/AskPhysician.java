package com.rugged.tuberculosisapp.notes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AskPhysician extends AppCompatActivity {

    private boolean sent = false;
    private static int ID_OFFSET = 100;
    private ArrayList<QuestionToPhysician> askedQuestions;

    private LinearLayout questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_physician);

        // Get list view
        questions = findViewById(R.id.askedQuestions);

        // Prepare categories
        askedQuestions = new ArrayList<>();
        retrieveAskedQuestions();
        prepareListData(askedQuestions);

        Button submitQuestion = findViewById(R.id.submitQuestionButton);
        final EditText textQuestion = findViewById(R.id.questionEditText);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textQuestion.getText().toString().length() > 0) { // check if there actually is a question
                    sendQuestion(textQuestion.getText().toString(), textQuestion);
                    refreshAskedQuestions();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.question_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void retrieveAskedQuestions() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<ArrayList<QuestionToPhysician>> call = serverAPI.retrieveAskedQuestions(UserData.getIdentification().getId(),
                UserData.getIdentification().getToken());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ArrayList<QuestionToPhysician>> response = call.execute();
                    if (response.code() == 200) { // 200 means successfully retrieved
                        askedQuestions = response.body();
                    } else {
                        threadedToast(R.string.retrieving_faq_failed);
                    }
                } catch (IOException e) {
                    // TODO add more advanced exception handling
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareListData(ArrayList<QuestionToPhysician> askedQuestions) {
        for (int i = 0; i < askedQuestions.size(); i++) {
            QuestionToPhysician currentQuestion = askedQuestions.get(i);

            LinearLayout.LayoutParams holderParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout holder = new LinearLayout(this);
            holder.setLayoutParams(holderParam);
            holder.setOrientation(LinearLayout.HORIZONTAL);

            //******************************fill holder********************************************

            LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

            TextView question = new TextView(this);
            question.setLayoutParams(questionParam);
            question.setText(currentQuestion.getQuestion());
            question.setTextColor(Color.rgb(80,80,80));
            holder.addView(question);


            LinearLayout.LayoutParams iconParam = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.edit_icon_width),
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

            ImageButton edit = new ImageButton(this);
            edit.setImageResource(android.R.drawable.ic_menu_edit);
            edit.setLayoutParams(iconParam);
            //edit.setOnClickListener(ClickListener);
            holder.addView(edit);

            ImageButton delete = new ImageButton(this);
            delete.setImageResource(android.R.drawable.ic_delete);
            delete.setLayoutParams(iconParam);
            //edit.setOnClickListener(ClickListener); // Here we need a DELETE API call
            holder.addView(delete);


            //******************************fill holder********************************************

            if (i > 0) { // Add distance between questions
                holder.setPadding(0, 24, 0, 0);
            }
            questions.addView(holder);
        }
    }

    private void sendQuestion(final String question, final EditText editText) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<ResponseBody> call = serverAPI.askPhysician(UserData.getIdentification().getId(),
                UserData.getIdentification().getToken(), new QuestionToPhysician(question));

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = call.execute();
                    if (response.code() == 201) { // 201 means successfully created
                        threadedToast(R.string.question_successful);
                        sent = true;
                    } else {
                        threadedToast(R.string.question_failed);
                        sent = false;
                    }
                } catch (IOException e) {
                    // TODO add more advanced exception handling
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(sent) editText.setText("");
    }

    private void threadedToast(int textToToast) {
        final int text = textToToast;
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override // To clear focus from EditText when tapping outside of the EditText
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    private void refreshAskedQuestions() {
        questions.removeAllViews();
        retrieveAskedQuestions();
        prepareListData(askedQuestions);
    }
}
