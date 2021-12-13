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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileClientFragment extends Fragment {

    private TextView clientName2, emailClient2, gymClient2, accountStatusClient2,
            membershipClient2, dateClient2, but;
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

        clientName2 = binding.clientName2;
        emailClient2 = binding.emailClient2;
        gymClient2 = binding.gymClient2;
        accountStatusClient2 = binding.accountStatusClient2;
        membershipClient2 = binding.membershipClient2;
        dateClient2 = binding.dateClient2;
        but = binding.but;

        FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).collection("Subscription").document(subscription.getId()).addSnapshotListener(
                (value, error) -> {
                    subscription = value.toObject(Subscription.class);
                }
        );

        accountStatusClient2.setText(subscription.getState());
        membershipClient2.setText(subscription.getMembership());
        if(subscription.isActive()) {
            but.setText("Subscripcion activa");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date dateStart = new Date(subscription.getDateStart());
            Date dateEnd = new Date(subscription.getDateEnd());
            dateClient2.setText(format.format(dateStart)  +"\n"+ " - "+"\n" + format.format(dateEnd));
        } else {
            but.setText("Subscripcion inactiva");
            dateClient2.setText("--/--/--");
        }

        //Display Client info
        clientName2.setText(person.getFullName());
        emailClient2.setText(person.getEmail());
        gymClient2.setText("My Gym");
        accountStatusClient2.setText(subscription.getState());
        membershipClient2.setText("Mensual");

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