package com.rugged.tuberculosisapp.calendar;

public class JSONResponse {

    private Dosage dosage;
    private String date;
    private Boolean taken;

    public Dosage getDosage() {
        return dosage;
    }

    public String getDate() {
        return date;
    }

    public Boolean getTaken() {
        return taken;
    }

}

class Dosage {

    private String intake_moment;
    private int amount;
    private Medicine medicine;

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

    public String getName() {
        return name;
    }
}
