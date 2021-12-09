package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.proyectofinalapps.databinding.ActivityNotificationsBinding;

public class Notifications extends AppCompatActivity {

    private ActivityNotificationsBinding binding;

    private RecyclerView notifications;
    private TextView amountNotifications;
    private Button deleteNotifications;
    private ImageButton closeNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notifications = binding.notifications;
        amountNotifications = binding.amountNotifications;
        deleteNotifications = binding.deleteNotifications;
        closeNotifications = binding.closeNotifications;

        deleteNotifications.setOnClickListener(this::deleteAll);
        closeNotifications.setOnClickListener(this::close);
    }

    private void deleteAll(View view){
        //TODO
    }

    private void close(View view){
        finish();
    }
}