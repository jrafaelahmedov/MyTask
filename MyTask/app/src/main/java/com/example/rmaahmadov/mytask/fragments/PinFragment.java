package com.example.rmaahmadov.mytask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.Interfaces.MyKeyboardClick;
import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.activitys.HomeActivity;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.MyKeyboard;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;


public class PinFragment extends Fragment implements MyKeyboardClick{

    private EditText loginPin;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    AppBarLayout appBarLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pin, container, false);
        loginPin = view.findViewById(R.id.login_pin);
        loginPin.setShowSoftInputOnFocus(false);
        mProgressbar = view.findViewById(R.id.progressBarPin);
        MyKeyboard keyboard = view.findViewById(R.id.keyboard);
        loginPin.setRawInputType(InputType.TYPE_CLASS_TEXT);
        loginPin.setTextIsSelectable(true);
        keyboard.setOnClickListener(this);
        InputConnection in = loginPin.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(in);
        appBarLayout = getActivity().findViewById(R.id.appbar);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(getActivity());
        return view;
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null && loginPin.getText() == null) {
                    Toast.makeText(getActivity(), "Pin is empty!!", Toast.LENGTH_LONG).show();
                } else if (s.length() == 4) {
                    mProgressbar.setVisibility(View.VISIBLE);
                    int pin = Integer.parseInt(loginPin.getText().toString().trim());

                    Boolean res = db.checkUserPin(pin);
                    if (res == true) {
                        closeKeyboard();
                        setupViewPager();

                        getFragmentManager().beginTransaction().remove(PinFragment.this).commitAllowingStateLoss();
                    } else {
                        Toast.makeText(getActivity(), "Pin invalid!!", Toast.LENGTH_LONG).show();
                    }
                }mProgressbar.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }





    private void setupViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = getActivity().findViewById(R.id.container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        appBarLayout.setVisibility(View.VISIBLE);
    }




    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





    @Override
    public void clickKeyboard() {
        if(getActivity() != null) {
            ((HomeActivity) getActivity()).createLoginFragment(new LoginFragment());
            getFragmentManager().beginTransaction().remove(PinFragment.this).commitAllowingStateLoss();
        }
    }
}
