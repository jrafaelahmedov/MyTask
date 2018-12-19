package com.example.rmaahmadov.mytask.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.fragments.InforationFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class PinActivity extends AppCompatActivity {

    private EditText loginPin;
    Context mContex;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    ImageView informationImg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        loginPin=findViewById(R.id.login_pin);
        mProgressbar=findViewById(R.id.progressBarPin);
        informationImg=findViewById(R.id.imgInformation);
        mProgressbar.setVisibility(View.GONE);
        mContex=PinActivity.this;
        db=new DatabaseHelper(this);
        
        loginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pin= Integer.parseInt(loginPin.getText().toString().trim());
                if(s.length()==4){
                    Boolean res =db.checkUserPin(pin);
                    if(res==true){
//                        FragmentManager fm = getSupportFragmentManager();
//                        HomeNewsFragent fragment = new HomeNewsFragent();
//                        fm.beginTransaction().add(R.id.frag_containerPin,fragment).commit();
                        Intent intent = new Intent(PinActivity.this,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(mContex,"Welcome",Toast.LENGTH_LONG).show();
                        
                    }else{
                        Toast.makeText(mContex,"Email or Password invalid!!",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        informationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                InforationFragment fragment = new InforationFragment();
                fm.beginTransaction().replace(R.id.frag_containerPin,fragment).commit();
            }
        });
        
    }
    
    
//    public void informationPin(View view){
//    
//    }
    
    
    
}
