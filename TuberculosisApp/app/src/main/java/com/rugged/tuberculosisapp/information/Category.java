package com.rugged.tuberculosisapp.information;

import java.util.ArrayList;

public class Category {

    private String title = "Category Title";
    private ArrayList<String> videoUrls;

    Category(String title, ArrayList<String> videoUrls) {
        this.title = title;
        this.videoUrls = videoUrls;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getVideoUrls() {
        return videoUrls;
    }
}
