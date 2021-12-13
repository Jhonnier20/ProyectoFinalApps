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
import android.widget.Toast;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.MainActivity;
import com.example.proyectofinalapps.activities.Notifications;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.activities.SplashActivity;
import com.example.proyectofinalapps.databinding.FragmentConfigBinding;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;
import com.example.proyectofinalapps.model.Subscription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private View view;
    private Button logoutBtn, deleteThisProfile, goToPrivacyPolicy;

    private Context context;

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
        deleteThisProfile = binding.deleteThisProfile;
        goToPrivacyPolicy = binding.goToPrivacyPolicy;

        deleteThisProfile.setOnClickListener(this::deleteProfile);
        goToPrivacyPolicy.setOnClickListener(this::goToPrivacyPolicy);
        logoutBtn.setOnClickListener(this::logout);

        context = getActivity();

        return view;
    }


    private void deleteProfile(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Eliminar mi cuenta")
                .setMessage("¿Esta seguro que deseas eliminar tu cuenta?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    String idToDelete = firebaseUser.getUid();

                    FirebaseAuth.getInstance().getCurrentUser().delete().addOnSuccessListener(
                            userDeleted->{
                                Log.e("LLLL", "exitoso borrando auth");
                                FirebaseFirestore.getInstance().collection("Clientes").document(idToDelete).collection("Subscription").get().addOnSuccessListener(
                                        suss-> {
                                            for (DocumentSnapshot ds: suss.getDocuments()){
                                                Subscription sub = ds.toObject(Subscription.class);

                                                FirebaseFirestore.getInstance().collection("Clientes").document(idToDelete).collection("Subscription").document(sub.getId()).delete().addOnSuccessListener(
                                                        deleted -> {
                                                            Log.e("LLLL", "exitoso borrando susbs");
                                                            FirebaseFirestore.getInstance().collection("Clientes").document(idToDelete).delete().addOnSuccessListener(
                                                                    personDeleted -> {
                                                                        Log.e("LLLL", "exitoso borrando per");
                                                                        context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                                                                        FirebaseAuth.getInstance().signOut();
                                                                        Intent intent = new Intent(context, MainActivity.class);
                                                                        context.startActivity(intent);
                                                                        dialog.dismiss();
                                                                        getActivity().finish();
                                                                    }
                                                            );
                                                        }
                                                );
                                            }
                                        }
                                );
                            }
                    ).addOnFailureListener(
                            fail->{
                                Toast.makeText(getActivity(), fail.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    );

                });
        builder.show();
    }

    private void goToPrivacyPolicy(View view){
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        intent.putExtra("origen","Client");
        getActivity().startActivity(intent);
    }

    private void logout(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Esta seguro que desea cerrar sesión?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    FirebaseAuth.getInstance().signOut();
                    context.getSharedPreferences("data", context.MODE_PRIVATE).edit().clear().apply();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                    getActivity().finish();
                });
        builder.show();
    }
}