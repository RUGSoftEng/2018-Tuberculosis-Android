package com.rugged.tuberculosisapp.medication;

import java.util.Date;

public class Medication {

    private String name;
    private Date timeIntervalStart, timeIntervalEnd;
    private int dose;
    private boolean isTaken;

    public Medication(String name, Date timeIntervalStart, Date timeIntervalEnd, int dose, boolean isTaken) {
        this.name = name;
        this.timeIntervalStart = timeIntervalStart;
        this.timeIntervalEnd = timeIntervalEnd;
        this.dose = dose;
        this.isTaken = isTaken;
    }

    public String getName() {
        return name;
    }

    public Date getTimeIntervalStart() {
        return timeIntervalStart;
    }

    public Date getTimeIntervalEnd() {
        return timeIntervalEnd;
    }

    public int getDose() {
        return dose;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Time: " + timeIntervalStart.toString() + " - " + timeIntervalEnd.toString() + ", Dose: " + dose + ", isTaken: " + isTaken;
    }

    @Override
    public boolean equals(Object o) {
        Medication m = (Medication) o;
        if(this.name.equals(m.getName())) return true;
        return false;
    }
}


