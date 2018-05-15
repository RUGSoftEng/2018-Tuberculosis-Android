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

    CalendarJSONHolder(String name, Date time, int dose, Boolean isTaken, Date date) {
        Locale locale = new Locale(LanguageHelper.getCurrentLocale());
        DateFormat df = new SimpleDateFormat("HH:mm:ss", locale);
        Medicine medicine = new Medicine(name);
        this.dosage = new Dosage(df.format(time), dose, medicine);
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
            Date time = format.parse(getDosage().getIntakeMoment());
            String name = getDosage().getMedicine().getName();
            int amount = getDosage().getAmount();
            Boolean isTaken = getTaken();

            return new Medication(name, time, amount, isTaken);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}

class Dosage {

    private String intake_moment;
    private int amount;
    private Medicine medicine;

    Dosage(String intake_moment, int amount, Medicine medicine) {
        this.intake_moment = intake_moment;
        this.amount = amount;
        this.medicine = medicine;
    }

    public String getIntakeMoment() {
        return intake_moment;
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
