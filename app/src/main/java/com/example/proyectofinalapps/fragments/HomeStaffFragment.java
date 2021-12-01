package com.example.proyectofinalapps.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;

public class HomeStaffFragment extends Fragment {

    private FragmentHomeStaffBinding binding;

    private View view;

    public HomeStaffFragment() {
        // Required empty public constructor
    }

    public static HomeStaffFragment newInstance(String param1, String param2) {
        HomeStaffFragment fragment = new HomeStaffFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeStaffBinding.inflate(inflater, container, false);
        view = binding.getRoot();



        return view;
    }
}