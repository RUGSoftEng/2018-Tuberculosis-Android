package com.rugged.tuberculosisapp.medication;

public class Time {

    private int hour, minute;

    public Time(int hour, int minute) {
        this.hour = hour % 24;
        this.minute = minute % 60;
    }

    @Override
    public String toString() {
        return hour + ":" + (minute > 9 ? minute : "0" + minute);
    }

}
