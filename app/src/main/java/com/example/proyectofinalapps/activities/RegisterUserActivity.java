package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.databinding.ActivityRegisterUserBinding;
import com.example.proyectofinalapps.model.Client;

import java.util.PrimitiveIterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;

    private EditText nameRegisterET;
    private EditText userIdET;
    private EditText emailRegisterET;
    private EditText passRegisterET;
    private EditText confPassET;
    private Button registerBtn;
    private TextView loginRegisterTV;

    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameRegisterET = binding.nameRegisterET;
        userIdET = binding.userIdET;
        emailRegisterET = binding.emailRegisterET;
        passRegisterET = binding.passRegisterET;
        confPassET = binding.confPassET;
        registerBtn = binding.registerBtn;
        loginRegisterTV = binding.loginRegisterTV;

        rol = getIntent().getExtras().getString("rol");

        registerBtn.setOnClickListener(this::validate);

    }

    private void registerUser(View view) {

    }


    private void validate(View view) {
        boolean result[] = {validateEmail(), validatePassword()};
        if (result[0] && result[1] && validateOtherData() == 0) {
            //next screen
            if(rol.equals("Client")) {
                Intent intent = new Intent(this, HomeClientActivity.class);
                Client client = new Client(
                        UUID.randomUUID().toString(),
                        nameRegisterET.getText().toString(),
                        Integer.parseInt(userIdET.getText().toString()),
                        emailRegisterET.getText().toString(),
                        passRegisterET.getText().toString(),
                        false
                );
                intent.putExtra("client", client);
                startActivity(intent);
            } else if(rol.equals("Staff")) {
                Intent intent1 = new Intent(this, HomeStaffActivity.class);
                startActivity(intent1);
            }
        }
    }

    private boolean validateEmail() {
        String email = binding.emailRegisterET.getText().toString();
        if(email.isEmpty()) {
            emailRegisterET.setError("Por favor ingrese un email");
        } else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRegisterET.setError("Por favor ingrese un email valido");
        } else {
            emailRegisterET.setError(null);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String password = passRegisterET.getText().toString();
        String regEx = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$";

        if(password.isEmpty()) {
            passRegisterET.setError("Por favor ingrese una contraseña");
            return false;
        } else {
            CharSequence inputStr = password;

            Pattern pattern = Pattern.compile(regEx,Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(inputStr);

            if(matcher.matches()) {
                return true;
            } else {
                passRegisterET.setError("Ingrese una contraseña valida");
                return false;
            }
        }
    }

    private int validateOtherData() {
        int complete = 0;
        if(nameRegisterET.getText().toString().isEmpty()) {
            nameRegisterET.setError("Por favor ingrese un nombre");
            complete++;
        }
        if (userIdET.getText().toString().isEmpty()) {
            userIdET.setError("Ingrese su número de identidad");
            complete++;
        }
        if(!passRegisterET.getText().toString().isEmpty()) {
            if(!passRegisterET.getText().toString().equals(confPassET.getText().toString())) {
                confPassET.setError("No coínciden las contraseñas");
                complete++;
            }
        }

        return complete;
    }
}