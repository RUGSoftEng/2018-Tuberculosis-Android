package com.rugged.tuberculosisapp.signin;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rugged.tuberculosisapp.R;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rugged.tuberculosisapp.MainActivity;


public class TabSignIn extends Fragment {

    public static final String TITLE = "TabSignIn";
    private static final Account[] DUMMY_ACCOUNTS = new Account[] {
        new Account("bar", "42"),
        new Account("foo", "21")
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sign_in, container, false);

        Button signInButton = view.findViewById(R.id.signInButton);
        final EditText textUsername = view.findViewById(R.id.textUsername);
        final EditText textPassword = view.findViewById(R.id.textPassword);
        final RelativeLayout parentView = view.findViewById(R.id.signInParent);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString();
                String password = textPassword.getText().toString();
                if (authenticate(new Account(username, password))) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    TextView invalid = new TextView(getActivity());
                    invalid.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    invalid.setText(getText(R.string.invalidLogIn));
                    invalid.setTextColor(Color.RED);
                    parentView.addView(invalid);
                }
            }
        });
        return view;
    }

    // TODO change authentication function to call database
    private boolean authenticate(Account account) {
        for (int i = 0; i < DUMMY_ACCOUNTS.length; i++) {
            if (account.getUsername().equals(DUMMY_ACCOUNTS[i].getUsername())
                    && account.getPassword().equals(DUMMY_ACCOUNTS[i].getPassword())) {
                return true;
            }
        }
        return false;
    }
}