package com.example.proyectofinalapps.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.ActivityHomeStaffBinding;
import com.example.proyectofinalapps.fragments.HomeStaffFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeStaffActivity extends AppCompatActivity implements HomeStaffFragment.OnReadQRListener {

    private ActivityHomeStaffBinding binding;

    private BottomNavigationView navigatorStaff;

    private HomeStaffFragment homeStaffFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigatorStaff = binding.navigatorStaff;

        homeStaffFragment = HomeStaffFragment.newInstance();
        homeStaffFragment.setOnReadQRListener(this);

        showFragment(homeStaffFragment);
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentStaffContainer, fragment);
        transaction.commit();
    }

    public void readQR() {

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Log.e(">>>", "Cancelled");
                } else {
                    Log.e(">>>", "Scanned: " + result.getContents());
                }
            });

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