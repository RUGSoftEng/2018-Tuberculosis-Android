package com.rugged.tuberculosisapp.settings;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.widget.Toast;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;

public class MainPreferenceFragment extends PreferenceFragment {

    public static final String PREF_KEY_LANGUAGE = "pref_key_language";
    private static int resultCode = 0;
    private boolean flag = false;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);

        // Language preference change listener
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_language)));

        // Notification preference change listeners
        findPreference(getString(R.string.pref_key_notification_silent)).setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_notification_sound)));

        // Alarm preference change listeners
        findPreference(getString(R.string.pref_key_alarm_silent)).setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_alarm_sound)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;

                int index;
                if (flag) {
                    index = listPreference.findIndexOfValue(stringValue);
                } else {
                    index = listPreference.findIndexOfValue(LanguageHelper.getCurrentLocale());
                    listPreference.setValueIndex(index);
                }

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

                String stringEntry = preference.getKey();
                if (stringEntry.equals(PREF_KEY_LANGUAGE)) {
                    // Change locale to selected value
                    LanguageHelper.changeLocale(preference.getContext().getResources(), listPreference.getEntryValues()[index].toString());
                    // Set resultCode to new language
                    resultCode = MainActivity.NEW_LANGUAGE;

                    // Flag is set after first time changeListener activates (initialization)
                    if (flag) {
                        // Recreate fragment
                        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
                    } else {
                        flag = true;
                    }
                }
            } else if (preference instanceof CheckBoxPreference) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                String key = checkBoxPreference.getKey();
                Toast toast = Toast.makeText(preference.getContext(), key + " ; " + getString(R.string.pref_key_alarm_silent), Toast.LENGTH_SHORT);
                toast.show();
                if (key.equals(getString(R.string.pref_key_notification_silent))) {
                    UserData.setNotificationSilent(!checkBoxPreference.isChecked());
                } else if (key.equals(getString(R.string.pref_key_alarm_silent))) {
                    UserData.setAlarmSilent(!checkBoxPreference.isChecked());
                }

            } else if (preference instanceof RingtonePreference) {
                RingtonePreference ringtonePreference = (RingtonePreference) preference;

                // For ringtone preferences, look up the correct display value using RingtoneManager.
                Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(stringValue));
                if (ringtone == null) {
                    Toast toast = Toast.makeText(preference.getContext(), getString(R.string.pref_sound_lookup_error), Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int type = ringtonePreference.getRingtoneType();
                    if (type == RingtoneManager.TYPE_NOTIFICATION) {
                        UserData.setNotificationSound(stringValue);
                    } else if (type == RingtoneManager.TYPE_ALARM) {
                        UserData.setAlarmSound(stringValue);
                    }
                    // Set the summary to reflect the new ringtone display name.
                    String name = ringtone.getTitle(preference.getContext());
                    preference.setSummary(name);
                }

            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    public int getResultCode() {
        return resultCode;
    }

}
