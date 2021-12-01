package com.example.proyectofinalapps.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.databinding.ActivityRegisterUserBinding;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}