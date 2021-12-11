package com.example.proyectofinalapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.viewholders.StaffViewHolder;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffViewHolder> {

    private ArrayList<Person> clients;

    public StaffAdapter() {
        clients = new ArrayList<>();
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
        Person client = clients.get(position);
        holder.getNameuserrow().setText(client.getFullName());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void addClient(Person client) {
        clients.add(client);
        notifyItemInserted(clients.size()-1);
    }

}
