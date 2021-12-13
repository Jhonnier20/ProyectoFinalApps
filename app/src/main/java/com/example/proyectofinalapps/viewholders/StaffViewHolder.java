package com.example.proyectofinalapps.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.User;

public class StaffViewHolder extends RecyclerView.ViewHolder {

    private TextView nameuserrow;
    private ImageView userImg;

    private User user;

    public StaffViewHolder(@NonNull View itemView) {
        super(itemView);

        nameuserrow = itemView.findViewById(R.id.nameuserrow);
        userImg = itemView.findViewById(R.id.userImg);
    }

    public TextView getNameuserrow() {
        return nameuserrow;
    }

    public void setNameuserrow(TextView nameuserrow) {
        this.nameuserrow = nameuserrow;
    }

    public ImageView getUserImg() {
        return userImg;
    }

    public void setUserImg(ImageView userImg) {
        this.userImg = userImg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
