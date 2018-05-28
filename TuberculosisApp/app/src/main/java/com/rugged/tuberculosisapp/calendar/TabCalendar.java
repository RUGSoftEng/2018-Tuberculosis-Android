package com.rugged.tuberculosisapp.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;

public class TabCalendar extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TITLE = "TabCalendar";

    private CalendarView cv;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("RESUME");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);

        cv = view.findViewById(R.id.calendarView);
        cv.setActivity(getActivity());

        cv.updateCalendar();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);
        System.out.println("View is created");
    }

    @Override
    public void onRefresh() {
        cv.updateCalendar();
        refreshLayout.setRefreshing(false);
    }

}
