package com.rugged.tuberculosisapp.medication;

public class Medication {

    private String name;
    private Time time;
    private int dose;

    Medication(String name, Time time, int dose) {
        this.name = name;
        //TODO: Change when API call is available
        this.time = time;
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time.toString();
    }

    public int getDose() {
        return dose;
    }

}
