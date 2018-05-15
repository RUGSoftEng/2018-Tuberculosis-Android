package com.rugged.tuberculosisapp.notes;

public class FAQEntry {
    private String question;
    private String answer;

    public FAQEntry(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }
}
