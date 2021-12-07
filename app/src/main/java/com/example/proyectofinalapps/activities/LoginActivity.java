package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private EditText emailLoginET, passwordLoginET;
    private TextView forgotPassTV;
    private Button loginBtn, goToLoginTV;
    private RadioButton isClient, isGym;
    private String rol;
    private String rolTmp;

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
        goToLoginTV = binding.goToLoginTV;
        isClient = binding.isClient;
        isGym = binding.isGym;

        loginBtn.setOnClickListener(this::loginUser);
        goToLoginTV.setOnClickListener(this::changeToRegister);

        isClient.setOnClickListener(this::isClient);
        isGym.setOnClickListener(this::isGym);
    }

    private void isClient(View view){
        boolean checked = isClient.isChecked();
        if(checked){
            rolTmp = "Client";
            isGym.setChecked(false);
        }
    }

    private void isGym(View view){
        boolean checked = isGym.isChecked();
        if(checked){
            rolTmp = "Gym";
            isClient.setChecked(false);
        }
    }

    private void loginUser(View view) {
        String email = emailLoginET.getText().toString();
        String pass = passwordLoginET.getText().toString();

        if(email.isEmpty() || pass.isEmpty() ){
            Toast.makeText(this, "Rellene todos los espacios", Toast.LENGTH_LONG).show();
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email,
                    pass
            ).addOnSuccessListener(
                    task->{
                        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

                        FirebaseFirestore.getInstance().collection("Clientes").document(fbuser.getUid()).get().addOnSuccessListener(
                                document->{
                                    User user = document.toObject(User.class);
                                    saveUser(user);
                                    if(rolTmp.equals("Client")) {
                                        Intent intent = new Intent(this, HomeClientActivity.class);
                                        intent.putExtra("rol", "Client");
                                        startActivity(intent);
                                        finish();
                                    } else if(rolTmp.equals("Gym")) {
                                        Intent intent = new Intent(this, HomeStaffActivity.class);
                                        intent.putExtra("rol", "Staff");
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                        );
                    }
            ).addOnFailureListener(
                    error->{
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        }
    }


    private void saveUser(User user) {
        String json = new Gson().toJson(user);
        getSharedPreferences("data", MODE_PRIVATE).edit().putString("user", json).apply();
    }

    private void changeToRegister(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        if(rol.equals("Client")) {
            intent.putExtra("rol", "Client");
        } else if(rol.equals("Staff")) {
            intent.putExtra("rol", "Staff");
        }
        startActivity(intent);
        finish();
    }







    private void validate(View view) {
        boolean result[] = {validateEmail(), validatePassword()};
        if (result[0] && result[1]) {
            //loginUser();
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