package com.example.rmaahmadov.mytask.activitys;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.fragments.MyProfileFragment;
import com.example.rmaahmadov.mytask.fragments.SportNewsFragment;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class HomeActivity extends AppCompatActivity {
    ViewPager mViewPager;
    Context mContex;
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContex=HomeActivity.this;
        setupViewPager();
    }
    
    
    private void setupViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new HomeNewsFragent());//0
        sectionsPagerAdapter.addFragment(new SportNewsFragment());//1
        sectionsPagerAdapter.addFragment(new MyProfileFragment());//2
        ViewPager viewPager = (ViewPager) findViewById(R.id.myContainer);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.layout_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefeeds);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sportfeeds);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_myprofile);
        
 }
    
}
