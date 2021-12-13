package com.example.proyectofinalapps.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectofinalapps.databinding.ActivityPrivacyPolicyBinding;
import com.example.proyectofinalapps.databinding.ActivitySolicitudDePagoClientBinding;
import com.example.proyectofinalapps.model.Notification;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SolicitudDePagoClient extends AppCompatActivity {

    private ActivitySolicitudDePagoClientBinding binding;
    private Button payBtn;
    private ImageButton closeNotifications2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySolicitudDePagoClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        payBtn = binding.payBtn;
        closeNotifications2 = binding.closeNotifications2;

        payBtn.setOnClickListener(this::requestPayment);
        closeNotifications2.setOnClickListener(this::close);
    }

    private void requestPayment(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Realizar pago")
                .setMessage("¿Esta seguro que desea realizar el pago?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    successPayment();
                    dialog.dismiss();
                });
        builder.show();
    }

    private void successPayment() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore.getInstance().collection("Clientes").document(auth.getUid()).get().addOnSuccessListener(
                per -> {
                    Person myperson = per.toObject(Person.class);
                    Notification noti = new Notification(myperson.getFullName(), "pendiente", myperson.getId());

                    //send user to Payments collection
                    FirebaseFirestore.getInstance().collection("Payments").document(myperson.getId()).set(noti).addOnSuccessListener(
                            added -> {
                                Toast.makeText(this, "Esperando confirmación de staff", Toast.LENGTH_LONG);
                                finish();
                            }
                    );
                }
        );
    }

    private void close(View view){
        finish();
    }
}