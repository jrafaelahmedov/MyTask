package com.example.rmaahmadov.mytask.activitys;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragent;
import com.example.rmaahmadov.mytask.fragments.LoginFragment;
import com.example.rmaahmadov.mytask.fragments.PinFragment;
import com.example.rmaahmadov.mytask.fragments.SportNewsFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout topBarlayout;
    LoginFragment fragment;
    DatabaseHelper db;
    PinFragment fragmentPin;
    RelativeLayout homeActivity;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTogger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupInit();
        createNecessary();
        setupViewPager();
        createMenuSlider();
        controlUser();
        myMenu();
    }

    private void myMenu() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        return true;
                    }
                });
    }

    public void createLoginFragment() {

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activityhomelayout, fragment).commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mTogger.onOptionsItemSelected(item)) {
            return true;
        }
        findViewById(R.id.drawermenuLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .add(R.id.activityhomelayout, fragment).commit();
            }
        });
        return super.onOptionsItemSelected(item);
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

    private void controlUser() {
        SharedPreferences preferences = getSharedPreferences("SavedUser", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");
        if (db.checkUserAndMovePinPage(email, password)) {
            homeActivity.setVisibility(View.GONE);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.activityhomelayout, fragmentPin).commit();
        } else {
            fragment = new LoginFragment();
            createLoginFragment();
        }
    }


    private void createMenuSlider() {
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        mTogger = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar1, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mTogger);
        mDrawerLayout.bringToFront();
        mDrawerLayout.addDrawerListener(mTogger);
        mTogger.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createNecessary() {
        fragmentPin = new PinFragment();
        db = new DatabaseHelper(this);
    }


    private void setupInit() {
        topBarlayout = findViewById(R.id.relLayout1);
        homeActivity = findViewById(R.id.relLayout2);
        mDrawerLayout = findViewById(R.id.activityhomelayout);
        topBarlayout.setVisibility(View.GONE);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.drawermenuLogout) {
            System.out.println("logout clicked..........................");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.activityhomelayout, fragment).commit();
            return true;
        } else {
            return false;
        }
    }
}
