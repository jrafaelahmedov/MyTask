package com.example.rmaahmadov.mytask.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmail, mPassword, mPin;
    private Button btnRegistration;
    private TextView moveToLogin;
    Context mContex;
    ProgressBar mProgressbar;
    DatabaseHelper db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEmail = findViewById(R.id.inputEmailRegistration);
        mPassword = findViewById(R.id.inputPasswordRegistration);
        mPin = findViewById(R.id.inputPin);
        btnRegistration = findViewById(R.id.btnRegistration);
        moveToLogin = findViewById(R.id.textViewMoveToLogin);
        mProgressbar = findViewById(R.id.progressBarRegistration);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(this);
        mContex = RegistrationActivity.this;
    }

    public void signUp(View view) {
        mProgressbar.setVisibility(View.VISIBLE);
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        int pin = Integer.parseInt(mPin.getText().toString().trim());
        long val = db.addUser(email, password, pin);
        if (val > 0) {
            FragmentManager fm = getSupportFragmentManager();
            HomeNewsFragent fragment = new HomeNewsFragent();
            fm.beginTransaction().add(R.id.frag_containerRegistartion,fragment).commit();
        }
    }


    public void moveToSignIn(View view) {
        mProgressbar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(mContex, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
