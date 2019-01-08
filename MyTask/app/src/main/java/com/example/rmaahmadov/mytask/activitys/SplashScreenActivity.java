package com.example.rmaahmadov.mytask.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.rmaahmadov.mytask.R;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        startIntent();
    }


    private void startIntent() {
        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
