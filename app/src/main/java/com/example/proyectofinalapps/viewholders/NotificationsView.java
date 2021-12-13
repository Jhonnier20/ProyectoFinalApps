package com.example.proyectofinalapps.viewholders;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NotificationsView extends RecyclerView.ViewHolder {

    private TextView notificationName, notificationDescription;
    private Button see;
    private Notification notification;

    public NotificationsView(@NonNull View itemView, String rol) {
        super(itemView);

        notificationName = itemView.findViewById(R.id.notificationName);
        notificationDescription = itemView.findViewById(R.id.notificationDescription);
        see = itemView.findViewById(R.id.see);

        if(rol.equals("Client")){
            notificationName.setVisibility(View.INVISIBLE);
            see.setVisibility(View.INVISIBLE);
        }else{
            notificationName.setVisibility(View.VISIBLE);
            see.setVisibility(View.VISIBLE);
            see.setOnClickListener(this::see);
        }
    }

    private void see(View view){
            FirebaseFirestore.getInstance().collection("Payments").addSnapshotListener(
                    (value, error) -> {

                        for (DocumentChange dc: value.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    //esta persona acabo de pagar
                                    Person client = dc.getDocument().toObject(Person.class);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                            .setTitle(client.getFullName()+" acaba de realizar pago")
                                            .setMessage("¿Confirmar el pago?")
                                            .setNegativeButton("NO", (dialog, id) -> {
                                                FirebaseFirestore.getInstance().collection("Payments").document(client.getId()).delete();

                                                dialog.dismiss();
                                            })
                                            .setPositiveButton("SI", (dialog, id) -> {

                                                //send user to PaymentsAnswered collection
                                                FirebaseFirestore.getInstance().collection("PaymentsAnswered").document(client.getId()).set(client);


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

    public Button getSee() {
        return see;
    }

    public void setNotification(Notification notification) {
    }
}
