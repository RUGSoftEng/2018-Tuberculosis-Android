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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;


public class TabSignIn extends Fragment {

    public static final String TITLE = "TabSignIn";
    private static final boolean IS_CONNECTED_TO_DATABASE = false; // TODO remove when authentication goes through server
    public static String userAPIToken = "";
    private boolean canSignIn = false;

    private static final Account[] DUMMY_ACCOUNTS = new Account[] { // TODO remove when authentication goes through server
        new Account("admin", "admin"),
        new Account("marco", "marco"),
        new Account("niek", "niek"),
        new Account("pj", "pj"),
        new Account("robert", "robert"),
        new Account("roel", "roel")
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
            Retrofit retrofit = new RetrofitClientInstance().getRetrofitInstance();
            ServerAPI serverAPI = retrofit.create(ServerAPI.class);
            Call<ResponseBody> call = serverAPI.login(account);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d(TAG, "onResponse: Server Response: " + response.toString());
                    if (response.code() == 200) { // 200 means the login was successful
                        try { // The response body will return the following: {"token":"String containing api_token for authorization of requests"}
                            String token = response.body().string(); // First we retrieve the response
                            token = token.split(":")[1]; // Now we get the part after the ":"
                            token = token.substring(1, token.length()-2); // Now we will remove the closing bracket and the " signs yielding the token
                            userAPIToken = token; // Save the token (will be needed for other API calls TODO find out why it does not get saved??
                            canSignIn = true; // TODO find out why it does not save true to signIn??
                            Log.d(TAG,"userAPIToken = " + token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
                }
            });
            System.out.println("canSignIn = " + canSignIn);  // TODO always prints false??
            System.out.println("userAPIToken = " + userAPIToken); // TODO never gets set??
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