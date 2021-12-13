package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import com.example.proyectofinalapps.databinding.ActivityLoginBinding;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.example.proyectofinalapps.model.User;
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
import java.util.Arrays;
import java.util.UUID;

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
        forgotPassTV.setOnClickListener(this::forgotPass);

    }

    private void forgotPass(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Olvidaste tu contraseña")
                .setMessage("Ingresa tu email para cambiar la contraseña");
        EditText emailForgot = new EditText(this);
        emailForgot.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(emailForgot);
        builder.setPositiveButton("Enviar", (dialog, id) -> {
            String sendEmail = emailForgot.getText().toString().trim();
            if(sendEmail.equals("") || sendEmail == null) {
                Toast.makeText(this, "Ingresa tu email", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(sendEmail).addOnSuccessListener(
                        task -> {
                            Toast.makeText(this, "El cambio de contraseña se ha enviado a " + sendEmail, Toast.LENGTH_LONG).show();
                        }
                ).addOnFailureListener(
                        error -> {
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                );
            }
        }).setNegativeButton("Cancelar", (dialog, id) -> {
            dialog.dismiss();
        });
        builder.show();
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

        if(validateEmail() && validatePassword()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email,
                    pass
            ).addOnSuccessListener(
                    task-> {
                        FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseFirestore.getInstance().collection("Users").document(fbuser.getUid()).get().addOnSuccessListener(
                                taskUser -> {
                                    User u = taskUser.toObject(User.class);

                                    //login de cliente
                                    if (u.getRol().equals("Client")) {
                                        FirebaseFirestore.getInstance().collection("Clientes").document(fbuser.getUid()).get().addOnSuccessListener(
                                                document -> {
                                                    User user = document.toObject(User.class);
                                                    saveUser(user);
                                                    Log.e(">>>", ">>>>>" + user.getRol());

                                                    Intent intent = new Intent(this, HomeClientActivity.class);
                                                    intent.putExtra("rol", "Client");
                                                    startActivity(intent);
                                                    finish();
                                                }
                                        );
                                    } else {
                                        FirebaseFirestore.getInstance().collection("Staff").document(fbuser.getUid()).get().addOnSuccessListener(
                                                document -> {
                                                    User user = document.toObject(User.class);
                                                    saveUser(user);
                                                    Log.e(">>>", user.getRol());

                                                    if (user.getRol().equals("Admin")) {
                                                        Intent intent = new Intent(this, HomeAdminActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    if (user.getRol().equals("Staff")) {
                                                        Intent intent = new Intent(this, HomeStaffActivity.class);
                                                        intent.putExtra("rol", "Staff");
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        );
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

    //usuario guardado
    private void saveUser(User user) {
        String json = new Gson().toJson(user);
        getSharedPreferences("data", MODE_PRIVATE).edit().putString("user", json).apply();
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

                    Toast.makeText(this, "Vamos bien xd", Toast.LENGTH_LONG).show();
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

    private boolean validateEmail() {
        String email = binding.emailLoginET.getText().toString();
        if(email.isEmpty()) {
            emailLoginET.setError("Por favor ingrese un email");
        } else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLoginET.setError("Por favor ingrese un email valido");
        } else {
            emailLoginET.setError(null);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String password = passwordLoginET.getText().toString();
        if(password.isEmpty()) {
            passwordLoginET.setError("Por favor ingrese una contraseña");
            return false;
        }
        return true;
    }
}