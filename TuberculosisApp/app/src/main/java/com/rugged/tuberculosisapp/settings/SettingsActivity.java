package com.rugged.tuberculosisapp.settings;

import android.os.Bundle;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private MainPreferenceFragment mainPreferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load settings fragment
        mainPreferenceFragment = new MainPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, mainPreferenceFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Set result when user exits settings activity
            setResult(mainPreferenceFragment.getResultCode());
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
