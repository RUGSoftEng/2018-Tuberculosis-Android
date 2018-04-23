package com.rugged.tuberculosisapp.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.information.Category;

import java.util.ArrayList;
import java.util.List;

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

        return view;
    }

    private void prepareListData() {
        TextView question1 = new TextView(getActivity());
        question1.setId(View.generateViewId());
        question1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        question1.setText(getText(R.string.lorem_ipsum));

        TextView question2 = new TextView(getActivity());
        question1.setId(View.generateViewId());
        question1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        question1.setText(getText(R.string.lorem_ipsum));

        TextView question3 = new TextView(getActivity());
        question1.setId(View.generateViewId());
        question1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        question1.setText(getText(R.string.lorem_ipsum));

        entries.addView(question1);
        entries.addView(question2);
        entries.addView(question3);
    }
}
