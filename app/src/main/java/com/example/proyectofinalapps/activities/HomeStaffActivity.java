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
import com.example.proyectofinalapps.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeStaffActivity extends AppCompatActivity implements HomeStaffFragment.OnReadQRListener {

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
        homeStaffFragment.setOnReadQRListener(this);

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

    public void readQR() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e(">>>", "Cancelled");
            } else {
                Log.e(">>>", "Scanned: " + result.getContents());
                Gson gson = new Gson();
                User user = gson.fromJson(result.getContents(), User.class);
                homeStaffFragment.getAdapter().addUser(user);
                //showFragment(homeStaffFragment);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {

                } else {
                    Log.e(">>>", "Scanned: " + result.getContents());
                    Gson gson = new Gson();
                    User user = gson.fromJson(result.getContents(), User.class);
                    homeStaffFragment.getAdapter().addUser(user);
                    showFragment(homeStaffFragment);
                }
            });

    public void onButtonClick(View view) {
        barcodeLauncher.launch(new ScanOptions());
    }

    @Override
    public void onReadQR() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("LECTOR -QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();

    }
}