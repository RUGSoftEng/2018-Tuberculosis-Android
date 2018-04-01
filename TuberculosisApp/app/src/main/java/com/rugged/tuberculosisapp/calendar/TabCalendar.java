package com.rugged.tuberculosisapp.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;

public class TabCalendar extends Fragment {

    public static final String TITLE = "TabCalendar";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);
        return view;
    }

    /* Code to add events to the calendar
    HashSet<Date> events = new HashSet<>();
    events.add(new Date());

    CalendarView cv = ((CalendarView)findViewById(R.id.calendarView));
    cv.updateCalendar(events);
     */

}
