package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private ImageView imageClient;
    private TextView clientName, emailClient, ageClient, gymClient, accountStatusClient, membershipClient, dateClient;
    private Button allowEntryC, but;

    private ActivityCustomerDetailsBinding binding;

    private Person client;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = (Person) getIntent().getExtras().get("client");

        imageClient = binding.imageClient;
        clientName = binding.clientName;
        emailClient = binding.emailClient;
        ageClient = binding.ageClient;
        gymClient = binding.gymClient;
        accountStatusClient = binding.accountStatusClient;
        membershipClient = binding.membershipClient;
        dateClient = binding.dateClient;
        but = binding.but;
        allowEntryC = binding.allowEntryC;

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
    }

    private void allowEntry(View view){
        Intent intent = new Intent(this, ActivateClient_AllowEntry.class);
        //TODO
        //No se como pasarle el titulo  a ActivateClient_AllowEntry
        //Me parece que es con put extra pero no recuerdo como ponerselo a un string allá
        intent.putExtra("title", "PERMITIR");
        startActivity(intent);
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }
}