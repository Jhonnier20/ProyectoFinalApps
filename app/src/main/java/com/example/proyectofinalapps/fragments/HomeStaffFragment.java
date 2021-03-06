package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectofinalapps.activities.ActivateClient_AllowEntry;
import com.example.proyectofinalapps.activities.Notifications;
import com.example.proyectofinalapps.activities.customerDetails;
import com.example.proyectofinalapps.adapters.StaffAdapter;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Date;

public class HomeStaffFragment extends Fragment implements ActivateClient_AllowEntry.OnActivedClient, StaffAdapter.OnClientInfoListener {

    private FragmentHomeStaffBinding binding;

    private EditText searchClient;
    private Button activateClientStaffBtn, allowEntry, buscarBtn;
    private RecyclerView clientRecylcler;
    private ImageView notifications;

    private View view;

    private StaffAdapter adapter;
    private LinearLayoutManager manager;

    private OnReadQRListener onReadQRListener;

    private ActivateClient_AllowEntry activateClient_allowEntry;

    public HomeStaffFragment() {
        // Required empty public constructor
        adapter = new StaffAdapter();
        activateClient_allowEntry = new ActivateClient_AllowEntry();
    }

    public static HomeStaffFragment newInstance() {
        HomeStaffFragment fragment = new HomeStaffFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeStaffBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        activateClient_allowEntry.setListener(this);

        clientRecylcler = binding.clientRecylcler;
        manager = new LinearLayoutManager(getActivity());
        clientRecylcler.setLayoutManager(manager);
        clientRecylcler.setAdapter(adapter);
        adapter.setListener(this);
        clientRecylcler.setHasFixedSize(true);
        activateClientStaffBtn = binding.activateClientStaffBtn;
        allowEntry = binding.allowEntry;
        buscarBtn  = binding.buscarBtn;
        notifications = binding.notifications;
        searchClient = binding.searchClient;

        activateClientStaffBtn.setOnClickListener(this::activateClient);
        allowEntry.setOnClickListener(this::allowEntry);
        notifications.setOnClickListener(this::goToNotifications);
        buscarBtn.setOnClickListener(this::searchClient);

        searchClient.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(editable.toString().equals("")) {
                            chargeClients();
                        }
                    }
                }
        );

        if(adapter.getItemCount() == 0) {
            chargeClients();
        }
        return view;
    }

    private void searchClient(View view) {

        String toSearch = searchClient.getText().toString().trim();
        if(!toSearch.isEmpty() && !toSearch.equals("")){
            adapter.deleteClients();
            FirebaseFirestore.getInstance().collection("Clientes").whereEqualTo("fullName", toSearch).get().addOnCompleteListener(
                    task -> {
                        for(DocumentSnapshot doc: task.getResult()) {
                            Person person = doc.toObject(Person.class);
                            if(person.getIsActive().equals("Y")) {
                                Log.e(">>>", person.getFullName());
                                adapter.addClient(person);
                                //adapter.notifyDataSetChanged();
                            }
                        }
                        Log.e(">>>", "Finish");
                    }
            );
        }
    }

    private void activateClient(View view) {
        Intent intent = new Intent(getActivity(), ActivateClient_AllowEntry.class);
        intent.putExtra("title", "ACTIVAR");
        getActivity().startActivity(intent);
    }

    private void allowEntry(View view){
        Intent intent = new Intent(getActivity(), ActivateClient_AllowEntry.class);
        intent.putExtra("title", "PERMITIR");
        getActivity().startActivity(intent);
    }

    private void goToNotifications(View view){
        Intent intent = new Intent(getActivity(), Notifications.class);
        intent.putExtra("rol","Staff");
        getActivity().startActivity(intent);
    }

    public void setOnReadQRListener(OnReadQRListener onReadQRListener) {
        this.onReadQRListener = onReadQRListener;
    }

    private void chargeClients() {
        adapter.deleteClients();
        FirebaseFirestore.getInstance().collection("Clientes").get().addOnCompleteListener(
                task -> {
                    for(DocumentSnapshot doc: task.getResult()) {
                        Person person = doc.toObject(Person.class);
                        if(person.getIsActive().equals("Y")) {
                            adapter.addClient(person);
                        }
                    }
                }
        );
    }

    @Override
    public void onActivedClient(Person client) {
        adapter.addClient(client);
    }

    @Override
    public void onClientInfo(Person client) {
        Intent intent = new Intent(getActivity(), customerDetails.class);
        intent.putExtra("client", client);
        startActivity(intent);
    }

    public interface OnReadQRListener {
        void onReadQR();
    }

    public StaffAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StaffAdapter adapter) {
        this.adapter = adapter;
    }


}
