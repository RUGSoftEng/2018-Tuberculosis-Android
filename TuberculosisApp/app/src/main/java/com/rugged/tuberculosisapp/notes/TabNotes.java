package com.rugged.tuberculosisapp.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;

public class TabNotes extends Fragment {

    public static final String TITLE = "TabNotes";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_notes, container, false);
        return view;
    }

}
