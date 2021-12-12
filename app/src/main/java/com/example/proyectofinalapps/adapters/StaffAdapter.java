package com.example.proyectofinalapps.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.HomeStaffActivity;
import com.example.proyectofinalapps.activities.PrivacyPolicyActivity;
import com.example.proyectofinalapps.activities.SplashActivity;
import com.example.proyectofinalapps.activities.customerDetails;
import com.example.proyectofinalapps.fragments.HomeStaffFragment;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.User;
import com.example.proyectofinalapps.viewholders.StaffViewHolder;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffViewHolder> {

    private ArrayList<Person> clients;
    HomeStaffActivity homeStaff;

    public StaffAdapter() {
        clients = new ArrayList<>();
    }

    public void addClient(Person client) {
        clients.add(client);
        notifyItemInserted(clients.size()-1);
    }

    public void rebaseAdapter () {
        clients.clear();

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

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), customerDetails.class);
                intent.putExtra("clientName",client.getFullName());
                intent.putExtra("emailName",client.getEmail());
                intent.putExtra("ageClient","");
                intent.putExtra("gymClient","My Gym");
                //TODO intent.putExtra("accountStatusClient",client.getIsActive());
                intent.putExtra("membershipClient","Mensual");
                intent.putExtra("status",client.getIsActive());
                //TODO intent.putExtra("dateClient",client.get);

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }
}
