package com.rugged.tuberculosisapp.signin;

public class Identification {
    private String token;
    private int id;

    public Identification(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public int getId() {
        return this.id;
    }
}
