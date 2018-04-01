package com.rugged.tuberculosisapp.calendar;

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
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class CalendarView extends LinearLayout {

    // For logging
    private static final String LOGTAG = "Calendar View";

    // How many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // Current displayed month
    private Calendar currentDate = Calendar.getInstance();

    // Event handling
    private EventHandler eventHandler = null;

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
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // Handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
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
    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // Determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // Move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // Fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // Update title to month, (conversion character MMMM..)
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale(LanguageHelper.getCurrentLocale()));
        String titleMonth = sdf.format(currentDate.getTime());
        // Capitalize first letter
        titleMonth = titleMonth.substring(0, 1).toUpperCase() + titleMonth.substring(1);
        txtDate.setText(titleMonth);
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Date date);
    }
}