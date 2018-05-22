package com.rugged.tuberculosisapp.settings;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rugged.tuberculosisapp.signin.Identification;

/**
 * This class is for 'storing' user data that is retrieved from persistent storage,
 * so NOT for persistent data storage itself. This data is only available for the application's lifecycle
 */
public class UserData extends Application {

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    // Test function
    public static void clearPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static Identification getIdentification() {
        Identification identification = new Identification();
        identification.setId(sharedPreferences.getInt("patientId", -1));
        identification.setToken(sharedPreferences.getString("access-token", null));
        return identification;
    }

    public static String getLocaleString() {
        return sharedPreferences.getString("locale", null);
    }

    public static Boolean isFirstLaunch() {
        return sharedPreferences.getBoolean("firstLaunch", true);
    }

    public static void setIdentification(Identification identification) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putInt("patientId", identification.getId());
        preferenceEditor.putString("access-token", identification.getToken());
        preferenceEditor.apply();
    }

    public static void setLocale(String locale) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString("locale", locale);
        preferenceEditor.apply();
    }

    public static void setIsFirstLaunch(Boolean bool) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean("firstLaunch", bool);
        preferenceEditor.apply();
    }

}
