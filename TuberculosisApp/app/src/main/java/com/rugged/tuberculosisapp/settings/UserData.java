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

    public static Boolean getNotificationSilent() {
        return sharedPreferences.getBoolean("notification_silent", false);
    }

    public static Boolean getNotificationVibrate() {
        return sharedPreferences.getBoolean("notification_vibrate", true);
    }

    public static String getNotificationSound() {
        return sharedPreferences.getString("notification", null);
    }

    public static Boolean getAlarmSilent() {
        return sharedPreferences.getBoolean("alarm_silent", false);
    }

    public static Boolean getAlarmVibrate() {
        return sharedPreferences.getBoolean("alarm_vibrate", true);
    }

    public static String getAlarmSound() {
        return sharedPreferences.getString("alarm", null);
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

    public static void setNotificationSilent(Boolean bool) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean("notification_silent", bool);
        preferenceEditor.apply();
    }

    public static void setNotificationVibrate(Boolean bool) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean("notification_vibrate", bool);
        preferenceEditor.apply();
    }

    public static void setNotificationSound(String alarm) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString("notification", alarm);
        preferenceEditor.apply();
    }

    public static void setAlarmSilent(Boolean bool) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean("alarm_silent", bool);
        preferenceEditor.apply();
    }

    public static void setAlarmVibrate(Boolean bool) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putBoolean("alarm_vibrate", bool);
        preferenceEditor.apply();
    }

    public static void setAlarmSound(String alarm) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putString("alarm", alarm);
        preferenceEditor.apply();
    }

}
