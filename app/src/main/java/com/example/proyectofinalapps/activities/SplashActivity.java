package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        login();
    }

    private void login() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        if(auth == null) {
            getSharedPreferences("data", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            FirebaseFirestore.getInstance().collection("Users").document(auth.getUid()).get().addOnCompleteListener(
                    task -> {
                        user = task.getResult().toObject(User.class);
                        if (user==null){
                            FirebaseAuth.getInstance().signOut();
                            getSharedPreferences("data", MODE_PRIVATE).edit().clear().apply();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Log.e("<><<><>", user.getRol());
                            saveUser(user);
                            if(user.getRol().equals("Client")) {
                                Intent intentC = new Intent(this, HomeClientActivity.class);
                                startActivity(intentC);

                            } else if(user.getRol().equals("Staff")) {
                                Intent intentS = new Intent(this, HomeStaffActivity.class);
                                startActivity(intentS);

                            } else if(user.getRol().equals("Admin")) {
                                Intent intentA = new Intent(this, HomeAdminActivity.class);
                                startActivity(intentA);
                            }
                        }
                    }
            );
        }
    }

    private void saveUser(User user) {
        String json = new Gson().toJson(user);
        getSharedPreferences("data", MODE_PRIVATE).edit().putString("user", json).apply();
    }
}