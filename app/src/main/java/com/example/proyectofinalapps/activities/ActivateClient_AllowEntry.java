package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityActivateClientAllowEntryBinding;
import com.example.proyectofinalapps.fragments.ConfigGymFragment;

public class ActivateClient_AllowEntry extends AppCompatActivity {

    private TextView title_Activate_Allow;
    private EditText code;
    private Button allow, scanQR;
    private ImageButton goToClients;
    private ActivityActivateClientAllowEntryBinding binding;
    public String title = "ACTIVAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivateClientAllowEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title_Activate_Allow = binding.titleActivateAllow;
        code = binding.code;
        allow = binding.allow;
        scanQR = binding.scanQR;
        goToClients = binding.goToClients;

        //Esto se hace porque lo unico que cambia en estas dos actividades es el titulo
        //Entonces solo cambio el titulo y la funcionalidad sigue igual

        if(title.equals("ACTIVAR")){
            title_Activate_Allow.setText("ACTIVAR CLIENTE");
        }else{
            title_Activate_Allow.setText("PERMITIR ENTRADA");
        }

        allow.setOnClickListener(this::allowEntry);
        scanQR.setOnClickListener(this::scanQR);
        goToClients.setOnClickListener(this::goToClients);
    }

    protected void allowEntry(View view){
        //TODO Aceptar entrada
        //Supongo que se modificará algo en la base de datos
    }

    protected void scanQR(View view){
        //TODO escanear QR
    }

    protected void goToClients(View view){
        Intent intent = new Intent(this, HomeStaffActivity.class);
        startActivity(intent);
    }
}