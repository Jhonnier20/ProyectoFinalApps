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
        //TODO Recibe los extras y sabe de donde vino, toca hacer un if para saber a cual va a regresar

        String origin = "gym";//TODO este string debe coger el getExtras

        String rol = getIntent().getExtras().getString("origen");


        if(origin.equals("gym")){
            Intent intent = new Intent(this, ConfigGymFragment.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, ConfigFragment.class);
            startActivity(intent);
        }
    }
}