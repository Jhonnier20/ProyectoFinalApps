package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.proyectofinalapps.databinding.ActivityPrivacyPolicyBinding;
import com.example.proyectofinalapps.fragments.ConfigFragment;
import com.example.proyectofinalapps.fragments.ConfigGymFragment;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ImageButton goToConfig;
    private ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goToConfig = binding.goToConfig;

        goToConfig.setOnClickListener(this::goToConfig);
    }

    private void goToConfig(View view){
        finish();
    }
}