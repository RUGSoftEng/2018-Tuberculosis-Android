package com.rugged.tuberculosisapp.information;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.medication.MedicationListAdapter;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class QuizFragment extends DialogFragment {
    private ArrayList<String> checked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_answers, container, false);

        ListView quizListView = view.findViewById(R.id.quizList);
        Button dismissButton = view.findViewById(R.id.buttonDismiss);
       ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.quizlist, checked);
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
