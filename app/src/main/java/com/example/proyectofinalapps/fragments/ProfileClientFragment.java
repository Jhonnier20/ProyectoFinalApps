package com.example.proyectofinalapps.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.databinding.FragmentProfileClientBinding;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.model.Subscription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileClientFragment extends Fragment {

    private ImageView imageClient2;
    private TextView clientName2, emailClient2, ageClient2, gymClient2, accountStatusClient2,
            membershipClient2, dateClient2, activeTV;
    private View view;

    private FragmentProfileClientBinding binding;

    private Person person;
    private Subscription subscription;

    public ProfileClientFragment() {
        // Required empty public constructor
        subscription = new Subscription();
        person = new Person();
    }

    public static ProfileClientFragment newInstance() {
        ProfileClientFragment fragment = new ProfileClientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileClientBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        imageClient2 = binding.imageClient2;
        clientName2 = binding.clientName2;
        emailClient2 = binding.emailClient2;
        ageClient2 = binding.ageClient2;
        gymClient2 = binding.gymClient2;
        accountStatusClient2 = binding.accountStatusClient2;
        membershipClient2 = binding.membershipClient2;
        dateClient2 = binding.dateClient2;
        activeTV = binding.activeTV;

        FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).collection("Subscription").document(subscription.getId()).addSnapshotListener(
                (value, error) -> {
                    subscription = value.toObject(Subscription.class);
                }
        );

        accountStatusClient2.setText(subscription.getState());
        membershipClient2.setText(subscription.getMembership());
        if(subscription.isActive()) {
            activeTV.setText("Subscripcion activa");
        } else {
            activeTV.setText("Subscripcion inactiva");
        }

        return view;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}