package com.example.proyectofinalapps.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.adapters.StaffAdapter;
import com.example.proyectofinalapps.databinding.FragmentHomeStaffBinding;
import com.google.zxing.integration.android.IntentIntegrator;

public class HomeStaffFragment extends Fragment {

    private FragmentHomeStaffBinding binding;
    private EditText readQR;

    private Button activateClientStaffBtn;

    private RecyclerView clientRecylcler;

    private View view;

    private StaffAdapter adapter;
    private LinearLayoutManager manager;

    private OnReadQRListener onReadQRListener;

    public HomeStaffFragment() {
        // Required empty public constructor
        adapter = new StaffAdapter();
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

        clientRecylcler = binding.clientRecylcler;
        manager = new LinearLayoutManager(getActivity());
        clientRecylcler.setLayoutManager(manager);
        clientRecylcler.setAdapter(adapter);
        clientRecylcler.setHasFixedSize(true);

        activateClientStaffBtn = binding.activateClientStaffBtn;
        readQR = binding.readQR;

        activateClientStaffBtn.setOnClickListener(this::readQR);

        return view;
    }

    private void readQR(View view) {
        onReadQRListener.onReadQR();
    }


    public void setOnReadQRListener(OnReadQRListener onReadQRListener) {
        this.onReadQRListener = onReadQRListener;
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