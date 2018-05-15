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
                        if (response.code() == 201 || response.code() == 405) { // 201 means successfully created TODO: remove 405 when we are allowed to put notes
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
            TextView question = new TextView(getActivity());
            question.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            question.setText(getText(R.string.lorem_ipsum)); //TODO get strings from API call
            entries.addView(question);
        }
    }
}
