package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectofinalapps.activities.ClientNotificationsActivity;
import com.example.proyectofinalapps.databinding.FragmentHomeClientBinding;
import com.example.proyectofinalapps.model.Client;
import com.example.proyectofinalapps.model.User;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HomeClientFragment extends Fragment {

    private FragmentHomeClientBinding binding;
    private View view;
    private ImageView codQrImg, goToNotificationsActivity;

    private Client client;
    private User user;

    public HomeClientFragment() {
        // Required empty public constructor
    }

    public static HomeClientFragment newInstance() {
        HomeClientFragment fragment = new HomeClientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeClientBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        codQrImg = binding.codQrImg;
        goToNotificationsActivity = binding.goToNotificationsActivity;

        generateQR();

        goToNotificationsActivity.setOnClickListener(this::goToNotificationsActivity);

        return view;
    }

    private void generateQR() {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            Bitmap bitmap = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE, 750, 750);

            codQrImg.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void goToNotificationsActivity(View view){
        Intent intent = new Intent(getActivity(), ClientNotificationsActivity.class);
        getActivity().startActivity(intent);
    }
}