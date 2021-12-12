package com.example.proyectofinalapps.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.adapters.AdminAdapter;
import com.example.proyectofinalapps.databinding.FragmentHomeAdminBinding;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeAdminFragment extends Fragment {

    private RecyclerView instructorList;
    private FragmentHomeAdminBinding binding;
    private View view;

    private LinearLayoutManager manager;
    private AdminAdapter adapter;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    public static HomeAdminFragment newInstance() {
        HomeAdminFragment fragment = new HomeAdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        instructorList = binding.instructorList;

        manager = new LinearLayoutManager(this.getContext());
        instructorList.setLayoutManager(manager);
        adapter = new AdminAdapter();
        instructorList.setAdapter(adapter);
        instructorList.setHasFixedSize(true);


        chargeInstructors();
        adapter.notifyDataSetChanged();

        return view;
    }

    private void chargeInstructors() {
        adapter.rebaseAdapter();
        FirebaseFirestore.getInstance().collection("Staff").get().addOnCompleteListener(
                task -> {
                    for(DocumentSnapshot doc: task.getResult()) {
                        Person person = doc.toObject(Person.class);
                        if(person.getRol().equals("Staff")){
                            adapter.addInstructor(person);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

}