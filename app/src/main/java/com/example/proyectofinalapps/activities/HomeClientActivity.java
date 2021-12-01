package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.ActivityHomeClientBinding;
import com.example.proyectofinalapps.fragments.HomeClientFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeClientActivity extends AppCompatActivity {

    private ActivityHomeClientBinding binding;

    private BottomNavigationView navigatorClient;

    private HomeClientFragment homeClientFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigatorClient = binding.navigatorClient;

        homeClientFragment = HomeClientFragment.newInstance();

        showFragment(homeClientFragment);

        navigatorClient.setOnItemSelectedListener(
                MenuItem -> {


                    return true;
                }
        );

    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentClientContainer, fragment);
        transaction.commit();
    }
}