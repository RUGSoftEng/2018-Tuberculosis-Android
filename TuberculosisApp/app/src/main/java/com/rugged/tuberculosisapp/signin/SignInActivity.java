package com.rugged.tuberculosisapp.signin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.SectionsPagerAdapter;
import com.rugged.tuberculosisapp.signin.TabSignIn;

public class SignInActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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


}
