package com.example.rmaahmadov.mytask.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.utils.DatabaseHelper;

public class LoginFragment extends Fragment {

    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextView moveToRegistartion;
    private ProgressBar mProgressbar;
    DatabaseHelper db;
    RegistrationFragment fragmentRegistration;
    LoginFragment fragmentLogin;
    PinFragment fragmentPin;
    CheckBox checkBoxLogin;
    Animation upToDown,downtoup;
    LinearLayout linearLayoutUpToDown,linearLayoutDownToUp;
    DrawerLayout mynav;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        linearLayoutDownToUp=view.findViewById(R.id.linearLayoutDowntoUp);
        linearLayoutUpToDown=view.findViewById(R.id.linearLayoutUpToDown);
        mEmail = view.findViewById(R.id.inputEmailLogin);
        mPassword = view.findViewById(R.id.inputPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        mProgressbar = view.findViewById(R.id.progressBarLogin);
        checkBoxLogin = view.findViewById(R.id.checkboxLogin);
        moveToRegistartion = view.findViewById(R.id.textViewMoveToRegistration);


        mynav=getActivity().findViewById(R.id.activityhomelayout);
        mynav.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



        utils();
        loadAnimation();
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeKeyboard();
                return true;
            }
        });

        mProgressbar.setVisibility(View.GONE);

        moveToRegistartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                mProgressbar.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.activitymaincontainer, fragmentRegistration).commit();
//                getFragmentManager().beginTransaction().remove(LoginFragment.this).commitAllowingStateLoss();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                closeKeyboard();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                Boolean res = db.checkUserAndMovePinPage(email, password);
                if (res == true) {
                    if (checkBoxLogin.isChecked()) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("SavedUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.apply();
                    }
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.activitymaincontainer, fragmentPin).commit();
                } else {
                    Toast.makeText(getActivity(), "Email or Password invalid!!", Toast.LENGTH_LONG).show();
                }
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }





    public void utils(){
        fragmentRegistration = new RegistrationFragment();
        fragmentLogin = new LoginFragment();
        fragmentPin = new PinFragment();
        db = new DatabaseHelper(getActivity());
    }




    public void loadAnimation(){
        upToDown=AnimationUtils.loadAnimation(getActivity(),R.anim.spashscreenanimation);
        downtoup=AnimationUtils.loadAnimation(getActivity(),R.anim.spashscreenanimation);
        linearLayoutUpToDown.setAnimation(downtoup);
        linearLayoutDownToUp.setAnimation(downtoup);
    }





    private void closeKeyboard(){
        View view =this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager input =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
