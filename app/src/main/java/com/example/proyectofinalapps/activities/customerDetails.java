package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityCustomerDetailsBinding;

public class customerDetails extends AppCompatActivity {

    private ImageView imageClient;
    private TextView clientName, emailClient, ageClient, gymClient, accountStatusClient, membershipClient, dateClient;
    private Button allowEntryC, but;
    private ImageButton closeDetails;

    private ActivityCustomerDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageClient = binding.imageClient;
        clientName = binding.clientName;
        emailClient = binding.emailClient;
        ageClient = binding.ageClient;
        gymClient = binding.gymClient;
        accountStatusClient = binding.accountStatusClient;
        membershipClient = binding.membershipClient;
        dateClient = binding.dateClient;
        but = binding.but;
        closeDetails = binding.closeDetails;
        allowEntryC = binding.allowEntryC;

        allowEntryC.setOnClickListener(this::allowEntry);
        closeDetails.setOnClickListener(this::close);
    }

    private void allowEntry(View view){
        Intent intent = new Intent(this, ActivateClient_AllowEntry.class);
        intent.putExtra("title", "PERMITIR");
        startActivity(intent);
    }

    private void modifySubscriptionStatus(Boolean status){
        if(status){
            //La subscripcion esta activa
            but.setText("Subscripción activa");
        }else{
            //La subscripcion esta inactiva
            but.setText("Subscripción inactiva");
        }
    }

    private void close(View view){
        finish();
    }
}