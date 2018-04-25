package com.rugged.tuberculosisapp.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.medication.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TabCalendar extends Fragment {

    public static final String TITLE = "TabCalendar";
    private static HashMap<Date, ArrayList<Medication>> events;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);

        // Initialize medication on different days
        initializeMedication();

        CalendarView cv = view.findViewById(R.id.calendarView);
        cv.setActivity(getActivity());
        cv.updateCalendar(events);

        return view;
    }

    private void initializeMedication() {
        events = new HashMap<>();
        ArrayList<Medication> medicationList = new ArrayList<>();
        ArrayList<Medication> medicationList2 = new ArrayList<>();
        Time time1 = new Time(8, 0);
        Time time2 = new Time(16, 30);
        medicationList.add(new Medication("Rifampicin", time1, 1, true));
        medicationList.add(new Medication("Isoniazid", time1, 2, false));
        medicationList.add(new Medication("Pyrazinamide", time1, 1, false));
        medicationList.add(new Medication("Ethambutol", time2, 2, false));

        medicationList2.add(new Medication("Rifampicin", time1, 1, true));


        Calendar cal = new GregorianCalendar(2018, 3, 9);
        events.put(new Date(cal.getTimeInMillis()), medicationList);
        cal.set(2018, 4, 1);
        events.put(new Date(cal.getTimeInMillis()), medicationList);
        cal.set(2018, 3, 22);
        events.put(new Date(cal.getTimeInMillis()), medicationList);

        cal.set(2018, 3, 21);
        events.put(new Date(cal.getTimeInMillis()), medicationList2);
        cal.set(2018, 2, 14);
        events.put(new Date(cal.getTimeInMillis()), medicationList2);
        cal.set(2018, 2, 15);
        events.put(new Date(cal.getTimeInMillis()), medicationList2);
    }

}
