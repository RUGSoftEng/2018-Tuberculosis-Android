package com.rugged.tuberculosisapp.information;

public class JSONVideoHolder {
    private String videoUrl;
    private String title;
    private String topic;

    JSONVideoHolder(String topic,String title, String videoUrl) {
        this.topic = topic;
        this.title = title;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
