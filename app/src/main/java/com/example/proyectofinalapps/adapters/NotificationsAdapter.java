package com.example.proyectofinalapps.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Notification;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.viewholders.NotificationsView;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsView> implements NotificationsView.OnDeleteNotificationListener {

    //private ArrayList<Notification> notifications;
    private ArrayList<Notification> notifications;
    public String rol;

    private OnAmountNotiListener onAmountNotiListener;

    public NotificationsAdapter(String rol){
        notifications = new ArrayList<>();
        this.rol = rol;
    }

    public void addNotification(Notification notification){
        notifications.add(notification);
        notifyItemInserted(notifications.size()-1);
    }

    @NonNull
    @Override
    public NotificationsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notificationrow, parent, false);
        NotificationsView skeleton = new NotificationsView(view,rol);
        skeleton.setListener(this);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsView skeleton, int position) {
        Notification notification = notifications.get(position);
        skeleton.setNotification(notification);
        //Log.e("000000000000000000000000000",notification.getName() + "  " + notification.getDescription());
        skeleton.getNotificationName().setText(notification.getName());
        if(rol.equals("Client")){
            skeleton.getNotificationDescription().setText("Su solicitud fue: "+notification.getDescription());
        }else{
            skeleton.getNotificationDescription().setText("Ha enviado una nueva solicitud");
        }


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void deteleNotifications() {
        notifyItemRangeRemoved(0, notifications.size());
        notifications.clear();
    }

    @Override
    public void onDeleteNotification(Notification n) {
        int i = notifications.indexOf(n);
        notifyItemRemoved(i);
        notifications.remove(i);
        onAmountNotiListener.onAmountNoti();
    }


    public OnAmountNotiListener getOnAmountNotiListener() {
        return onAmountNotiListener;
    }

    public void setOnAmountNotiListener(OnAmountNotiListener onAmountNotiListener) {
        this.onAmountNotiListener = onAmountNotiListener;
    }

    public interface OnAmountNotiListener {
        void onAmountNoti();
    }
}
