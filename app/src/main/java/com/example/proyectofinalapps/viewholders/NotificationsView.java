package com.example.proyectofinalapps.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Notification;

public class NotificationsView extends RecyclerView.ViewHolder {

    private TextView notificationName, notificationDescription;
    private Button see;
    private Notification notification;

    public NotificationsView(@NonNull View itemView, String rol) {
        super(itemView);

        notificationName = itemView.findViewById(R.id.notificationName);
        notificationDescription = itemView.findViewById(R.id.notificationDescription);
        see = itemView.findViewById(R.id.see);

        if(rol.equals("Cliente")){
            notificationName.setVisibility(View.GONE);
            see.setVisibility(View.GONE);
        }

        see.setOnClickListener(this::see);
    }

    private void see(View view){
        //TODO
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
