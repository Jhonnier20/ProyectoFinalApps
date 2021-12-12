package com.example.proyectofinalapps.fragments;

import android.content.Context;
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
import com.example.proyectofinalapps.activities.SplashActivity;
import com.example.proyectofinalapps.databinding.FragmentConfigGymBinding;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfigGymFragment extends Fragment {

    private FragmentConfigGymBinding binding;
    private ImageView gymImage;
    private TextView gymName, gymInstructor;
    private TextView clientName6;
    private Button privacyPolicy, deleteProfile, editProfile, signOff;
    private View view;

    protected FirebaseUser firebaseUser;
    private Person person;

    private PrivacyPolicyActivity privacyPolicyActivity;

    private Context context;

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
        clientName6 = binding.clientName6;
        gymInstructor = binding.gymInstructor;
        privacyPolicy = binding.privacyPolicy;
        deleteProfile = binding.deleteProfile;
        editProfile = binding.editProfile;
        signOff = binding.signOff;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("Clientes").document(firebaseUser.getUid()).get().addOnSuccessListener(
                v-> {
                   person = v.toObject(Person.class);
                }
        );

        if (person != null){
            clientName6.setText(person.getFullName());
        }

        context = getActivity();

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
                    context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                    Intent intent = new Intent(context, SplashActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                });
        builder.show();
    }
}