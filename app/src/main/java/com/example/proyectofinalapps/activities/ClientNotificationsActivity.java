package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityClientNotificationsBinding;

public class ClientNotificationsActivity extends AppCompatActivity {

    private ActivityClientNotificationsBinding binding;
    private RecyclerView clientNotifications;
    private TextView amountNotifications2;
    private Button deleteClientNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clientNotifications = binding.clientNotifications;
        amountNotifications2 = binding.amountNotifications2;
        deleteClientNotifications = binding.deleteClientNotifications;

        deleteClientNotifications.setOnClickListener(this::deleteNotifications);
    }

    private void deleteNotifications(View view){
        //TODO
    }
}