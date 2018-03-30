package com.rugged.tuberculosisapp.settings;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public abstract class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {
        Configuration config;
        config = res.getConfiguration();
        Locale myLocale = new Locale(locale);
        DisplayMetrics dm = res.getDisplayMetrics();
        config.locale = myLocale;
        res.updateConfiguration(config, dm);
    }

}
