package com.example.rmaahmadov.mytask.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.activitys.HomeActivity;
import com.example.rmaahmadov.mytask.fragments.InforationFragment;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;

public class PinFragment extends Fragment {

    private EditText loginPin;
    Context mContex;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    ImageView informationImg;
    RelativeLayout topBarlayout;
    LoginFragment fragment;
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pin, container, false);
        loginPin = view.findViewById(R.id.login_pin);
        mProgressbar = view.findViewById(R.id.progressBarPin);
        informationImg = view.findViewById(R.id.imgInformation);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(getActivity());
        loginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pin = Integer.parseInt(loginPin.getText().toString().trim());
                if (s.length() == 4) {
                    Boolean res = db.checkUserPin(pin);
                    if (res == true) {
                        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
                        topBarlayout=getActivity().findViewById(R.id.relLayout1);
                        topBarlayout.setVisibility(View.VISIBLE);
                        sectionsPagerAdapter.addFragment(new HomeNewsFragent());//0
                        sectionsPagerAdapter.addFragment(new SportNewsFragment());//1
                        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.myContainer);
                        viewPager.setAdapter(sectionsPagerAdapter);
                        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.layout_tabs);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_homefeeds);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sportfeeds);
                        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                        PinFragment.this.onDestroy();
                    } else {
                        Toast.makeText(getActivity(), "Email or Password invalid!!", Toast.LENGTH_LONG).show();
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
                FragmentManager fm = getFragmentManager();
                InforationFragment fragment = new InforationFragment();
                fm.beginTransaction().replace(R.id.frag_containerPin, fragment).commit();
            }
        });
     return view;
    }

}
