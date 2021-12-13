package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityCustomerDetailsBinding;

public class customerDetails extends AppCompatActivity {

    private TextView clientName, emailClient, gymClient, accountStatusClient, membershipClient, dateClient, but;
    private Button allowEntryC;
    private ImageButton closeDetails;

    private ActivityCustomerDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clientName = binding.clientName;
        emailClient = binding.emailClient;
        gymClient = binding.gymClient;
        accountStatusClient = binding.accountStatusClient;
        membershipClient = binding.membershipClient;
        dateClient = binding.dateClient;
        but = binding.but;

        closeDetails = binding.closeDetails;
        allowEntryC = binding.allowEntryC;

        clientName.setText(getIntent().getExtras().getString("clientName"));
        emailClient.setText(getIntent().getExtras().getString("emailName"));
        gymClient.setText(getIntent().getExtras().getString("gymClient"));
        accountStatusClient.setText(getIntent().getExtras().getString("accountStatusClient"));
        dateClient.setText("Válida hasta: " + getIntent().getExtras().getString("dateClient"));
        String tmp = getIntent().getExtras().getString("status");

        if(tmp.equalsIgnoreCase("Y")){
            but.setText("Subscripción Activa");
        }else{
            but.setText("Subscripción Inactiva");
        }

        allowEntryC.setOnClickListener(this::allowEntry);
        closeDetails.setOnClickListener(this::close);
    }

    private void allowEntry(View view){
        Intent intent = new Intent(this, ActivateClient_AllowEntry.class);
        intent.putExtra("title", "PERMITIR");
        startActivity(intent);
    }

    private void close(View view){
        finish();
    }
}