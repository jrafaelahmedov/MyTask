package com.example.rmaahmadov.mytask.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.fragments.LoginFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class RegistrationFragment extends Fragment {

    private EditText mEmail, mPassword, mPin;
    private Button btnRegistration;
    private TextView moveToLogin;
    Context mContex;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    RegistrationFragment fragmentRegistration;
    LoginFragment fragmentLogin;
    RelativeLayout homeActivity;
    RelativeLayout homeActivity1;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        mEmail = view.findViewById(R.id.inputEmailRegistration);
        mPassword = view.findViewById(R.id.inputPasswordRegistration);
        mPin = view.findViewById(R.id.inputPin);
        btnRegistration = view.findViewById(R.id.btnRegistration);
        moveToLogin = view.findViewById(R.id.textViewMoveToLogin);
        mProgressbar = view.findViewById(R.id.progressBarRegistration);
        mProgressbar.setVisibility(View.GONE);
        fragmentRegistration = new RegistrationFragment();
        fragmentLogin = new LoginFragment();
        db = new DatabaseHelper(getActivity());


        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragmentLogin).commit();
//                FragmentManager manager = getFragmentManager();
//                manager.beginTransaction()
//                        .replace(R.id.activityhomelayout, fragmentLogin).commit();
            }
        });


        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (!mPin.getText().toString().trim().isEmpty()) {
                    int pin = Integer.parseInt(mPin.getText().toString().trim());
                    long val = db.addUser(email, password, pin);
                    if (val > 0) {
                        getFragmentManager().beginTransaction().remove(RegistrationFragment.this).commitAllowingStateLoss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Pin is empty!!", Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.GONE);
                }
                ;

            }
        });

        return view;
    }
}
