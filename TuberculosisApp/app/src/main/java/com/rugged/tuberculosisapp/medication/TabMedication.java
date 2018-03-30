package com.rugged.tuberculosisapp.medication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabMedication extends Fragment {

    public static final String TITLE = "TabMedication";

    private ListView medicationListView;

    private List<Medication> medicationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_medication, container, false);

        prepareListData();

        medicationListView = view.findViewById(R.id.medicationList);

        MedicationListAdapter adapter = new MedicationListAdapter(this.getContext(), R.layout.medication_row, medicationList);

        medicationListView.setAdapter(adapter);

        return view;
    }

    private void prepareListData() {
        medicationList = new ArrayList<>();
        Time time1 = new Time(8, 0);
        Time time2 = new Time(16, 30);
        medicationList.add(new Medication("Rifampicin", time1, 1));
        medicationList.add(new Medication("Isoniazid", time1, 2));
        medicationList.add(new Medication("Pyrazinamide", time1, 1));
        medicationList.add(new Medication("Ethambutol", time2, 2));

    }

}
