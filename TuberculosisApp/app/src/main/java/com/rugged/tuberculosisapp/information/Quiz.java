package com.rugged.tuberculosisapp.information;

import java.util.ArrayList;

public class Quiz {
    private String question;
    private ArrayList<String> options;
    private String answer;

    public Quiz (String question, ArrayList<String> options, String answer){
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }
}
