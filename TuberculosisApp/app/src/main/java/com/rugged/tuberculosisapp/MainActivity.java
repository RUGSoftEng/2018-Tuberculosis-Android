package com.rugged.tuberculosisapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rugged.tuberculosisapp.information.TabInformation;
import com.rugged.tuberculosisapp.calendar.TabCalendar;
import com.rugged.tuberculosisapp.medication.TabMedication;
import com.rugged.tuberculosisapp.notes.TabNotes;
import com.rugged.tuberculosisapp.reminders.AlarmTestActivity;
import com.rugged.tuberculosisapp.reminders.NotificationTestActivity;
import com.rugged.tuberculosisapp.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static final int NEW_SETTING = 1;
    public static final int NEW_LANGUAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the four
        // primary sections.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public void setupViewPager(ViewPager viewPager) {
        // Create the adapter that will return a fragment for each of the four
        // primary sections.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(new TabCalendar(), TabCalendar.TITLE);
        mSectionsPagerAdapter.addFragment(new TabMedication(), TabMedication.TITLE);
        mSectionsPagerAdapter.addFragment(new TabInformation(), TabInformation.TITLE);
        mSectionsPagerAdapter.addFragment(new TabNotes(), TabNotes.TITLE);
        viewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, NEW_SETTING);
        }

        if (id == R.id.action_test_alarm) {
            Intent intent = new Intent(this, AlarmTestActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_test_notification) {
            Intent intent = new Intent(this, NotificationTestActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_sign_out) {
            //TODO: Sign out option
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_SETTING) {
            // Handle new language result
            if (resultCode == NEW_LANGUAGE) {
                recreate();
            }
        }
    }
}
