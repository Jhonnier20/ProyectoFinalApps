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
    private ImageButton closeNotifications;
    private LinearLayoutManager manager;
    private NotificationsAdapter adapter;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notifications = binding.notifications;
        amountNotifications = binding.amountNotifications;
        closeNotifications = binding.closeNotifications;

        rol = getIntent().getExtras().getString("rol");
        manager = new LinearLayoutManager(this);
        notifications.setLayoutManager(manager);
        adapter = new NotificationsAdapter(rol);
        notifications.setAdapter(adapter);
        notifications.setHasFixedSize(true);

        closeNotifications.setOnClickListener(this::close);

        chargeNotifications();
    }

    private void chargeNotifications() {
        adapter.deteleNotifications();

        if(rol.equals("Client")){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore.getInstance().collection("PaymentsAnswered").document(firebaseUser.getUid()).get().addOnSuccessListener(
                    task -> {
                        Notification notification = task.toObject(Notification.class);
                        if(notification != null){
                            adapter.addNotification(notification);
                            adapter.notifyDataSetChanged();
                            amountNotifications.setText("Actualmente tienes "+adapter.getItemCount()+" notificaciones");
                        }
                    }
            );

        }else if(rol.equals("Staff")){
            FirebaseFirestore.getInstance().collection("Payments").get().addOnCompleteListener(
                    task -> {
                        for(DocumentSnapshot doc: task.getResult()) {
                            Notification notification = doc.toObject(Notification.class);
                            adapter.addNotification(notification);
                            adapter.notifyDataSetChanged();
                            amountNotifications.setText("Actualmente tienes "+adapter.getItemCount()+"notificaciones");
                        }
                    }
            );

        }
    }


    private void close(View view){
        finish();
    }
}