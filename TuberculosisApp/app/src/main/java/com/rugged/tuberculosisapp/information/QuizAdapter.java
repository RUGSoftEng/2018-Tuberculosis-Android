package com.rugged.tuberculosisapp.information;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Quiz quiz = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_quiz, parent, false);
        }

        TextView categoryTitle = (TextView) convertView.findViewById(R.id.quizList);

        if (quiz != null) {
            categoryTitle.setText(quiz.getQuestion());
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
