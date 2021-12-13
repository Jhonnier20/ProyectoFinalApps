package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.databinding.ActivityRegisterUserBinding;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;
    private EditText nameRegisterET, idRegisterET, mailRegisterET, passwordRegisterET, password2RegisterET;
    private Button registerBtn, goToRegisterTV;

    private LoginButton registerFacebookBtn;
    private CallbackManager callbackManager;

    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rol = getIntent().getExtras().getString("rol");

        nameRegisterET = binding.nameRegisterET;
        idRegisterET = binding.idRegisterET;
        mailRegisterET = binding.mailRegisterET;
        passwordRegisterET = binding.passwordRegisterET;
        password2RegisterET = binding.password2RegisterET;
        goToRegisterTV = binding.goToRegisterTV;
        registerBtn = binding.registerBtn;
        registerFacebookBtn = binding.registerFacebookBtn;

        callbackManager = CallbackManager.Factory.create();

        registerFacebookBtn.setOnClickListener(this::loginFacebook);
        goToRegisterTV.setOnClickListener(this::changeToLogin);
        registerBtn.setOnClickListener(this::registerUser);
    }

    //login con facebook
    private void loginFacebook(View view) {
        if(rol == null || rol.equals("")) {
            Toast.makeText(this, "Seleccione un rol existente", Toast.LENGTH_LONG).show();
        } else {

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(@NonNull FacebookException e) {

                }
            });
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(
                        task -> {

                            if(rol.equals("Client")) {
                                addClientFirebase();

                            } else if(rol.equals("Staff")) {
                                addStaffFirebase();
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
        User user = new User(firebaseUser.getUid(), "Staff");
        saveUser(user);
        Person person = new Person();
        person.setId(firebaseUser.getUid());
        person.setFullName(firebaseUser.getDisplayName());
        person.setEmail(firebaseUser.getEmail());
        person.setRol(user.getRol());
        person.setIsActive("N");

        validateExistUser(person, user);
    }

    private void addClientFirebase() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(firebaseUser.getUid(), "Client");
        saveUser(user);
        Person person = new Person();
        person.setId(firebaseUser.getUid());
        person.setFullName(firebaseUser.getDisplayName());
        person.setEmail(firebaseUser.getEmail());
        person.setRol(user.getRol());
        person.setIsActive("N");

        validateExistUser(person, user);

    }

    private void validateExistUser(Person person, User user) {
        switch (user.getRol()) {
            case "Client":
                FirebaseFirestore.getInstance().collection("Clientes").document(user.getId()).get().addOnSuccessListener(
                        e -> {
                            FirebaseFirestore.getInstance().collection("Users").document(user.getId()).set(user);
                            FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).set(person);
                            Subscription subscription = new Subscription(
                                    UUID.randomUUID().toString(),
                                    false,
                                    0,
                                    0,
                                    "Sin pago",
                                    "Ninguna"
                            );
                            FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).collection("Subscription")
                                    .document(subscription.getId()).set(subscription);
                            Intent intentClient = new Intent(this, HomeClientActivity.class);
                            startActivity(intentClient);
                            finish();
                        }
                );

                break;

            case "Staff":
                FirebaseFirestore.getInstance().collection("Staff").document(user.getId()).get().addOnSuccessListener(
                        e -> {
                            FirebaseFirestore.getInstance().collection("Users").document(user.getId()).set(user);
                            FirebaseFirestore.getInstance().collection("Staff").document(person.getId()).set(person);
                            Intent intentStaff = new Intent(this, HomeStaffActivity.class);
                            startActivity(intentStaff);
                            finish();
                        }
                );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void registerUser(View view) {
        String name = nameRegisterET.getText().toString();
        String id = idRegisterET.getText().toString();
        String email = mailRegisterET.getText().toString();
        String pass1 = passwordRegisterET.getText().toString();

        if(validateEmail() && validateOtherData() == 0) {
            //1. registro en db de auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email,
                    pass1
            ).addOnSuccessListener(
                    task -> {
                        Log.e(">>>", "Datos correctos");
                        //2. registar al user en la base de datos
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                        //String uid = fbUser.getUid();

                        User user = new User(fbUser.getUid(), rol);
                        Person person = new Person(fbUser.getUid(), name, email, id, rol, "N");
                        Subscription subscription = new Subscription(
                                UUID.randomUUID().toString(),
                                false,
                                0,
                                0,
                                "Sin pago",
                                "Ninguna"
                        );

                        FirebaseFirestore.getInstance().collection("Users").document(user.getId()).set(user).addOnSuccessListener(
                                firetask -> {

                                    if (user.getRol().equals("Client")) {
                                        FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).set(person).addOnSuccessListener(
                                                task2 -> {
                                                    FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).collection("Subscription")
                                                            .document(subscription.getId()).set(subscription);

                                                    saveUser(user);
                                                    Intent intent = new Intent(this, LoginActivity.class);
                                                    intent.putExtra("rol", user.getRol());
                                                    startActivity(intent);
                                                    sendVerificationEmail();
                                                    finish();
                                                }
                                        );

                                    } else if (user.getRol().equals("Staff")) {
                                        FirebaseFirestore.getInstance().collection("Staff").document(person.getId()).set(person).addOnSuccessListener(
                                                task3 -> {
                                                    saveUser(user);
                                                    Intent intent = new Intent(this, LoginActivity.class);
                                                    intent.putExtra("rol", user.getRol());
                                                    startActivity(intent);
                                                    sendVerificationEmail();
                                                    finish();
                                                }
                                        );
                                    }
                                }
                        );

                    }
            ).addOnFailureListener(
                    error -> {
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );
        }
    }

    private void sendVerificationEmail() {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(
                task -> {
                    Toast.makeText(this, "Verifique su email antes de iniciar sesion", Toast.LENGTH_LONG).show();
                }
        ).addOnFailureListener(
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );
    }

    private void changeToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        if(rol.equals("Client")) {
            intent.putExtra("rol", "Client");
        } else if(rol.equals("Staff")) {
            intent.putExtra("rol", "Staff");
        }
        startActivity(intent);
        finish();
    }

    //usuario guardado
    private void saveUser(User user) {
        String json = new Gson().toJson(user);
        getSharedPreferences("data", MODE_PRIVATE).edit().putString("user", json).apply();
    }

    private boolean validateEmail() {
        String email = binding.mailRegisterET.getText().toString();
        if(email.isEmpty()) {
            mailRegisterET.setError("Por favor ingrese un email");
        } else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            mailRegisterET.setError("Por favor ingrese un email valido");
        } else {
            mailRegisterET.setError(null);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String password = passwordRegisterET.getText().toString();
        String regEx = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$";

        if(password.isEmpty()) {
            passwordRegisterET.setError("Por favor ingrese una contraseña");
            return false;
        } else {
            CharSequence inputStr = password;

            Pattern pattern = Pattern.compile(regEx,Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(inputStr);

            if(matcher.matches()) {
                return true;
            } else {
                passwordRegisterET.setError("Ingrese una contraseña valida");
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
        if (idRegisterET.getText().toString().isEmpty()) {
            idRegisterET.setError("Ingrese su número de identidad");
            complete++;
        }
        if(!passwordRegisterET.getText().toString().isEmpty()) {
            if(!passwordRegisterET.getText().toString().equals(password2RegisterET.getText().toString())) {
                password2RegisterET.setError("No coínciden las contraseñas");
                complete++;
            }
        } else {
            password2RegisterET.setError("Ingrese una contraseña");
            complete++;
        }

        return complete;
    }

}