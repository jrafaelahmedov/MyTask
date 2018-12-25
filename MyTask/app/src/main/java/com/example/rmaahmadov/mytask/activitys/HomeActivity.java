package com.example.rmaahmadov.mytask.activitys;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.fragments.HomeNewsFragment;
import com.example.rmaahmadov.mytask.fragments.LoginFragment;
import com.example.rmaahmadov.mytask.fragments.PinFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LoginFragment fragment;
    DatabaseHelper db;
    PinFragment fragmentPin;
    ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupInit();
        createNecessary();
        createMenuSlider();
        controlUser();
    }


    public void createLoginFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.activityhomelayout, fragment).commit();

    }


    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();

        DrawerLayout drawer =  findViewById(R.id.activityhomelayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (fragments == 1) {
            this.finish();
        }
        super.onBackPressed();
    }


    private void controlUser() {
        SharedPreferences preferences = getSharedPreferences("SavedUser", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");
        if (db.checkUserAndMovePinPage(email, password)) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.activityhomelayout, fragmentPin).commit();

        } else {
            fragment = new LoginFragment();
            createLoginFragment();
        }
    }


    private void createMenuSlider() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.activityhomelayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
    }

    private void createNecessary() {
        fragmentPin = new PinFragment();
        db = new DatabaseHelper(this);
    }


    private void setupInit() {
        mDrawerLayout = findViewById(R.id.activityhomelayout);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawermenuAbout) {
         Intent intent = new Intent(this,AboutUsActivity.class);
         startActivity(intent);
        } else if (id == R.id.drawermenuContact) {

            alertCallUs();

        } else if (id == R.id.drawermenuLogout) {

            logOut();

        }

        DrawerLayout drawer = findViewById(R.id.activityhomelayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void alertCallUs() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setMessage("Do you want to call us? \n +994504500501");
        builder1.setCancelable(true);


        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent dial = new Intent();
                        dial.setAction("android.intent.action.DIAL");
                        try {
                            dial.setData(Uri.parse("tel:+994504500501"));
                            startActivity(dial);
                        } catch (Exception e) {
                            Log.e("Calling", "" + e.getMessage());
                        }

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void logOut() {
        SharedPreferences preferences = getSharedPreferences("SavedUser", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        fragment = new LoginFragment();
        createLoginFragment();
        mViewPager.removeAllViews();
        tabLayout.removeAllTabs();
    }
}
