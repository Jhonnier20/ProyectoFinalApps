package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinalapps.databinding.ActivityRegisterGymBinding;

public class RegisterGymActivity extends AppCompatActivity {

    private ActivityRegisterGymBinding binding;
    private EditText nameRegisterGym, mailRegisterGym, passwordRegisterGym, password2RegisterGym;
    private Button registerGymBtn, goToRegisterGym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterGymBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameRegisterGym = binding.nameRegisterGym;
        mailRegisterGym = binding.mailRegisterGym;
        passwordRegisterGym = binding.passwordRegisterGym;
        password2RegisterGym = binding.password2RegisterGym;
        registerGymBtn = binding.registerGymBtn;
        goToRegisterGym = binding.goToRegisterGym;

        goToRegisterGym.setOnClickListener(this::changueToLogin);
        registerGymBtn.setOnClickListener(this::registerGym);
    }

    private void changueToLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("rol", "gym");
        startActivity(intent);
        finish();
    }

    private void registerGym(View view){
        String name = nameRegisterGym.getText().toString();
        String email = mailRegisterGym.getText().toString();
        String password = passwordRegisterGym.getText().toString();
        String passsword2 = password2RegisterGym.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || passsword2.isEmpty()){
            Toast.makeText(this, "Rellene todos los espacios", Toast.LENGTH_LONG).show();
        }else{
            if(password.equals(passsword2)){
                //TODO aquí se hace lo de firebase según el register USER
                //Una vez se verifique en la base de datos se lanza lo siguiente

                Intent intent = new Intent(this, HomeStaffActivity.class);
                intent.putExtra("rol", "Staff");
                startActivity(intent);
                finish();


            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }
    }
}