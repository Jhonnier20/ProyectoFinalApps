package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.proyectofinalapps.activities.ActivateClient_AllowEntry;
import com.example.proyectofinalapps.activities.Notifications;
import com.example.proyectofinalapps.adapters.StaffAdapter;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeStaffFragment extends Fragment implements ActivateClient_AllowEntry.OnActivedClient {

    private FragmentHomeStaffBinding binding;
    private EditText searchClient;
    private Button activateClientStaffBtn, allowEntry, buscarBtn;
    private RecyclerView clientRecylcler;
    private View view;
    private StaffAdapter adapter;
    private LinearLayoutManager manager;
    private OnReadQRListener onReadQRListener;
    private ImageView notifications;

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
        clientRecylcler.setHasFixedSize(true);
        activateClientStaffBtn = binding.activateClientStaffBtn;
        allowEntry = binding.allowEntry;
        notifications = binding.notifications;
        searchClient = binding.searchClient;
        activateClientStaffBtn.setOnClickListener(this::activateClient);
        allowEntry.setOnClickListener(this::allowEntry);
        notifications.setOnClickListener(this::goToNotifications);

        chargeClients();
        return view;
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
        getActivity().startActivity(intent);
    }

    public void setOnReadQRListener(OnReadQRListener onReadQRListener) {
        this.onReadQRListener = onReadQRListener;
    }

    private void chargeClients() {
        adapter.rebaseAdapter();
        FirebaseFirestore.getInstance().collection("Clientes").get().addOnCompleteListener(
                task -> {
                    for(DocumentSnapshot doc: task.getResult()) {
                        Person person = doc.toObject(Person.class);
                        if(person.getIsActive().equals("Y")) {
                            adapter.addClient(person);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    @Override
    public void onActivedClient(Person client) {
        adapter.addClient(client);
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