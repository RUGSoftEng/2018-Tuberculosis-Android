package com.rugged.tuberculosisapp.information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizAdapter extends ArrayAdapter<Quiz> {
    private final Context mContext;
    private final List<Quiz> mquizList;
    private final HashMap<Quiz, Integer> mIdMap = new HashMap<>();

    QuizAdapter(Context context,int resourceId, List<Quiz> quizList) {
        super(context, resourceId, quizList);
        this.mContext = context;
        this.mquizList = quizList;
        int i = 0;
        for (Quiz quiz : quizList) {
            mIdMap.put(quiz, i);
            i++;
        }
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Quiz quiz = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_question, parent, false);
        }

        TextView quizQuestion = (TextView) convertView.findViewById(R.id.quizQuestion);
        RadioGroup quizOptions  = (RadioGroup) convertView.findViewById(R.id.quizOptions);
        if (quiz != null) {
            int i = 0;
            for(Quiz question: mquizList) {
                quizQuestion.setText(question.getQuestion());
                List<String> options = question.getOptions();
                if (options != null) {
                    for (String option : options) {
                        RadioButton button = new RadioButton(mContext);
                        button.setText(option);
                        button.setId(i);
                        quizOptions.addView(button);
                        i++;
                    }
                }
            }
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return mIdMap.get(getItem(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}