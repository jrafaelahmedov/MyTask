package com.example.rmaahmadov.mytask.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class LoginFragment extends Fragment {

    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextView moveToRegistartion;
    private ProgressBar mProgressbar;
    DatabaseHelper db;
    RegistrationFragment fragmentRegistration;
    LoginFragment fragmentLogin;
    PinFragment fragmentPin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEmail = view.findViewById(R.id.inputEmailLogin);
        mPassword = view.findViewById(R.id.inputPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        mProgressbar = view.findViewById(R.id.progressBarLogin);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(getActivity());
        fragmentRegistration= new RegistrationFragment();
        fragmentLogin=new LoginFragment();
        fragmentPin=new PinFragment();
        moveToRegistartion = view.findViewById(R.id.textViewMoveToRegistration);

        moveToRegistartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.activityhomelayout, fragmentRegistration).commit();
            }
        });
        
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                Boolean res = db.checkUserAndMovePinPage(email, password);
                if (res == true) {
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.activityhomelayout, fragmentPin).commit();
                } else {

                    Toast.makeText(getActivity(), "Email or Password invalid!!", Toast.LENGTH_LONG).show();

                }
                mProgressbar.setVisibility(View.GONE);
            }
        });
        
        
        return view;
    }
}
