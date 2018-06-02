package com.rugged.tuberculosisapp.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.SectionsPagerAdapter;
import com.rugged.tuberculosisapp.settings.LanguageHelper;
import com.rugged.tuberculosisapp.settings.LanguageSelect;
import com.rugged.tuberculosisapp.settings.UserData;

public class SignInActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Update language
        if (UserData.getLocaleString() != null) {
            LanguageHelper.changeLocale(getResources(), UserData.getLocaleString());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = findViewById(R.id.toolbarSignIn);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.viewPagerSignIn);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabsSignIn);
        //tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public void setupViewPager(ViewPager viewPager) {
        // Create the adapter that will return a fragment for each of the four
        // primary sections.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new TabSignIn(), com.rugged.tuberculosisapp.signin.TabSignIn.TITLE);
        mSectionsPagerAdapter.addFragment(new com.rugged.tuberculosisapp.information.TabInformation(), com.rugged.tuberculosisapp.information.TabInformation.TITLE);

        viewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.change_language) {
            Intent intent = new Intent(this, LanguageSelect.class);
            UserData.setIsFirstLaunch(true);
            startActivity(intent);
        }

        if (id == R.id.close_app_signin) {
            if (Build.VERSION.SDK_INT >= 21) {
                moveTaskToBack(true);
            } else {
                ActivityCompat.finishAffinity(this);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override // To clear focus from EditText when tapping outside of the EditText
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

}
