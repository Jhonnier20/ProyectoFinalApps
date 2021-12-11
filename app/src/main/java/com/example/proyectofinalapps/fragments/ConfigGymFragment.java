package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectofinalapps.activities.ActivateClient_AllowEntry;
import com.example.proyectofinalapps.activities.LoginActivity;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.databinding.FragmentConfigGymBinding;

public class ConfigGymFragment extends Fragment {

    private FragmentConfigGymBinding binding;
    private ImageView gymImage;
    private TextView gymName, gymInstructor;
    private Button privacyPolicy, deleteProfile, editProfile, signOff;
    private View view;

    private PrivacyPolicyActivity privacyPolicyActivity;

    public ConfigGymFragment() {
        // Required empty public constructor
    }

    public static ConfigGymFragment newInstance() {
        ConfigGymFragment fragment = new ConfigGymFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfigGymBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        gymImage = binding.gymImage;
        gymName = binding.gymName;
        gymInstructor = binding.gymInstructor;
        privacyPolicy = binding.privacyPolicy;
        deleteProfile = binding.deleteProfile;
        editProfile = binding.editProfile;
        signOff = binding.signOff;

        privacyPolicy.setOnClickListener(this::goToPrivacyPolicy);
        deleteProfile.setOnClickListener(this::deleteProfile);
        editProfile.setOnClickListener(this::editProfile);
        signOff.setOnClickListener(this::signOff);

        return view;
    }

    protected void goToPrivacyPolicy(View view){
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        getActivity().startActivity(intent);
    }

    protected void deleteProfile(View view){
        //TODO
    }

    protected void editProfile(View view){
        //TODO
    }

    protected void signOff(View view){
        //TODO
    }
}