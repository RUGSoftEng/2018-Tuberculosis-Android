package com.rugged.tuberculosisapp.notes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TabNotes extends Fragment {

    public static final String TITLE = "TabNotes";
    private static final int NO_LAST_QUESTION = -1;
    private int questionIdx = NO_LAST_QUESTION;
    private ArrayList<FAQEntry> FAQEntries;

    private LinearLayout entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_notes, container, false);

        // Get list view
        entries = view.findViewById(R.id.FAQEntries);

        // Prepare categories
        FAQEntries = new ArrayList<>();
        retrieveFAQEnries();
        prepareListData(FAQEntries);

        Button askPhysician = view.findViewById(R.id.askPhysicianButton);

        askPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AskPhysician.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        questionIdx = NO_LAST_QUESTION;
    }

    private void threadedToast(int textToToast) {
        final int text = textToToast;
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveFAQEnries() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<ArrayList<FAQEntry>> call = serverAPI.retrieveFAQ(LanguageHelper.getCurrentLocale().toUpperCase());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ArrayList<FAQEntry>> response = call.execute();
                    if (response.code() == 200) { // 200 means successfully retrieved
                        FAQEntries = response.body();
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

    private void prepareListData(ArrayList<FAQEntry> FAQEntries) {
        for (int i = 0; i < FAQEntries.size(); i++) {
            final FAQEntry currentEntry = FAQEntries.get(i);

            final TextView question = new TextView(getActivity());
            question.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            question.setText(currentEntry.getQuestion());
            question.setTextColor(Color.rgb(80,80,80));

            if (i > 0) { // Add distance between questions
                question.setPadding(0, 24, 0, 0);
            }

            question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int elementIdx = entries.indexOfChild(question);

                    if (elementIdx == questionIdx) { // Clicking the same question
                        question.setTypeface(null, Typeface.NORMAL);
                        entries.removeViewAt(elementIdx+1); // remove answer
                        questionIdx = NO_LAST_QUESTION;
                    } else if (questionIdx == NO_LAST_QUESTION) { // No question has been selected previously
                        question.setTypeface(null, Typeface.BOLD);

                        addAnswerToQuestion(currentEntry, elementIdx);

                        questionIdx = elementIdx;
                    } else { // Clicking on a different question
                        question.setTypeface(null, Typeface.BOLD);

                        TextView previousQuestionText = (TextView) entries.getChildAt(questionIdx);
                        previousQuestionText.setTypeface(null, Typeface.NORMAL);

                        entries.removeViewAt(questionIdx+1); // remove answer
                        if (questionIdx < elementIdx) {
                            elementIdx--;
                        }

                        addAnswerToQuestion(currentEntry, elementIdx);

                        questionIdx = elementIdx;
                    }
                }
            });
            entries.addView(question);
        }
    }

    private void addAnswerToQuestion(FAQEntry currentEntry, int elementIdx) {
        final TextView answer = new TextView(getActivity());
        answer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        answer.setText(currentEntry.getAnswer());
        answer.setTextColor(Color.rgb(80,80,80));
        entries.addView(answer, elementIdx+1); // add answer below question
    }

}
