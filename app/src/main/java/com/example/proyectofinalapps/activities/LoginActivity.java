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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private EditText emailLoginET, passwordLoginET;
    private TextView forgotPassTV;
    private Button loginBtn, goToLoginTV;
    private LoginButton loginFaceBtn;
    private String rol;

    private CallbackManager callbackManager;

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
        loginFaceBtn = binding.loginFaceBtn;
        goToLoginTV = binding.goToLoginTV;

        callbackManager = CallbackManager.Factory.create();

        loginFaceBtn.setOnClickListener(this::loginFacebook);
        loginBtn.setOnClickListener(this::loginUser);
        goToLoginTV.setOnClickListener(this::changeToRegister);

    }

    //cambiar al registro
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

    //login
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
                    task-> {
                        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

                        //login de cliente
                        if(rol.equals("Client")) {
                            FirebaseFirestore.getInstance().collection("Clientes").document(fbuser.getUid()).get().addOnSuccessListener(
                                    document -> {
                                        User user = document.toObject(User.class);
                                        saveUser(user);
                                        Log.e(">>>", ">>>>>"+ user.getRol());

                                        Intent intent = new Intent(this, HomeClientActivity.class);
                                        intent.putExtra("rol", "Client");
                                        startActivity(intent);
                                        finish();
                                    }
                            );
                        }else{
                            FirebaseFirestore.getInstance().collection("Staff").document(fbuser.getUid()).get().addOnSuccessListener(
                                    document -> {
                                        User user = document.toObject(User.class);
                                        saveUser(user);
                                        Log.e(">>>", ">>>>>"+ user.getRol());

                                        if(user.getRol().equals("Admin")){
                                            Intent intent = new Intent(this, HomeGymActivity.class);
                                            intent.putExtra("rol", "Staff");
                                            startActivity(intent);
                                            finish();
                                        }
                                        if(user.getRol().equals("Staff")){
                                            Intent intent = new Intent(this, HomeStaffActivity.class);
                                            intent.putExtra("rol", "Staff");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                            );
                        }
                    }
            ).addOnFailureListener(
                    error->{
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        }
    }

    //usuario guardado
    private void saveUser(User user) {
        String json = new Gson().toJson(user);
        getSharedPreferences("data", MODE_PRIVATE).edit().putString("user", json).apply();
    }


    //login con facebook
    private void loginFacebook(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(@NonNull FacebookException e) {}
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(
                task -> {
                    if(rol.equals("Client")) {
                        addClientFirebase();
                        Intent intentClient = new Intent(this, HomeClientActivity.class);
                        startActivity(intentClient);
                        finish();
                    } else if(rol.equals("Staff")) {
                        addStaffFirebase();
                        Intent intentStaff = new Intent(this, HomeStaffActivity.class);
                        startActivity(intentStaff);
                        finish();
                    }
                }
        ).addOnFailureListener(
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
    }

    private void addStaffFirebase() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User();
        user.setUid(firebaseUser.getUid());
        user.setName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());

        FirebaseFirestore.getInstance().collection("Staff").document(user.getUid()).set(user);
    }

    private void addClientFirebase() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User();
        user.setUid(firebaseUser.getUid());
        user.setName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());

        FirebaseFirestore.getInstance().collection("Clientes").document(user.getUid()).set(user);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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