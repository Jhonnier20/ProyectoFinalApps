package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.ActivityHomeStaffBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeStaffActivity extends AppCompatActivity {

    private ActivityHomeStaffBinding binding;

    private BottomNavigationView navigatorStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigatorStaff = binding.navigatorStaff;
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentStaffContainer, fragment);
        transaction.commit();
    }
}