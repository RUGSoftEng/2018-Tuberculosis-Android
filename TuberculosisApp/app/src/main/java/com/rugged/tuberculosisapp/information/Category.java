package com.rugged.tuberculosisapp.information;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Category {

    private String title = "Category Title";
    private ArrayList<Urls> videoUrls;
    private int color = android.R.color.white;

    // TODO: Remove when colors in database
    Category(String title, ArrayList<Urls> videoUrls) {
        this.title = title;
        this.videoUrls = videoUrls;
    }

    Category(String title, ArrayList<Urls> videoUrls, int color) {
        this.title = title;
        this.videoUrls = videoUrls;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Urls> getVideoUrls() {
        return videoUrls;
    }

    public int getColor() {
        return color;
    }
}
class Urls implements Serializable {
    private String url;
    private ArrayList<Quiz> quiz;
    Urls (String url, ArrayList<Quiz> quiz){
        this.url = url;
        this.quiz = quiz;
    }

    public ArrayList<Quiz> getQuiz() {
        return quiz;
    }

    public String getUrl() {
        return url;
    }
}
