package com.rugged.tuberculosisapp.calendar;

import com.rugged.tuberculosisapp.medication.Medication;
import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarJSONHolder {

    private Dosage dosage;
    private String date;
    private Boolean taken;

    CalendarJSONHolder(String name, Date timeIntervalStart, Date timeIntervalEnd, int dose, Boolean isTaken, Date date) {
        Locale locale = new Locale(LanguageHelper.getCurrentLocale());
        DateFormat df = new SimpleDateFormat("HH:mm:ss", locale);
        Medicine medicine = new Medicine(name);
        this.dosage = new Dosage(df.format(timeIntervalStart), df.format(timeIntervalEnd), dose, medicine);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        this.date = sdf.format(date);
        this.taken = isTaken;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public String getDate() {
        return date;
    }

    public Boolean getTaken() {
        return taken;
    }

    /**
     * Method that converts JSONData to a medication object
     * @return medication object
     */
    public Medication toMedication() {
        try {
            DateFormat format = new SimpleDateFormat("HH:mm:ss", new Locale(LanguageHelper.getCurrentLocale()));
            Date timeIntervalStart = format.parse(getDosage().getIntakeIntervalStart());
            Date timeIntervalEnd = format.parse(getDosage().getIntakeIntervalEnd());
            String name = getDosage().getMedicine().getName();
            int amount = getDosage().getAmount();
            Boolean isTaken = getTaken();

            return new Medication(name, timeIntervalStart, timeIntervalEnd, amount, isTaken);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}

class Dosage {

    private String intake_interval_start;
    private String intake_interval_end;
    private int amount;
    private Medicine medicine;

    Dosage(String intake_interval_start, String intake_interval_end, int amount, Medicine medicine) {
        this.intake_interval_start = intake_interval_start;
        this.intake_interval_end = intake_interval_end;
        this.amount = amount;
        this.medicine = medicine;
    }

    public String getIntakeIntervalStart() {
        return intake_interval_start;
    }

    public String getIntakeIntervalEnd() {
        return intake_interval_end;
    }

    public int getAmount() {
        return amount;
    }

    public Medicine getMedicine() {
        return medicine;
    }

}

class Medicine {

    private String name;

    Medicine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
