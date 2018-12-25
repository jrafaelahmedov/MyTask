package com.example.rmaahmadov.mytask.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;

public class PinFragment extends Fragment {

    private EditText loginPin;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pin, container, false);
        loginPin = view.findViewById(R.id.login_pin);
        mProgressbar = view.findViewById(R.id.progressBarPin);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(getActivity());
        loginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null&&loginPin.getText()==null) {
                    Toast.makeText(getActivity(), "Pin is empty!!", Toast.LENGTH_LONG).show();
                } else if (s.length() == 4) {
                    int pin = Integer.parseInt(loginPin.getText().toString().trim());
                    Boolean res = db.checkUserPin(pin);
                    if (res == true) {
                        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction().remove(PinFragment.this).commitAllowingStateLoss();
                        setupViewPager();
                    } else{
                        Toast.makeText(getActivity(), "Email or Password invalid!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    private void setupViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = getActivity().findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
