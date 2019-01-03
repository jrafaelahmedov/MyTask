package com.example.rmaahmadov.mytask.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;


public class SplashScreenActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;
    Context mContex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        initAnimation();
        controlUser();
    }




    public void initAnimation() {
        mContex = SplashScreenActivity.this;
        tv = findViewById(R.id.splashscreentext);
        iv = findViewById(R.id.splashscreenimage);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.spashscreenanimation);
        Animation myAnim1 = AnimationUtils.loadAnimation(this, R.anim.spashscreenanimation);
        tv.startAnimation(myAnim1);
        iv.startAnimation(myAnim);
    }


    private void controlUser() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.getLocalizedMessage();
                } finally {
                    if (isOnline()) {
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Please Connect Internet", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        };
        timer.start();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
