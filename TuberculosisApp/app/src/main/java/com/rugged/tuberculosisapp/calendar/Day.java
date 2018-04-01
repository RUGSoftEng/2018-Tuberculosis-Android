package com.rugged.tuberculosisapp.calendar;

import com.rugged.tuberculosisapp.medication.Medication;

import java.util.ArrayList;
import java.util.Date;

public class Day {

    private Date date;
    private ArrayList<Medication> medicationList;

    Day(Date date, ArrayList<Medication> medicationList) {
        this.date = date;
        this.medicationList = medicationList;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Medication> getMedicationList() {
        return medicationList;
    }

}
