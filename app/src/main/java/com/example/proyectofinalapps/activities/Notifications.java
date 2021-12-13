package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyectofinalapps.adapters.NotificationsAdapter;
import com.example.proyectofinalapps.databinding.ActivityNotificationsBinding;
import com.example.proyectofinalapps.model.Notification;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Notifications extends AppCompatActivity {

    private ActivityNotificationsBinding binding;

    private RecyclerView notifications;
    private TextView amountNotifications;
    private Button deleteNotifications;
    private ImageButton closeNotifications;
    private LinearLayoutManager manager;
    private NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notifications = binding.notifications;
        amountNotifications = binding.amountNotifications;
        deleteNotifications = binding.deleteNotifications;
        closeNotifications = binding.closeNotifications;

        manager = new LinearLayoutManager(this);
        notifications.setLayoutManager(manager);
        adapter = new NotificationsAdapter(getIntent().getExtras().getString("rol"));
        notifications.setAdapter(adapter);
        notifications.setHasFixedSize(true);

        deleteNotifications.setOnClickListener(this::deleteAll);
        closeNotifications.setOnClickListener(this::close);

        chargeNotifications();
    }

    private void chargeNotifications() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        adapter.deteleNotifications();
        FirebaseFirestore.getInstance().collection("Clientes").document(auth.getUid()).collection("Subscription").get().addOnSuccessListener(
                task -> {
                    for(DocumentSnapshot doc: task.getDocuments()) {
                        Notification notification = doc.toObject(Notification.class);
                        adapter.addNotification(notification);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }

    private void deleteAll(View view){
        //TODO
    }

    private void close(View view){
        finish();
    }
}