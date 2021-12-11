package com.example.proyectofinalapps.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.ActivityHomeStaffBinding;
import com.example.proyectofinalapps.fragments.ConfigGymFragment;
import com.example.proyectofinalapps.fragments.HomeStaffFragment;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeStaffActivity extends AppCompatActivity {

    private ActivityHomeStaffBinding binding;

    private BottomNavigationView navigatorStaff;

    private HomeStaffFragment homeStaffFragment;
    private ConfigGymFragment configGymFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigatorStaff = binding.navigatorStaff;

        homeStaffFragment = HomeStaffFragment.newInstance();
        configGymFragment = ConfigGymFragment.newInstance();

        showFragment(homeStaffFragment);

        navigatorStaff.setOnItemSelectedListener(menuItem->{

            if(menuItem.getItemId() == R.id.clientsStaffMenu){
                showFragment(homeStaffFragment);
            }else if(menuItem.getItemId() == R.id.configStaffMenu){
                showFragment(configGymFragment);
            }
            return true;
        });
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentStaffContainer, fragment);
        transaction.commit();
    }

}