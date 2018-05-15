package com.rugged.tuberculosisapp.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import static com.rugged.tuberculosisapp.MainActivity.ENABLE_API;

public class TabNotes extends Fragment {

    public static final String TITLE = "TabNotes";
    private static final int NO_LAST_QUESTION = -1;
    private int questionIdx = NO_LAST_QUESTION;

    private LinearLayout entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_notes, container, false);

        // Get list view
        entries = view.findViewById(R.id.FAQEntries);

        // Prepare categories
        prepareListData();

        Button submitQuestion = view.findViewById(R.id.submitQuestionButton);
        final EditText textQuestion = view.findViewById(R.id.questionEditText);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textQuestion.getText().toString().length() > 0) { // check if there actually is a question
                    sendQuestion(textQuestion.getText().toString(), textQuestion);
                } else {
                    Toast.makeText(getActivity(), R.string.question_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        questionIdx = NO_LAST_QUESTION;
    }

    private void sendQuestion(String question, final EditText editText) {
        if (ENABLE_API) {
            Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);

            final Call<ResponseBody> call = serverAPI.askPhysician(UserData.getIdentification().getId(),
                    UserData.getIdentification().getToken(), new QuestionToPhysician(question));

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<ResponseBody> response = call.execute();
                        System.out.println("id = " + UserData.getIdentification().getId() +
                                " token = " + UserData.getIdentification().getToken());
                        System.out.println("response from server = " + response.code());
                        if (response.code() == 201) { // 201 means successfully created
                            editText.setText("");
                            getActivity().runOnUiThread(new Runnable() { // To display a toast in a thread you need this
                                public void run() {
                                    Toast.makeText(getActivity(), R.string.question_successful, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), R.string.question_failed, Toast.LENGTH_SHORT).show();
                                }
                            });
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
        } else {
            Toast.makeText(getActivity(), "API is disabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void prepareListData() {
        int numOfEntries = 10; //TODO get entries from API call

        for (int i = 0; i < numOfEntries; i++) {
            final TextView question = new TextView(getActivity());
            question.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            question.setText(getText(R.string.lorem_ipsum)); //TODO get strings from API call
            if (i > 0) { // Add distance between questions
                question.setPadding(0, 24, 0, 0);
            }
            question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int elementIdx = entries.indexOfChild(question);

                    if (elementIdx == questionIdx) { // Clicking the same question
                        question.setText(R.string.lorem_ipsum);
                        entries.removeViewAt(elementIdx+1);
                        questionIdx = NO_LAST_QUESTION;
                    } else if (questionIdx == NO_LAST_QUESTION) { // No question has been selected previously
                        question.setText("I am clicked yo");

                        final TextView answer = new TextView(getActivity());
                        answer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        answer.setText("testy answer");
                        entries.addView(answer, elementIdx+1);

                        questionIdx = elementIdx;
                    } else { // Clicking on a different question
                        question.setText("I am clicked yo2");
                        TextView previousQuestionText = (TextView) entries.getChildAt(questionIdx);
                        previousQuestionText.setText(R.string.lorem_ipsum);

                        entries.removeViewAt(questionIdx+1);
                        if (questionIdx < elementIdx) {
                            elementIdx--;
                        }

                        final TextView answer = new TextView(getActivity());
                        answer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        answer.setText("testy answer2");
                        entries.addView(answer, elementIdx+1);

                        questionIdx = elementIdx;
                    }
                }
            });
            entries.addView(question);
        }
    }
}
