package com.rugged.tuberculosisapp.information;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends DialogFragment {
    private ArrayList<String> checked = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_answers, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        ListView quizListView = view.findViewById(R.id.answersList);
        Button dismissButton = view.findViewById(R.id.buttonDismiss);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, checked);
        quizListView.setAdapter(adapter);

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setChecked(ArrayList<String> checked) {
        this.checked = checked;
    }

}
