package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.ActivityHomeAdminBinding;
import com.example.proyectofinalapps.fragments.ConfAdminFragment;
import com.example.proyectofinalapps.fragments.HomeAdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends AppCompatActivity {

    private ConfAdminFragment confAdminFragment;
    private HomeAdminFragment homeAdminFragment;
    private BottomNavigationView adminNavigator;
    private ActivityHomeAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adminNavigator = binding.adminNavigator;

        confAdminFragment = ConfAdminFragment.newInstance();
        homeAdminFragment = HomeAdminFragment.newInstance();

        showFragment(homeAdminFragment);

        adminNavigator.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.clientsAdmin){
                showFragment(homeAdminFragment);
            }else if(menuItem.getItemId() == R.id.confgAdmin){
                showFragment(confAdminFragment);
            }
            return true;
        });
    }

    public void showFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentAdminContainer, fragment);
        transaction.commit();
    }
}