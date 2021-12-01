package com.example.proyectofinalapps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinalapps.R;

public class MainActivity extends AppCompatActivity {

    private Button clientBtn;
    private Button gymBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientBtn = findViewById(R.id.clientBtn);
        gymBtn = findViewById(R.id.gymBtn);

        clientBtn.setOnClickListener(this::nextScreen);
        gymBtn.setOnClickListener(this::nextScreen);
    }

    private void nextScreen(View view) {
        switch (view.getId()) {
            case R.id.clientBtn:
                Intent intent = new Intent(this, ClientActivity.class);
                startActivity(intent);
                break;
            case R.id.gymBtn:
                Intent intent1 = new Intent(this, GymActivity.class);
                startActivity(intent1);
                break;
        }
    }
}