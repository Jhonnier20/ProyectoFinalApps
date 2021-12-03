package com.example.proyectofinalapps.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.User;

public class StaffViewHolder extends RecyclerView.ViewHolder {

    private TextView nameuserrow;

    private User user;

    public StaffViewHolder(@NonNull View itemView) {
        super(itemView);

        nameuserrow = itemView.findViewById(R.id.nameuserrow);

    }

    public TextView getNameuserrow() {
        return nameuserrow;
    }

    public void setNameuserrow(TextView nameuserrow) {
        this.nameuserrow = nameuserrow;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
