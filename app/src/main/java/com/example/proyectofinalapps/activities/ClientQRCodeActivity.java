package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalapps.databinding.ActivityClientQrcodeBinding;
import com.example.proyectofinalapps.fragments.FragmentHomeRegisteredUser;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Date;

public class ClientQRCodeActivity extends AppCompatActivity {

    private ImageView QRCodeImg;
    private Button backToHome;
    private ActivityClientQrcodeBinding binding;

    private Person client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientQrcodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = (Person) getIntent().getExtras().get("client");

        QRCodeImg = binding.QRCodeImg;
        backToHome = binding.backToHome;

        backToHome.setOnClickListener(this::backToHome);

        FirebaseFirestore.getInstance().collection("Attendance").document(client.getId()).addSnapshotListener(
                (value, error) -> {
                    Toast.makeText(this, "Código QR generado exitósamente", Toast.LENGTH_SHORT).show();
                }
        );
        updateQR();
    }

    protected void backToHome(View view){
        finish();
    }

    public void updateQR(){
        //TODO Actualizar imagen y cadena de texto
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Gson gson = new Gson();
            String json = gson.toJson(client);
            Bitmap bitmap = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE, 750, 750);

            QRCodeImg.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }
}