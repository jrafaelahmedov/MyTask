package com.example.rmaahmadov.mytask;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupViewPager();
    }

    private void setupViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new HomeNewsFragent());//0
        sectionsPagerAdapter.addFragment(new WorldNewsFragment());//1
        sectionsPagerAdapter.addFragment(new SportNewaFragment());//2
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.layout_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefeeds);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_worldfeeds);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_sportfeeds);
// 
 }
    
}
