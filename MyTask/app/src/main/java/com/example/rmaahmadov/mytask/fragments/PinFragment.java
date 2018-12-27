package com.example.rmaahmadov.mytask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.Interfaces.MyKeyboardClick;
import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.MyKeyboard;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;

import java.util.ArrayList;

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
        MyKeyboard keyboard =view.findViewById(R.id.keyboard);

        loginPin.setRawInputType(InputType.TYPE_CLASS_TEXT);
        loginPin.setTextIsSelectable(true);

        InputConnection in = loginPin.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(in);
        mProgressbar = view.findViewById(R.id.progressBarPin);
        appBarLayout = getActivity().findViewById(R.id.appbar);
        mProgressbar.setVisibility(View.GONE);
        db = new DatabaseHelper(getActivity());
        loginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null && loginPin.getText() == null) {
                    Toast.makeText(getActivity(), "Pin is empty!!", Toast.LENGTH_LONG).show();
                } else if (s.length() == 4) {
                    int pin = Integer.parseInt(loginPin.getText().toString().trim());
                    Boolean res = db.checkUserPin(pin);
                    if (res == true) {
                        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction().remove(PinFragment.this).commitAllowingStateLoss();
                        closeKeyboard();
                        setupViewPager();
                    } else {
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
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragmentContainer, new LoginFragment()).commit();
        getFragmentManager().beginTransaction().remove(PinFragment.this).commitAllowingStateLoss();
    }
}
