package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityClientQrcodeBinding;
import com.example.proyectofinalapps.fragments.FragmentHomeRegisteredUser;

public class ClientQRCodeActivity extends AppCompatActivity {

    private ImageView QRCodeImg;
    private TextView QRCodeString;
    private Button backToHome;
    private ActivityClientQrcodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientQrcodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        QRCodeImg = binding.QRCodeImg;
        QRCodeString = binding.QRCodeString;
        backToHome = binding.backToHome;

        backToHome.setOnClickListener(this::backToHome);
    }

    protected void backToHome(View view){
        Intent intent = new Intent(this, FragmentHomeRegisteredUser.class);
        startActivity(intent);
    }

    public void updateQR(){
        //TODO Actualizar imagen y cadena de texto
    }
}