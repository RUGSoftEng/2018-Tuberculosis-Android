package com.rugged.tuberculosisapp.settings;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public abstract class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {
        Configuration config;
        config = res.getConfiguration();
        Locale myLocale = new Locale(locale);
        Locale.setDefault(myLocale);
        config.locale = myLocale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        UserData.setLocale(locale);
    }

    public static String getCurrentLocale() {
        return UserData.getLocaleString();
    }
}
