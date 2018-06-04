package com.rugged.tuberculosisapp.information;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JSONVideoHolder {

    private Video video;
    private ArrayList<Quiz> quiz;

    JSONVideoHolder(Video video, ArrayList<Quiz> quiz) {
        this.video = video;
        this.quiz = quiz;
    }

    public Video getVideo() {
        return video;
    }

    public ArrayList<Quiz> getQuiz() {
        return quiz;
    }

}

class Video {

    private String topic;
    private String title;
    private String reference;

    Video(String topic, String title, String reference) {
        this.topic = topic;
        this.title = title;
        this.reference = reference;
    }

    public String getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getReference() {
        return reference;
    }

}

class   Quiz implements Serializable {
    private String question;
    private ArrayList<String> answers;
    private static final long serialVersionUID = 1L;

     Quiz (String question, ArrayList<String> answers){
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return answers;
    }
}
