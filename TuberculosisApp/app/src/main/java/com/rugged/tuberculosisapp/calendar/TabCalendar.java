package com.rugged.tuberculosisapp.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import static com.rugged.tuberculosisapp.MainActivity.ENABLE_API;

public class TabCalendar extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TITLE = "TabCalendar";
    private static HashMap<Date, ArrayList<Medication>> events = new HashMap<>();

    private CalendarView cv;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);

        cv = view.findViewById(R.id.calendarView);
        cv.setActivity(getActivity());

        refreshLayout = view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        if (ENABLE_API) {
            cv.updateCalendar();
        } else {
            initializeMedication();
            cv.updateCalendar(events);
        }

        return view;
    }

    private void initializeMedication() {
        try {
            ArrayList<Medication> medicationList = new ArrayList<>();
            ArrayList<Medication> medicationList2 = new ArrayList<>();
            ArrayList<Medication> medicationList3 = new ArrayList<>();
            DateFormat df = new SimpleDateFormat("HH:mm", new Locale(LanguageHelper.getCurrentLocale()));

            Date time1 = df.parse("08:00");
            Date time2 = df.parse("21:33");
            Date time3 = df.parse("17:30");

            medicationList.add(new Medication("Rifampicin", time1, 1, true));

            medicationList2.add(new Medication("Rifampicin", time1, 1, true));
            medicationList2.add(new Medication("Isoniazid", time1, 2, false));
            medicationList2.add(new Medication("Pyrazinamide", time1, 1, false));
            medicationList2.add(new Medication("Ethambutol", time2, 2, false));

            medicationList3.add(new Medication("Rifampicin", time1, 1, true));
            medicationList3.add(new Medication("Ethambutol", time2, 2, false));
            medicationList3.add(new Medication("Isoniazid", time3, 2, false));

            Calendar cal = new GregorianCalendar(2018, 3, 9);
            events.put(new Date(cal.getTimeInMillis()), medicationList);
            cal.set(2018, 3, 28);
            events.put(new Date(cal.getTimeInMillis()), medicationList2);
            cal.set(2018, 2, 23);
            events.put(new Date(cal.getTimeInMillis()), medicationList3);
            cal.set(2018, 4, 15);
            events.put(new Date(cal.getTimeInMillis()), medicationList3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        cv.updateCalendar();
        refreshLayout.setRefreshing(false);
    }

}
