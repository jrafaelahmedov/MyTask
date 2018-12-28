package com.example.rmaahmadov.mytask.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rmaahmadov.mytask.fragments.HomeNewsFragment;
import com.example.rmaahmadov.mytask.fragments.SportNewsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }




    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeNewsFragment homeNewsFragent = new HomeNewsFragment();
                return homeNewsFragent;

            case 1:
                SportNewsFragment sportNewsFragment = new SportNewsFragment();
                return sportNewsFragment;

                default:
                    return null;
        }
    }






    @Override
    public int getCount() {
        return 2;
    }






    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home News";
            case 1:
                return "Sport News";

                default:
                    return null;
        }
    }
}