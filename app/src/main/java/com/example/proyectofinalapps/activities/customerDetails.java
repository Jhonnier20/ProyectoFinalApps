package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalapps.databinding.ActivityCustomerDetailsBinding;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class customerDetails extends AppCompatActivity {

    private TextView clientName, emailClient, gymClient, accountStatusClient, membershipClient, dateClient, but;
    private Button allowEntryC;
    private ImageButton closeDetails;

    private ActivityCustomerDetailsBinding binding;

    private Person client;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = (Person) getIntent().getExtras().get("client");

        clientName = binding.clientName;
        emailClient = binding.emailClient;
        gymClient = binding.gymClient;
        accountStatusClient = binding.accountStatusClient;
        membershipClient = binding.membershipClient;
        dateClient = binding.dateClient;
        but = binding.but;

        closeDetails = binding.closeDetails;
        allowEntryC = binding.allowEntryC;

        clientName.setText(getIntent().getExtras().getString("clientName"));
        emailClient.setText(getIntent().getExtras().getString("emailName"));
        gymClient.setText(getIntent().getExtras().getString("gymClient"));
        String tmp = getIntent().getExtras().getString("status");

        if(tmp.equalsIgnoreCase("Y")){
            but.setText("Subscripción Activa");
        }else{
            but.setText("Subscripción Inactiva");
        }

        allowEntryC.setOnClickListener(this::allowEntry);

        chargeInfo();
    }

    private void chargeInfo() {
        clientName.setText(client.getFullName());
        emailClient.setText(client.getEmail());
        gymClient.setText("My Gym");
        chargeSubscription();
    }

    private void chargeSubscription() {
        FirebaseFirestore.getInstance().collection("Clientes").document(client.getId()).collection("Subscription").get().addOnSuccessListener(
                task -> {
                    for(DocumentSnapshot doc: task.getDocuments()) {
                        subscription = doc.toObject(Subscription.class);
                        break;
                    }

                    accountStatusClient.setText(subscription.getState());
                    membershipClient.setText(subscription.getMembership());
                    if(subscription.isActive()){
                        //La subscripcion esta activa
                        but.setText("Subscripción activa");
                        Date dateStart = new Date(subscription.getDateStart());
                        Date dateEnd = new Date(subscription.getDateEnd());
                        dateClient.setText(dateStart + " - " + dateEnd);
                    }else{
                        //La subscripcion esta inactiva
                        but.setText("Subscripción inactiva");
                        dateClient.setVisibility(View.INVISIBLE);
                    }

                }
        ).addOnFailureListener(
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );

        closeDetails.setOnClickListener(this::close);
    }

    private void allowEntry(View view){
        Intent intent = new Intent(this, ActivateClient_AllowEntry.class);
        intent.putExtra("title", "PERMITIR");
        startActivity(intent);
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    private void close(View view){
        finish();
    }
}