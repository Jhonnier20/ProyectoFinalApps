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

public class ProfileClientFragment extends Fragment {

    private ImageView imageClient2;
    private TextView clientName2, emailClient2, ageClient2, gymClient2, accountStatusClient2,
            membershipClient2, dateClient2;
    private Button but2;
    private View view;

    private FragmentProfileClientBinding binding;

    public ProfileClientFragment() {
        // Required empty public constructor
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
        but2 = binding.but2;

        return view;
    }

    private void modifySubscriptionStatus(Boolean status){
        if(status){
            //La subscripcion esta activa
            but2.setText("Subscripción activa");
        }else{
            //La subscripcion esta inactiva
            but2.setText("Subscripción inactiva");
        }
    }
}