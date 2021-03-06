package com.rugged.tuberculosisapp.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.medication.Medication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CalendarAdapter extends ArrayAdapter<Date> {

    private Context mContext;

    // Days with events
    private HashMap<Date, ArrayList<Medication>> eventDays;

    // For view inflation
    private LayoutInflater inflater;

    CalendarAdapter(Context context, ArrayList<Date> days, HashMap<Date, ArrayList<Medication>> eventDays) {
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

        // Get dayNumber text and pill indicator image view
        TextView textDayNumber = view.findViewById(R.id.textDayNumber);
        ImageView pillIndicator = view.findViewById(R.id.pillIcon);
        ImageView takenOverlay = view.findViewById(R.id.takenOverlay);

        // Clear styling
        textDayNumber.setTypeface(null, Typeface.NORMAL);
        textDayNumber.setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear()) {
            // If this day is outside current month, grey it out
            textDayNumber.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
        } else if (day == today.getDate()) {
            // If it is today, set it to blue/bold
            textDayNumber.setTypeface(null, Typeface.BOLD);
            textDayNumber.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        }

        // If this day has an event, specify event image
        pillIndicator.setImageResource(0);
        if (eventDays != null) {
            if (eventDays.get(date) != null) {
                // Mark this day for event
                pillIndicator.setImageResource(R.drawable.ic_medication);
                if (month < today.getMonth() || (month == today.getMonth() && day < today.getDate())) {
                    // Set green check mark here
                    //TODO: takenOverlay.setImageResource(R.drawable.ic_check);
                    textDayNumber.setTextColor(view.getResources().getColor(android.R.color.holo_green_dark));
                    for (Medication medication : eventDays.get(date)) {
                        if (!medication.getTaken()) {
                            // Not taken, add red cross
                            //TODO: takenOverlay.setImageResource(R.drawable.ic_cross);
                            textDayNumber.setTextColor(view.getResources().getColor(android.R.color.holo_red_dark));
                            break;
                        }
                    }
                }
            }
        }

        // Set text
        textDayNumber.setText(String.valueOf(date.getDate()));

        return view;
    }
}