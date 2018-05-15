package com.rugged.tuberculosisapp.calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.reminders.ReminderSetter;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CalendarView extends LinearLayout {
    private Activity mActivity;

    private HashMap<Date, ArrayList<Medication>> mEvents = new HashMap<>();

    // Current displayed month
    private Calendar currentDate = Calendar.getInstance();

    // Internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    private Locale mLocale = new Locale(LanguageHelper.getCurrentLocale());

    private ReminderSetter rs;

    public CalendarView(Context context) {
        super(context);
        this.rs = new ReminderSetter(super.getContext());
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
        this.rs = new ReminderSetter(super.getContext());
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
        this.rs = new ReminderSetter(super.getContext());
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context) {
        inflate(context, R.layout.control_calendar, this);

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

    private void onNextMonth() {
        currentDate.add(Calendar.MONTH, 1);
        updateCalendar();
    }

    private void onPreviousMonth() {
        currentDate.add(Calendar.MONTH, -1);
        updateCalendar();
    }

    private void assignClickHandlers() {
        // Add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextMonth();
            }
        });

        // Subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousMonth();
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
        if (MainActivity.ENABLE_API) {
            mEvents.clear();
            getDatesFromAPI();
        }
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

        // Update title to month and year, (conversion characters MMMM yyyy..)
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", mLocale);
        String titleMonth = sdf.format(currentDate.getTime());
        // Capitalize first letter
        titleMonth = titleMonth.substring(0, 1).toUpperCase() + titleMonth.substring(1);
        txtDate.setText(titleMonth);

        // Create reminders for the current date
        if (mEvents != null) {
            //TODO get medication ArrayList of current day instead
            Calendar cal = new GregorianCalendar(2018, 4, 15);
            Date currentDate = new Date(cal.getTimeInMillis());
            ArrayList<Medication> meds = mEvents.get(currentDate);
            if (meds != null) {
                //Makes sure meds are sorted on time when passing it to the ReminderSetter
                Collections.sort(meds, new Comparator<Medication>() {
                    @Override
                    public int compare(Medication o1, Medication o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });
                //TODO having the date time fields in meds hold the date will remove the need to pass currentDate and do some computations
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    rs.setReminders(currentDate, meds);
                }
            }
        }
    }

    private void getDatesFromAPI() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        Calendar cal = (Calendar) currentDate.clone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01", mLocale);
        String fromDate = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        String toDate = sdf.format(cal.getTime());

        final Call<List<CalendarJSONHolder>> call = serverAPI.getCalendarData(UserData.getIdentification().getId(),
                fromDate, toDate, UserData.getIdentification().getToken());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<List<CalendarJSONHolder>> response = call.execute();

                    // Successful API call
                    if (response.code() == 200) {
                        try {
                            for (CalendarJSONHolder jsonResponse : response.body()) {

                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", mLocale);
                                Date date = format.parse(jsonResponse.getDate());

                                Medication medication = jsonResponse.toMedication();
                                ArrayList<Medication> medicationList;
                                // If there already exists a list for this date retrieve it
                                if (mEvents.containsKey(date)) {
                                    medicationList = mEvents.get(date);
                                } else {
                                    medicationList = new ArrayList<>();
                                }
                                medicationList.add(medication);
                                mEvents.put(date, medicationList);
                            }
                        } catch (Exception e) {
                            // TODO: advanced exception handling, catch specific exceptions: nullPointer, parse etc.
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try {
            // Wait for the thread to finish
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

}