package com.example.proyectofinalapps.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectofinalapps.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileClientFragment extends Fragment {


    public ProfileClientFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileClientFragment newInstance() {
        ProfileClientFragment fragment = new ProfileClientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_client, container, false);
    }
}