package com.example.proyectofinalapps.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.FragmentHomeAdminBinding;

public class HomeAdminFragment extends Fragment {

    private RecyclerView instructorList;
    private Button addInstructor;
    private FragmentHomeAdminBinding binding;
    private View view;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    public static HomeAdminFragment newInstance() {
        HomeAdminFragment fragment = new HomeAdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        instructorList = binding.instructorList;
        addInstructor = binding.addInstructor;

        addInstructor.setOnClickListener(this::addInstructor);

        return view;
    }

    protected void addInstructor(View view){
        //TODO
    }
}