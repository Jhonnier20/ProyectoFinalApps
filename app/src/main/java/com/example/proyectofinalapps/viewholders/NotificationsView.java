package com.example.proyectofinalapps.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Notification;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NotificationsView extends RecyclerView.ViewHolder {

    private TextView notificationName, notificationDescription;
    private Button see;
    private Notification notification;
    protected FirebaseUser firebaseUser;

    public NotificationsView(@NonNull View itemView, String rol) {
        super(itemView);

        notificationName = itemView.findViewById(R.id.notificationName);
        notificationDescription = itemView.findViewById(R.id.notificationDescription);
        see = itemView.findViewById(R.id.see);

        if(rol.equals("Client")){
            notificationName.setText("Respuesta de solicitud");
            see.setText("X");
            see.setOnClickListener(this::see2);
        }else{
            see.setVisibility(View.VISIBLE);
            see.setOnClickListener(this::see);
        }
    }

    private void see2(View view){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("PaymentsAnswered").document(firebaseUser.getUid()).delete();
    }

    private void see(View view){
            FirebaseFirestore.getInstance().collection("Payments").addSnapshotListener(
                    (value, error) -> {
                        for (DocumentChange dc: value.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    //esta persona acabo de pagar
                                    Notification client = dc.getDocument().toObject(Notification.class);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                            .setTitle(client.getName()+" acaba de solicitar el pago")
                                            .setMessage("¿Confirmar el pago?")
                                            .setNegativeButton("NO", (dialog, id) -> {

                                                FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).get().addOnSuccessListener(
                                                        answer -> {
                                                           Notification not = answer.toObject(Notification.class);
                                                           not.setDescription("Denegada");

                                                           FirebaseFirestore.getInstance().collection("PaymentsAnswered").document(client.getId()).set(not);
                                                            FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).delete();
                                                        }
                                                );

                                                dialog.dismiss();
                                            })
                                            .setPositiveButton("SI", (dialog, id) -> {

                                                FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).get().addOnSuccessListener(
                                                        answer -> {
                                                            Notification not = answer.toObject(Notification.class);
                                                            not.setDescription("Aceptada");

                                                            FirebaseFirestore.getInstance().collection("PaymentsAnswered").document(client.getId()).set(not);
                                                            FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).delete();
                                                            Toast.makeText(view.getContext(),"Solicitud denegada con éxito", Toast.LENGTH_LONG).show();
                                                        }
                                                );

                                                FirebaseFirestore.getInstance().collection("Clientes").document(client.getId()).collection("Subscription").get().addOnSuccessListener(
                                                        sussSub -> {
                                                            for(DocumentSnapshot doc: sussSub.getDocuments()) {
                                                                Subscription sub = doc.toObject(Subscription.class);

                                                                sub.setActive(true);
                                                                sub.setDateStart(new Date().getTime());
                                                                long dateend = new Date().getTime() + 2147483647;
                                                                dateend += 432000000;
                                                                sub.setDateEnd(dateend);
                                                                sub.setMembership("Gold");
                                                                sub.setState("Pago");

                                                                FirebaseFirestore.getInstance().collection("Clientes").document(client.getId()).collection("Subscription").document(sub.getId()).set(sub).addOnSuccessListener(
                                                                        suss -> {
                                                                            FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).delete();
                                                                            Toast.makeText(view.getContext(),"Solicitud aceptada con éxito", Toast.LENGTH_LONG).show();
                                                                        }
                                                                );
                                                            }
                                                        }
                                                );

                                                dialog.dismiss();
                                            });
                                    builder.show();

                                    break;
                            }
                        }
                    }
            );
    }

    public TextView getNotificationName() {
        return notificationName;
    }

    public TextView getNotificationDescription() {
        return notificationDescription;
    }

    public Notification getNotification() {
        return notification;
    }

    public Button getSee() {
        return see;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
