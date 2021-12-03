package com.example.proyectofinalapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.viewholders.StaffViewHolder;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffViewHolder> {

    private ArrayList<User> users;

    public StaffAdapter() {
        users = new ArrayList<>();
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.clientrow, parent, false);
        StaffViewHolder holder = new StaffViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        User user = users.get(position);
        holder.getNameuserrow().setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUser(User user) {
        users.add(user);

    }
}
