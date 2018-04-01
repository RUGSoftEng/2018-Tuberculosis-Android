package com.rugged.tuberculosisapp.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class CalendarAdapter extends ArrayAdapter<Date> {

    private Context mContext;

    // Days with events
    private HashSet<Date> eventDays;

    // For view inflation
    private LayoutInflater inflater;

    CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
        super(context, R.layout.control_calendar_day, days);
        this.mContext = context;
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        // Day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        // Today
        Date today = new Date();

        // Inflate item if it does not exist yet
        if (view == null) {
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);
        }

        // If this day has an event, specify event image
        view.setBackgroundResource(0);
        if (eventDays != null) {
            for (Date eventDate : eventDays) {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    // Mark this day for event
                    view.setBackgroundResource(R.drawable.ic_medication);
                    break;
                }
            }
        }

        // Clear styling
        ((TextView)view).setTypeface(null, Typeface.NORMAL);
        ((TextView)view).setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear()) {
            // If this day is outside current month, grey it out
            ((TextView)view).setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
        }
        else if (day == today.getDate()) {
            // If it is today, set it to blue/bold
            ((TextView)view).setTypeface(null, Typeface.BOLD);
            ((TextView)view).setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        }

        // Set text
        ((TextView)view).setText(String.valueOf(date.getDate()));

        return view;
    }
}