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
import java.util.List;

public class TabMedication extends Fragment {

    public static final String TITLE = "TabMedication";

    private ListView medicationList;

    private List<String> names;
    private List<String> times;
    private List<String> amounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_medication, container, false);

        prepareListData();

        ListView medicationList = view.findViewById(R.id.medicationList);

        MedicationAdapter adapter = new MedicationAdapter(this.getContext(), names, times, amounts);

        medicationList.setAdapter(adapter);

        return view;
    }

    private void prepareListData() {
        names = new ArrayList<String>();
        times = new ArrayList<String>();
        amounts = new ArrayList<String>();


        names.add("Rifampicin");
        names.add("Isoniazid");
        names.add("Pyrazinamide");
        names.add("Ethambutol");

        times.add("Between 5 and 10 am");
        times.add("Between 5 and 10 am");
        times.add("Between 5 and 10 am");
        times.add("Between 5 and 10 am");

        amounts.add("1 pill");
        amounts.add("2 pills");
        amounts.add("1 pill");
        amounts.add("2 pills");

    }

}
