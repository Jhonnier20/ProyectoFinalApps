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
import com.example.proyectofinalapps.activities.ClientNotificationsActivity;
import com.example.proyectofinalapps.activities.ClientQRCodeActivity;
import com.example.proyectofinalapps.databinding.FragmentHomeRegisteredUserBinding;
import com.example.proyectofinalapps.model.Person;

import java.util.Locale;

public class FragmentHomeRegisteredUser extends Fragment {

    private TextView welcomeTitle,actions,membership,cutoffDate;
    private Button generateCode, requestPayment;
    private ImageView QRIcon, cashIcon, goToNotifications;
    private View view;
    private FragmentHomeRegisteredUserBinding binding;

    private Person client;

    public FragmentHomeRegisteredUser() {
        // Required empty public constructor
    }

    public static FragmentHomeRegisteredUser newInstance() {
        FragmentHomeRegisteredUser fragment = new FragmentHomeRegisteredUser();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeRegisteredUserBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        welcomeTitle = binding.welcomeTitle;
        actions = binding.actions;
        membership = binding.membership;
        cutoffDate = binding.cutoffDate;
        goToNotifications = binding.goToNotifications;
        generateCode = binding.generateCode;
        requestPayment = binding.requestPayment;
        QRIcon = binding.QRIcon;
        cashIcon = binding.cashIcon;

        generateCode.setOnClickListener(this::generateQR);
        requestPayment.setOnClickListener(this::requestPayment);
        goToNotifications.setOnClickListener(this::goToNotifications);

        chargeData();

        return view;
    }

    private void generateQR(View view){
        //TODO
        Intent intent = new Intent(getActivity(), ClientQRCodeActivity.class);
        intent.putExtra("client", client);
        startActivity(intent);
    }

    private void requestPayment(View view){
        //TODO
    }

    private void goToNotifications(View view){
        Intent intent = new Intent(getActivity(), ClientNotificationsActivity.class);
        getActivity().startActivity(intent);
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    private void chargeData() {
        String[] fullName = client.getFullName().toUpperCase(Locale.ROOT).split(" ");

        welcomeTitle.setText("BIENVENIDO " + fullName[0] + "!");
        membership.setText(" - ");
        cutoffDate.setText(" - ");

    }
}