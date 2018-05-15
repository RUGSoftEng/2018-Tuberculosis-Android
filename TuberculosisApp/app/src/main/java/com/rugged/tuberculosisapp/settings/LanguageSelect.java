package com.rugged.tuberculosisapp.settings;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.signin.SignInActivity;
import com.rugged.tuberculosisapp.signin.TabSignIn;

import static com.rugged.tuberculosisapp.MainActivity.ENABLE_API;
import static com.rugged.tuberculosisapp.signin.TabSignIn.ACCOUNT_TYPE;

public class LanguageSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Update language

        super.onCreate(savedInstanceState);
        if (UserData.isFirstLaunch()) {
            setContentView(R.layout.activity_language_select);
            UserData.setIsFirstLaunch(false);
        } else {
            checkAccount();
        }
    }

    public void chooseLanguage(View view) {
        LanguageHelper.changeLocale(getResources(), (String) view.getTag());
        checkAccount();
    }

    private void checkAccount() {
        AccountManager mAccountManager = AccountManager.get(this);
        Account accounts[] = mAccountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 0 || !ENABLE_API) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        } else if (ENABLE_API) {
            final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(accounts[0], TabSignIn.TOKEN_TYPE,
                    null, this, null, null);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bundle bundle = future.getResult();
                        String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                        UserData.getIdentification().setToken(authToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            t.start();

            UserData.getIdentification().setId(Integer.parseInt(mAccountManager.getUserData(accounts[0], TabSignIn.KEY_PATIENT_ID)));

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
