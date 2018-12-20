package com.example.rmaahmadov.mytask.activitys;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.RelativeLayout;


import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.fragments.LoginFragment;
import com.example.rmaahmadov.mytask.fragments.MyProfileFragment;
import com.example.rmaahmadov.mytask.fragments.SportNewsFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class HomeActivity extends AppCompatActivity {
    ViewPager mViewPager;
    Context mContex;
    RelativeLayout topBarlayout;
    private static final String TAG = "HomeActivity";
    LoginFragment fragment;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        topBarlayout = findViewById(R.id.relLayout1);
        mContex = HomeActivity.this;
        topBarlayout.setVisibility(View.GONE);
        db = new DatabaseHelper(this);
        setupViewPager();
        
        fragment = new LoginFragment();
            createLoginFragment();

    }

    public void createLoginFragment() {

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activityhomelayout, fragment).commit();

    }


    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            fragment.onDestroy();
            this.finish();
        }
        super.onBackPressed();
    }

    private void setupViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new HomeNewsFragent());//0
        sectionsPagerAdapter.addFragment(new SportNewsFragment());//1
        ViewPager viewPager = (ViewPager) findViewById(R.id.myContainer);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.layout_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefeeds);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sportfeeds);

    }

}
