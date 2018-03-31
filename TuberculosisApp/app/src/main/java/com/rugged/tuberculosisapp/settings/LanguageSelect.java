package com.rugged.tuberculosisapp.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.signin.SignInActivity;

public class LanguageSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
    }
    public void chooseLanguage(View view) {
        LanguageHelper.changeLocale(getResources(), (String) view.getTag());
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
