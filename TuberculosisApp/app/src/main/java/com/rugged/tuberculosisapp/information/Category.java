package com.rugged.tuberculosisapp.information;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {

    private String title = "Category Title";
    private HashMap<String, ArrayList<Quiz>> videos;
    private int color = android.R.color.white;

    // TODO: Remove when colors in database
    Category(String title, HashMap<String, ArrayList<Quiz>> videos) {
        this.title = title;
        this.videos = videos;
    }

    Category(String title, HashMap<String, ArrayList<Quiz>> videos, int color) {
        this.title = title;
        this.videos = videos;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, ArrayList<Quiz>> getVideos() {
        return videos;
    }

    public int getColor() {
        return color;
    }

}
