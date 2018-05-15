package com.rugged.tuberculosisapp.information;

public class JSONVideoHolder {
    private String reference;
    private String title;
    private String topic;

    JSONVideoHolder(String topic,String title, String reference) {
        this.topic = topic;
        this.title = title;
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getReference() {
        return reference;
    }
}
