package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.databinding.ActivityLoginBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private EditText emailLoginET;
    private EditText passwordLoginET;
    private TextView forgotPassTV;
    private Button loginBtn;
    private TextView registerInTV;

    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rol = getIntent().getExtras().getString("rol");

        emailLoginET = binding.emailLoginET;
        passwordLoginET = binding.passwordLoginET;
        forgotPassTV = binding.forgotPassTV;
        loginBtn = binding.loginBtn;
        registerInTV = binding.registerInTV;

        registerInTV.setOnClickListener(this::registerUser);
        loginBtn.setOnClickListener(this::validate);

    }

    private void registerUser(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        if(rol.equals("Client")) {
            intent.putExtra("rol", "Client");
        } else if(rol.equals("Gym")) {
            intent.putExtra("rol", "Gym");
        } else if(rol.equals("Staff")) {
            intent.putExtra("rol", "Staff");
        }
        startActivity(intent);
    }


    //https://www.youtube.com/watch?v=vwD00u6Lshw - Link video para validaciones de correo y password

    private void validate(View view) {
        boolean result[] = {validateEmail(), validatePassword()};
        if (result[0] && result[1]) {
            //next screen
        }
    }

    private boolean validateEmail() {
        String email = binding.emailLoginET.getText().toString();
        if(email.isEmpty()) {
            emailLoginET.setError("Por favor ingrese un email");
        } else if(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLoginET.setError("Por favor ingrese un email valido");
        } else {
            emailLoginET.setError(null);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String password = passwordLoginET.getText().toString();
        String regEx = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$";

        if(password.isEmpty()) {
            passwordLoginET.setError("Por favor ingrese una contraseña");
            return false;
        } else {
            CharSequence inputStr = password;

            Pattern pattern = Pattern.compile(regEx,Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(inputStr);

            if(matcher.matches()) {
                return true;
            } else {
                return false;
            }
        }

    }

}