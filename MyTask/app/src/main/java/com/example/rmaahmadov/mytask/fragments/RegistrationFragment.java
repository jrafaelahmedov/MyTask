package com.example.rmaahmadov.mytask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;
import com.example.rmaahmadov.mytask.utils.SectionsPagerAdapter;

public class RegistrationFragment extends Fragment {

    private EditText mEmail, mPassword, mPin;
    private Button btnRegistration;
    private TextView moveToLogin;
    ProgressBar mProgressbar;
    DatabaseHelper db;
    RegistrationFragment fragmentRegistration;
    LoginFragment fragmentLogin;
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    AppBarLayout appBarLayout;
    Animation upToDown,downtoup;
    LinearLayout linearLayoutUpToDown,linearLayoutDownToUp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        linearLayoutDownToUp=view.findViewById(R.id.linearLayoutDowntoUp);
        linearLayoutUpToDown=view.findViewById(R.id.linearLayoutUpToDown);

        mEmail = view.findViewById(R.id.inputEmailRegistration);
        mPassword = view.findViewById(R.id.inputPasswordRegistration);
        mPin = view.findViewById(R.id.inputPin);
        btnRegistration = view.findViewById(R.id.btnRegistration);
        moveToLogin = view.findViewById(R.id.textViewMoveToLogin);
        mProgressbar = view.findViewById(R.id.progressBarRegistration);
        appBarLayout = getActivity().findViewById(R.id.appbar);
        mProgressbar.setVisibility(View.GONE);
        fragmentRegistration = new RegistrationFragment();
        fragmentLogin = new LoginFragment();
        db = new DatabaseHelper(getActivity());


        upToDown=AnimationUtils.loadAnimation(getActivity(),R.anim.uptodown);
        linearLayoutUpToDown.setAnimation(upToDown);
        downtoup=AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
        linearLayoutDownToUp.setAnimation(downtoup);


        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                mProgressbar.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragmentLogin).commit();
                getFragmentManager().beginTransaction().remove(RegistrationFragment.this).commitAllowingStateLoss();
            }
        });


        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                mProgressbar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (!mPin.getText().toString().trim().isEmpty()) {
                    int pin = Integer.parseInt(mPin.getText().toString().trim());
                    if (!db.checkUserAndMovePinPage(email, password)) {
                        long val = db.addUser(email, password, pin);
                        if (val > 0) {
                            setupViewPager();
                            getFragmentManager().beginTransaction().remove(RegistrationFragment.this).commitAllowingStateLoss();
                        }
                    }else
                    {
                        Toast.makeText(getActivity(), "This user Already Created!!", Toast.LENGTH_SHORT).show();
                        mProgressbar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Pin is empty!!", Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void setupViewPager() {
        appBarLayout.setVisibility(View.VISIBLE);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = getActivity().findViewById(R.id.container);
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imput = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imput.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
