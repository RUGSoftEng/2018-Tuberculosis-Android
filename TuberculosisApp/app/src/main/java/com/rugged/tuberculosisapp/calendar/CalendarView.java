package com.rugged.tuberculosisapp.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class CalendarView extends LinearLayout {

    private Activity mActivity;

    // Current displayed month
    private Calendar currentDate = Calendar.getInstance();

    // Calendar adapter
    private HashMap<Date, ArrayList<Medication>> mEvents = null;

    // Internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }
    private void assignUiElements() {
        // Layout is inflated, assign local variables to components
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // Add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // Subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // Long-pressing a day
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View cell, int position, long id) {
                // Handle clicking on a day
                Date clickedItem = (Date) view.getItemAtPosition(position);
                ArrayList<Medication> medicationList = mEvents.get(clickedItem);
                if (mEvents != null && medicationList != null) {
                    ViewDayFragment viewDayFragment = new ViewDayFragment();
                    viewDayFragment.setCalendarView(CalendarView.this);
                    viewDayFragment.setDate(clickedItem);
                    viewDayFragment.setMedicationList(medicationList);
                    viewDayFragment.show(mActivity.getFragmentManager(), "ViewDayFragment");
                }
            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashMap<Date, ArrayList<Medication>> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // Determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // Move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // Calculate how many rows are needed
        double daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        int numberOfCells = (int) Math.ceil((daysInMonth + monthBeginningCell) / 7) * 7;

        // Fill cells
        while (cells.size() < numberOfCells) {
            // Set everything less significant than day to 0 in order to get right keys for hash map..
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Save all events when they are added
        if (events != null) {
            mEvents = events;
        }

        // Update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, mEvents));

        // Update title to month, (conversion character MMMM..)
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM YYYY", new Locale(LanguageHelper.getCurrentLocale()));
        String titleMonth = sdf.format(currentDate.getTime());
        // Capitalize first letter
        titleMonth = titleMonth.substring(0, 1).toUpperCase() + titleMonth.substring(1);
        txtDate.setText(titleMonth);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

}