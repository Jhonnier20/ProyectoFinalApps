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

import com.example.proyectofinalapps.activities.MainActivity;
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
    private Button privacyPolicy, deleteProfile, signOff;
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
        signOff = binding.signOff;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("Staff").document(firebaseUser.getUid()).get().addOnSuccessListener(
                v-> {
                   person = v.toObject(Person.class);
                   clientName6.setText(person.getFullName());
                }
        );

        if (person != null){
            clientName6.setText(person.getFullName());
        }

        context = getActivity();

        privacyPolicy.setOnClickListener(this::goToPrivacyPolicy);
        deleteProfile.setOnClickListener(this::deleteProfile);
        signOff.setOnClickListener(this::signOff);

        return view;
    }

    protected void goToPrivacyPolicy(View view){
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        getActivity().startActivity(intent);
    }

    protected void deleteProfile(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Eliminar mi cuenta")
                .setMessage("¿Esta seguro que deseas eliminar tu cuenta?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseFirestore.getInstance().collection("Staff").document(firebaseUser.getUid()).delete();
                    context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                    getActivity().finish();
                });
        builder.show();

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
                    com.facebook.login.LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                    context.startActivity(intent);
                    dialog.dismiss();
                    getActivity().finish();

                });
        builder.show();
    }
}