package com.rugged.tuberculosisapp.notes;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AskPhysician extends AppCompatActivity {

    private boolean sent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_physician);

        Button submitQuestion = findViewById(R.id.submitQuestionButton);
        final EditText textQuestion = findViewById(R.id.questionEditText);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textQuestion.getText().toString().length() > 0) { // check if there actually is a question
                    sendQuestion(textQuestion.getText().toString(), textQuestion);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.question_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                        threadedToast(R.string.question_successful);
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
}
