package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.User;
import com.example.proyectofinalapps.databinding.ActivityHomeClientBinding;
import com.example.proyectofinalapps.fragments.HomeClientFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class HomeClientActivity extends AppCompatActivity {

    private ActivityHomeClientBinding binding;
    private BottomNavigationView navigatorClient;
    private HomeClientFragment homeClientFragment;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigatorClient = binding.navigatorClient;

        //Load User from SP
        User loadedUser = loadUser();
        if(loadedUser == null || FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            this.user = loadedUser;
        }


        homeClientFragment = HomeClientFragment.newInstance();

        showFragment(homeClientFragment);

        navigatorClient.setOnItemSelectedListener(
                MenuItem -> {
                    return true;
                }
        );

    }

    private User loadUser() {
        String json = getSharedPreferences("data", MODE_PRIVATE).getString("user", "NO_USER");
        if(json.equals("NO_USER")){
            return null;
        }else{
            return new Gson().fromJson(json, User.class);
        }
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentClientContainer, fragment);
        transaction.commit();
    }
}