package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.databinding.ActivityGymBinding;

public class GymActivity extends AppCompatActivity {

    private Button registerStaffBtn;
    private Button loginBtnGym;

    private ActivityGymBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGymBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerStaffBtn = binding.registerStaffBtn;
        loginBtnGym = binding.loginBtnGym;

        registerStaffBtn.setOnClickListener(this::register);
        loginBtnGym.setOnClickListener(this::login);
    }

    private void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("rol", "Staff");
        startActivity(intent);
        finish();
    }

    private void register(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        intent.putExtra("rol", "Staff");
        startActivity(intent);
        finish();
    }

}