package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.databinding.ActivityRegisterUserBinding;
import com.example.proyectofinalapps.model.Client;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding binding;
    private EditText nameRegisterET, idRegisterET, mailRegisterET, passwordRegisterET, password2RegisterET;
    private Button registerBtn, goToRegisterTV;

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

        rol = getIntent().getExtras().getString("rol");

        registerBtn.setOnClickListener(this::validate);

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
                        Integer.parseInt(idRegisterET.getText().toString()),
                        mailRegisterET.getText().toString(),
                        passwordRegisterET.getText().toString(),
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
        }

        goToRegisterTV.setOnClickListener(this::changeToLogin);

        Log.e(">>>>>>>",""+rol);

        return complete;
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

    private void registerUser(View view) {
        String name = nameRegisterET.getText().toString();
        String id = idRegisterET.getText().toString();
        String email = mailRegisterET.getText().toString();
        String pass1 = passwordRegisterET.getText().toString();
        String pass2 = password2RegisterET.getText().toString();

        if(name.isEmpty() || id.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
            Toast.makeText(this, "Rellene todos los espacios", Toast.LENGTH_LONG).show();
        }else{
            if(pass1.equals(pass2)){
                //1. registro en db de auth
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        email,
                        pass1
                ).addOnSuccessListener(
                        task -> {
                            //2. registar al user en la base de datos
                            FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = fbUser.getUid();

                            User user = new User(name, id, email, uid);

                            if(rol.equals("Client")) {

                                FirebaseFirestore.getInstance().collection("Clientes").document(user.getUid()).set(user).addOnSuccessListener(
                                        firetask->{
                                            Intent intent = new Intent(this, HomeClientActivity.class);
                                            intent.putExtra("rol", "Client");
                                            startActivity(intent);
                                            finish();
                                        }
                                );

                            } else if(rol.equals("Staff")) {

                                FirebaseFirestore.getInstance().collection("Staff").document(user.getUid()).set(user).addOnSuccessListener(
                                        firetask->{
                                            Intent intent = new Intent(this, HomeStaffActivity.class);
                                            intent.putExtra("rol", "Staff");
                                            startActivity(intent);
                                            finish();
                                        }
                                );
                            }
                        }
                ).addOnFailureListener(
                        error->{
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                );
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }
    }



}