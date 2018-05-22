package com.rugged.tuberculosisapp.information;

import java.util.ArrayList;

public class Category {

    private String title = "Category Title";
    private ArrayList<String> videoUrls;
    private int color = android.R.color.white;

    // TODO: Remove when colors in database
    Category(String title, ArrayList<String> videoUrls) {
        this.title = title;
        this.videoUrls = videoUrls;
    }

    Category(String title, ArrayList<String> videoUrls, int color) {
        this.title = title;
        this.videoUrls = videoUrls;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getVideoUrls() {
        return videoUrls;
    }

    public int getColor() {
        return color;
    }
}
