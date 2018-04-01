package com.rugged.tuberculosisapp.medication;

public class Medication {

    private String name;
    private Time time;
    private int dose;
    public enum takenState {
        PENDING, TRUE, FALSE
    }
    private takenState isTaken;

    public Medication(String name, Time time, int dose) {
        this.name = name;
        //TODO: Change when API call is available
        this.time = time;
        this.dose = dose;
    }

    public Medication(String name, Time time, int dose, takenState isTaken) {
        this.name = name;
        this.time = time;
        this.dose = dose;
        this.isTaken = isTaken;
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

    public takenState getTaken() {
        return isTaken;
    }
}
