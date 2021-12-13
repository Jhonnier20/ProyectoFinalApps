package com.example.proyectofinalapps.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalapps.databinding.ActivityActivateClientAllowEntryBinding;
import com.example.proyectofinalapps.fragments.ConfigGymFragment;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;

public class ActivateClient_AllowEntry extends AppCompatActivity {

    private TextView title_Activate_Allow, changingText;
    private Button scanQR;
    private ImageButton goToClients;
    private ActivityActivateClientAllowEntryBinding binding;
    public String title;

    private OnActivedClient listener;

    public ActivateClient_AllowEntry() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivateClientAllowEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title_Activate_Allow = binding.titleActivateAllow;
        scanQR = binding.scanQR;
        goToClients = binding.goToClients;
        changingText = binding.changingText;

        title = getIntent().getExtras().getString("title");

        if(title.equals("ACTIVAR")){
            title_Activate_Allow.setText("ACTIVAR CLIENTE");
            changingText.setText("Escanea el código QR para activar el cliente");
            scanQR.setOnClickListener(this::scanQR);
        }else{
            title_Activate_Allow.setText("PERMITIR ENTRADA");
            changingText.setText("Escanea el código QR para permitirle la entrada al cliente");
            scanQR.setOnClickListener(this::allowEntry);
        }

        goToClients.setOnClickListener(this::goToClients);
    }

    protected void allowEntry(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("LECTOR -QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    protected void scanQR(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("LECTOR -QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
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
                Person client = gson.fromJson(result.getContents(), Person.class);

                if(title.equals("ACTIVAR")) {
                    client.setIsActive("Y");
                    FirebaseFirestore.getInstance().collection("Clientes").document(client.getId()).update("isActive", client.getIsActive()).addOnCompleteListener(
                            task -> {
                                listener.onActivedClient(client);
                                finish();
                                //homeStaffFragment.getAdapter().addClient(client);
                            }
                    );

                } else if(title.equals("PERMITIR")) {
                    FirebaseFirestore.getInstance().collection("Attendance").document(client.getId()).set(client).addOnSuccessListener(
                            task -> {
                                FirebaseFirestore.getInstance().collection("Attendance").document(client.getId()).update("dateOfEntry", new Date().getTime());
                                Toast.makeText(this, "Ingreso permitido - " + new Date().toString(), Toast.LENGTH_LONG).show();
                            }
                    );
                }

                //showFragment(homeStaffFragment);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void goToClients(View view){
        finish();
    }


    public OnActivedClient getListener() {
        return listener;
    }

    public void setListener(OnActivedClient listener) {
        this.listener = listener;
    }

    public interface OnActivedClient {
        void onActivedClient(Person client);
    }
}