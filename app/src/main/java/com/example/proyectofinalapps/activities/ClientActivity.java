package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.databinding.ActivityClientBinding;

public class ClientActivity extends AppCompatActivity {

    private Button registerClientBtn;
    private Button loginClientBtn;

    private ActivityClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerClientBtn = binding.registerClientBtn;
        loginClientBtn = binding.loginClientBtn;

        registerClientBtn.setOnClickListener(this::registerClient);
        loginClientBtn.setOnClickListener(this::loginClient);

    }

    private void loginClient(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("rol", "Client");
        startActivity(intent);
    }

    private void registerClient(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        intent.putExtra("rol", "Client");
        startActivity(intent);
    }
}