package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.databinding.ActivityRegisterUserBinding;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;

    private EditText nameRegisterET;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameRegisterET = binding.nameRegisterET;
        registerBtn = binding.registerBtn;

        registerBtn.setOnClickListener(this::registerUser);


    }

    private void registerUser(View view) {
        Intent intent = new Intent(this, HomeClientActivity.class);
        startActivity(intent);
    }
}