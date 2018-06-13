package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.tuberculosisapp.R;

import java.util.List;

public class QuizAdapter extends ArrayAdapter<Quiz> {

    private final Context mContext;

    QuizAdapter(Context context,int resourceId, List<Quiz> quizList) {
        super(context, resourceId, quizList);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Quiz quiz = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_question, parent, false);
        }

        TextView quizQuestion = (TextView) convertView.findViewById(R.id.quizQuestion);
        RadioGroup quizOptions = (RadioGroup) convertView.findViewById(R.id.quizOptions);

        if (quiz != null) {
            quizQuestion.setText(quiz.getQuestion());
            List<String> answers = quiz.getAnswers();
            quizOptions.removeAllViews();

            if (answers != null) {
                final String rightAnswer = quiz.getCorrectAnswer();

                for (String option : answers) {
                    RadioButton button = new RadioButton(mContext);
                    button.setText(option);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioButton button = (RadioButton) v;
                            if (button.getText().equals(rightAnswer)) {
                                Toast.makeText(getContext(), getContext().getResources().getString(R.string.correct_answer), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), getContext().getResources().getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    quizOptions.addView(button);
                }
            }
        }

        return convertView;
    }

}