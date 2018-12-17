package com.example.rmaahmadov.mytask;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InforationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information,container,false);
        
        return view;
    }
    
    
    public void clickContactUs(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Do you want to call us? \n +994504500501");
        builder1.setCancelable(true);

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

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
