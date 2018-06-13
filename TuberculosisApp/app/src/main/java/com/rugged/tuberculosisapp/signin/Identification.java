package com.rugged.tuberculosisapp.signin;

public class Identification {
    private String token;
    private int id;

    public Identification() {
    }

    public Identification(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public int getId() {
        return this.id;
    }
}
