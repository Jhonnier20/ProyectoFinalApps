package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.databinding.FragmentConfAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ConfAdminFragment extends Fragment {

    private FragmentConfAdminBinding binding;
    private TextView gymNameAdmin, totalInstructors, activeInstructors, totalClients,activeClients;
    private Button privacyPolicyAdmin, logOutAdmin;
    private PrivacyPolicyActivity privacyPolicyActivity;
    private View view;

    public ConfAdminFragment() {
        // Required empty public constructor
    }

    public static ConfAdminFragment newInstance() {
        ConfAdminFragment fragment = new ConfAdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfAdminBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        gymNameAdmin = binding.gymNameAdmin;
        totalInstructors = binding.totalInstructors;
        activeInstructors  = binding.activeInstructors;
        totalClients = binding.totalClients;
        activeClients = binding.activeClients;
        privacyPolicyAdmin = binding.privacyPolicyAdmin;
        logOutAdmin = binding.logOutAdmin;

        privacyPolicyAdmin.setOnClickListener(this::goToPrivacyPolicy);
        logOutAdmin.setOnClickListener(this::logOut);

        return view;
    }

    protected void goToPrivacyPolicy(View view){
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        getActivity().startActivity(intent);
    }

    protected void logOut(View view){
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
        //FirebaseAuth.getInstance().signOut();
    }
}