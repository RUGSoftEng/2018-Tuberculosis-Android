package com.rugged.tuberculosisapp.signin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;
import com.rugged.tuberculosisapp.network.RetrofitClientInstance;
import com.rugged.tuberculosisapp.network.ServerAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;


public class TabSignIn extends Fragment {

    public static final String TITLE = "TabSignIn";
    private static final boolean IS_CONNECTED_TO_DATABASE = false; // TODO remove when authentication goes through server

    public static Identification identification;
    private boolean canSignIn = false;

    private static final Account[] DUMMY_ACCOUNTS = new Account[] { // TODO remove when authentication goes through server
        new Account("admin", "admin"),
        new Account("marco", "marco"),
        new Account("niek", "niek"),
        new Account("pj", "pj"),
        new Account("robert", "robert"),
        new Account("roel", "roel"),
        new Account("", "")
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sign_in, container, false);

        Button signInButton = view.findViewById(R.id.signInButton);
        final EditText textUsername = view.findViewById(R.id.textUsername);
        final EditText textPassword = view.findViewById(R.id.textPassword);
        final LinearLayout invalidText = view.findViewById(R.id.invalidText);
        invalidText.removeAllViews();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString();
                String password = textPassword.getText().toString();
                invalidText.removeAllViews();
                if (authenticate(new Account(username, password))) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    TextView invalid = new TextView(getActivity());
                    invalid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    invalid.setText(getText(R.string.invalidLogIn));
                    invalid.setTextColor(Color.RED);
                    invalid.setGravity(Gravity.CENTER);
                    invalidText.addView(invalid);
                    textUsername.setText("");
                    textPassword.setText("");
                }
            }
        });
        return view;
    }

    private boolean authenticate(Account account) {
        if (IS_CONNECTED_TO_DATABASE) {
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
                            identification = response.body();
                            Log.d(TAG,"userAPIToken = " + identification.getToken());
                            Log.d(TAG,"userID = " + identification.getId());
                            canSignIn = true;
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

        } else {
            for (int i = 0; i < DUMMY_ACCOUNTS.length; i++) {
                if (account.getUsername().equals(DUMMY_ACCOUNTS[i].getUsername())
                        && account.getPassword().equals(DUMMY_ACCOUNTS[i].getPassword())) {
                    return true;
                }
            }
            return false;
        }
    }
}