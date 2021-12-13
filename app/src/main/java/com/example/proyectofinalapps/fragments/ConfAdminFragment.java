package com.example.proyectofinalapps.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.MainActivity;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.activities.SplashActivity;
import com.example.proyectofinalapps.databinding.FragmentConfAdminBinding;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfAdminFragment extends Fragment {

    private FragmentConfAdminBinding binding;
    private TextView gymNameAdmin, totalInstructors, totalClients, activeClients;
    private Button privacyPolicyAdmin, logOutAdmin;
    private PrivacyPolicyActivity privacyPolicyActivity;
    private View view;

    private Context context;

    //counters
    private int counterInstructors;
    private int counterClients;
    private int counterActiveClients;

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

        counterInstructors = 0;
        counterClients = 0;
        counterActiveClients = 0;

        gymNameAdmin = binding.gymNameAdmin;
        totalInstructors = binding.totalInstructors;
        totalClients = binding.totalClients;
        activeClients = binding.activeClients;
        privacyPolicyAdmin = binding.privacyPolicyAdmin;
        logOutAdmin = binding.logOutAdmin;

        privacyPolicyAdmin.setOnClickListener(this::goToPrivacyPolicy);
        logOutAdmin.setOnClickListener(this::logOut);

        context = getActivity();

        setGymData();

        return view;
    }

    private void setGymData() {
        FirebaseFirestore.getInstance().collection("Staff").addSnapshotListener(
                (value, error)-> {
                    for (DocumentChange dc: value.getDocumentChanges()){
                        Person person = dc.getDocument().toObject(Person.class);

                        if (!person.getRol().equals("Admin")){
                            counterInstructors++;
                        }
                    }
                    totalInstructors.setText("Total instructores: "+counterInstructors);
                }
        );

        FirebaseFirestore.getInstance().collection("Clientes").addSnapshotListener(
                (value, error)-> {
                    for (DocumentChange dc:value.getDocumentChanges()){
                        Person person = dc.getDocument().toObject(Person.class);
                        counterClients++;
                        if (person.getIsActive().equals("Y")){
                            counterActiveClients++;
                        }
                    }
                    totalClients.setText("Total clientes: "+counterClients);
                    activeClients.setText("Clientes activos "+counterActiveClients);
                }
        );


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
                    com.facebook.login.LoginManager.getInstance().logOut();
                    context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                    getActivity().finish();
                });
        builder.show();
    }
}
