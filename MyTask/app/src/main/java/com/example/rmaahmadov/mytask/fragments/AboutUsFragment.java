package com.example.rmaahmadov.mytask.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rmaahmadov.mytask.R;


public class AboutUsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus,container,false);
        Button button=view.findViewById(R.id.btnCloseAbout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.coordinatorLayoutHomeActivity).setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().remove(AboutUsFragment.this).commitAllowingStateLoss();
            }
        });
        return view;
    }
}
