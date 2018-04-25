package com.rugged.tuberculosisapp.medication;

public class Medication {

    private String name;
    private Time time;
    private int dose;
    private boolean isTaken = false;

    Medication(String name, Time time, int dose) {
        this.name = name;
        //TODO: Change when API call is available
        this.time = time;
        this.dose = dose;
    }

    public Medication(String name, Time time, int dose, boolean isTaken) {
        this.name = name;
        this.time = time;
        this.dose = dose;
        this.isTaken = isTaken;
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public int getDose() {
        return dose;
    }

    public boolean getTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

}
