package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.databinding.ActivityGymBinding;

public class GymActivity extends AppCompatActivity {

    private Button registerMyGymBtn;
    private Button registerStaffBtn;
    private Button loginBtnGym;

    private ActivityGymBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGymBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerMyGymBtn = binding.registerMyGymBtn;
        registerStaffBtn = binding.registerStaffBtn;
        loginBtnGym = binding.loginBtnGym;

        registerMyGymBtn.setOnClickListener(this::registerGym);
        registerStaffBtn.setOnClickListener(this::registerStaff);
        loginBtnGym.setOnClickListener(this::login);
    }

    private void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.putExtra("rol", "gym");
        startActivity(intent);
    }

    private void registerStaff(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        intent.putExtra("rol", "Staff");
        startActivity(intent);
    }

    private void registerGym(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        intent.putExtra("rol", "Gym");
        startActivity(intent);
    }
}