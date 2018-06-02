package com.rugged.tuberculosisapp.signin;

import android.accounts.AccountManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;
import com.rugged.tuberculosisapp.settings.UserData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TabSignIn extends Fragment {

    public static final String TITLE = "TabSignIn";

    public static final String ACCOUNT_TYPE = "TubuddyPatientAccount";
    public static final String TOKEN_TYPE = "access_token";
    public static final String KEY_PATIENT_ID = "patient_id";

    private boolean canSignIn = false;

    private static Account mAccount;
    private AccountManager mAccountManager;

    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sign_in, container, false);
        mAccountManager = AccountManager.get(getContext());

        Button signInButton = view.findViewById(R.id.signInButton);
        final EditText textUsername = view.findViewById(R.id.textUsername);
        final EditText textPassword = view.findViewById(R.id.textPassword);
        final TextView invalidText = view.findViewById(R.id.invalidText);
        spinner = view.findViewById(R.id.progressBarSignIn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                String username = textUsername.getText().toString();
                String password = textPassword.getText().toString();
                mAccount = new Account(username, password);
                invalidText.setVisibility(View.GONE);
                if (authenticate(mAccount)) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    invalidText.setVisibility(View.VISIBLE);
                    invalidText.setTextColor(Color.RED);
                    invalidText.setText(getResources().getString(R.string.invalidLogIn));
                    textUsername.setText("");
                    textPassword.setText("");
                }
            }
        });
        return view;
    }

    private boolean authenticate(Account account) {
        canSignIn = false;

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        final Call<Identification> call = serverAPI.login(account);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response <Identification> response = call.execute();
                    if (response.code() == 200) { // 200 means the login was successful
                        UserData.setIdentification(response.body());
                        createAccount(response.body().getId(), response.body().getToken());
                        canSignIn = true;
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (IOException e) {
                    // TODO add more advanced exception handling
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try { // This is done to make sure that the function waits with returning until the API call is finished
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return canSignIn;
    }

    private void createAccount(int patientId, String authToken) {
        android.accounts.Account account = new android.accounts.Account(mAccount.getUsername(), ACCOUNT_TYPE);

        mAccountManager.addAccountExplicitly(account, mAccount.getPassword(), null);
        mAccountManager.setAuthToken(account, TOKEN_TYPE, authToken);
        mAccountManager.setUserData(account, KEY_PATIENT_ID, Integer.toString(patientId));
    }

}