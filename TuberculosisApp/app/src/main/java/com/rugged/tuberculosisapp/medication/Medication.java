package com.rugged.tuberculosisapp.medication;

import java.util.Date;

public class Medication {

    private String name;
    private Date time;
    private int dose;
    private boolean isTaken = false;

    Medication(String name, Date time, int dose) {
        this.name = name;
        this.time = time;
        this.dose = dose;
    }

    public Medication(String name, Date time, int dose, boolean isTaken) {
        this.name = name;
        this.time = time;
        this.dose = dose;
        this.isTaken = isTaken;
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
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

    @Override
    public String toString() {

        return "Name: " + name + ", Time: " + time.toString() + ", Dose: " + dose + ", isTaken: " + isTaken;
    }

}
