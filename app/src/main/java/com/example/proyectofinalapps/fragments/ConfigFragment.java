package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.Notifications;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.databinding.FragmentConfigBinding;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private View view;
    private Button logoutBtn, editThisProfile, deleteThisProfile, goToPrivacyPolicy;

    public ConfigFragment() {
        // Required empty public constructor
    }

    public static ConfigFragment newInstance() {
        ConfigFragment fragment = new ConfigFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        logoutBtn = binding.logout;
        editThisProfile = binding.editThisProfile;
        deleteThisProfile = binding.deleteThisProfile;
        goToPrivacyPolicy = binding.goToPrivacyPolicy;

        editThisProfile.setOnClickListener(this::editProfile);
        deleteThisProfile.setOnClickListener(this::deleteProfile);
        goToPrivacyPolicy.setOnClickListener(this::goToPrivacyPolicy);
        logoutBtn.setOnClickListener(this::logout);

        return view;
    }

    private void editProfile(View view){

    }

    private void deleteProfile(View view){

    }

    private void goToPrivacyPolicy(View view){
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        intent.putExtra("origen","Client");
        getActivity().startActivity(intent);
    }

    private void logout(View view){

    }
}