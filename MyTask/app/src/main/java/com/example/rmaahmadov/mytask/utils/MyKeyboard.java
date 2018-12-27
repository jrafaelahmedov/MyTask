package com.example.rmaahmadov.mytask.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rmaahmadov.mytask.Interfaces.MyKeyboardClick;
import com.example.rmaahmadov.mytask.R;
import com.example.rmaahmadov.mytask.activitys.HomeActivity;
import com.example.rmaahmadov.mytask.fragments.LoginFragment;


public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDelete, buttonCancel;
    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;
    Context mContext;


    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.my_keyoard, this, true);
        button0 = findViewById(R.id.btn0);
        button0.setOnClickListener(this);
        button1 = findViewById(R.id.btn1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.btn2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.btn3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.btn4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.btn5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.btn6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.btn7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.btn8);
        button8.setOnClickListener(this);
        button9 = findViewById(R.id.btn9);
        button9.setOnClickListener(this);


        buttonCancel = findViewById(R.id.btnCancel);
        buttonCancel.setOnClickListener(this);


        buttonDelete = findViewById(R.id.btnDelete);
        buttonDelete.setOnClickListener(this);


        keyValues.put(R.id.btn0, "0");
        keyValues.put(R.id.btn1, "1");
        keyValues.put(R.id.btn2, "2");
        keyValues.put(R.id.btn3, "3");
        keyValues.put(R.id.btn4, "4");
        keyValues.put(R.id.btn5, "5");
        keyValues.put(R.id.btn6, "6");
        keyValues.put(R.id.btn7, "7");
        keyValues.put(R.id.btn8, "8");
        keyValues.put(R.id.btn9, "9");
    }

    @Override
    public void onClick(View v) {
        if (inputConnection == null)
            return;
        if (v.getId() == R.id.btnDelete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else if (v.getId() == R.id.btnCancel) {
            HomeActivity homeActivity = new HomeActivity();
            FragmentManager manager=homeActivity.getSupportFragmentManager();
            manager.beginTransaction().add(R.id.activityhomelayout, new LoginFragment()).commit();


        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }

    }


    public void setInputConnection(InputConnection in) {
        inputConnection = in;
    }


}
