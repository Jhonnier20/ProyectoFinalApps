package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.databinding.FragmentConfigGymBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfigGymFragment extends Fragment {

    private FragmentConfigGymBinding binding;
    private ImageView gymImage;
    private TextView gymName, gymInstructor;
    private Button privacyPolicy, deleteProfile, editProfile, signOff;
    private View view;

    protected FirebaseUser firebaseUser;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("user", firebaseUser.getEmail());

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
        Log.e("user", firebaseUser.getEmail());
    }

    protected void editProfile(View view){
        Log.e("user", firebaseUser.getEmail());
    }

    protected void signOff(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Esta seguro que desea cerrar sesión?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    FirebaseAuth.getInstance().signOut();
                });
        builder.show();
    }
}