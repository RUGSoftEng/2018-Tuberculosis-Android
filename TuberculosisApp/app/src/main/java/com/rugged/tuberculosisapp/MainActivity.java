package com.rugged.tuberculosisapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rugged.tuberculosisapp.achievements.ActivityAchievements;
import com.rugged.tuberculosisapp.calendar.CalendarView;
import com.rugged.tuberculosisapp.information.TabInformation;
import com.rugged.tuberculosisapp.calendar.TabCalendar;
import com.rugged.tuberculosisapp.medication.TabMedication;
import com.rugged.tuberculosisapp.notes.TabNotes;
import com.rugged.tuberculosisapp.reminders.ReminderTestActivity;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.SettingsActivity;
import com.rugged.tuberculosisapp.settings.UserData;
import com.rugged.tuberculosisapp.signin.SignInActivity;

import static com.rugged.tuberculosisapp.signin.TabSignIn.ACCOUNT_TYPE;

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
    private static ViewPager mViewPager;

    private CalendarView cv;

    private int currentTab;

    public static boolean isActive;

    public static final int NEW_SETTING = 1;
    public static final int NEW_LANGUAGE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Update language
        if (UserData.getLocaleString() != null) {
            LanguageHelper.changeLocale(getResources(), UserData.getLocaleString());
        }

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
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                currentTab = tab.getPosition();
            }
        });

        setupNotificationChannels();
    }

    public void setupNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = getString(R.string.channel_reminders_name);
            String description = getString(R.string.channel_reminders_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("reminders", name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
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

    public void openAchievements(View view) {
        Intent intent = new Intent(this, ActivityAchievements.class);
        startActivity(intent);
    }

    public static void setTab(int index) {
        mViewPager.setCurrentItem(index);
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

        if (id == R.id.action_sign_out) {
            AccountManager am = AccountManager.get(this);
            Account[] accounts = am.getAccounts();
            for (Account account : accounts) {
                if (account.type.equals(ACCOUNT_TYPE)) {
                    am.removeAccount(account, null, null);
                }
            }

            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
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

    @Override // To clear focus from EditText when tapping outside of the EditText
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && currentTab == 0) {
            if (cv == null) {
                cv = findViewById(R.id.calendarView);
            }

            if (cv.isPointInsideCalendar(event.getRawX(), event.getRawY())) {
                cv.onTouchEvent(event);
                return false;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }
}
