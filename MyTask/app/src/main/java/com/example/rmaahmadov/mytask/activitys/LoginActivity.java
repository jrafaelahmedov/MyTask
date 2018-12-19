package com.example.rmaahmadov.mytask.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
  
    private EditText mEmail,mPassword;
    private Button btnLogin;
    private TextView moveToRegistartion;
    private ProgressBar mProgressbar;
    DatabaseHelper db;
  
  
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.inputEmailLogin);
        mPassword=findViewById(R.id.inputPasswordLogin);
        btnLogin=findViewById(R.id.btnLogin);
        mProgressbar=findViewById(R.id.progressBarLogin);
        mProgressbar.setVisibility(View.GONE);
        db=new DatabaseHelper(this);
        
        moveToRegistartion=findViewById(R.id.textViewMoveToRegistration);
    }
    
    public void signIn(View view){
        mProgressbar.setVisibility(View.VISIBLE);
        String email=mEmail.getText().toString().trim();
        String password =mPassword.getText().toString().trim();
        Boolean res =db.checkUserAndMovePinPage(email,password);
        if(res==true){
            Intent intent = new Intent(this,PinActivity.class);
            startActivity(intent);
        }else{ 
           
            Toast.makeText(this,"Email or Password invalid!!",Toast.LENGTH_LONG).show();
            
        }
        mProgressbar.setVisibility(View.GONE);
    }
    
    
    
    public void moveToSingUp(View view){
        //mProgressbar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
