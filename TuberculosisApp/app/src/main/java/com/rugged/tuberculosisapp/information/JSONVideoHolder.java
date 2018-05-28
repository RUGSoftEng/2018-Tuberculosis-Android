package com.rugged.tuberculosisapp.information;

public class JSONVideoHolder {

    private Video video;
    private Quiz quiz;

    JSONVideoHolder(Video video, Quiz quiz) {
        this.video = video;
        this.quiz = quiz;
    }

    public Video getVideo() {
        return video;
    }

    public Quiz getQuiz() {
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
