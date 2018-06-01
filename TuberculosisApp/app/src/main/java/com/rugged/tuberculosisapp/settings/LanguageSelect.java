package com.rugged.tuberculosisapp.settings;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.R;
import com.rugged.tuberculosisapp.reminders.Util;
import com.rugged.tuberculosisapp.signin.SignInActivity;
import com.rugged.tuberculosisapp.signin.TabSignIn;
import static com.rugged.tuberculosisapp.signin.TabSignIn.ACCOUNT_TYPE;

public class LanguageSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            if (UserData.isFirstLaunch() || UserData.getLocaleString() == null) {
                setContentView(R.layout.activity_language_select);
                UserData.setIsFirstLaunch(false);
            } else {
                checkAccount();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.no_internet));

            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (Build.VERSION.SDK_INT >= 21) {
                                LanguageSelect.this.finishAndRemoveTask();
                            } else {
                                ActivityCompat.finishAffinity(LanguageSelect.this);
                                System.exit(0);
                            }
                        }
                    });

            builder.show();
        }
    }

    public void chooseLanguage(View view) {
        LanguageHelper.changeLocale(getResources(), (String) view.getTag());
        checkAccount();
    }

    private void checkAccount() {
        if (Build.VERSION.SDK_INT >= 23) {
            Util.scheduleJob(this, 2000);
        }

        AccountManager mAccountManager = AccountManager.get(this);
        Account accounts[] = mAccountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 0) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        } else {
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
