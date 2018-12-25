package com.example.rmaahmadov.mytask.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rmaahmadov.mytask.R;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

//               findViewById(R.id.coordinatorLayoutHomeActivity).setVisibility(View.VISIBLE);
    }



    public void closeActivity(View view){
        AboutUsActivity.this.finish();
    }
}
