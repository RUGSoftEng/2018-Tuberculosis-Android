package com.rugged.tuberculosisapp.notes;

import com.rugged.tuberculosisapp.settings.LanguageHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuestionToPhysician {
    private String question;
    private String created_at;

    public QuestionToPhysician(String question) {
        this.question = question;

        Locale locale = new Locale(LanguageHelper.getCurrentLocale());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        Date currentDate = new Date();
        this.created_at = sdf.format(currentDate);
    }

    public String getQuestion() {
        return question;
    }

    public String getDate() {
        return created_at;
    }
}
