package com.example.proyectofinalapps.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectofinalapps.activities.Notifications;
import com.example.proyectofinalapps.databinding.FragmentHomeClientBinding;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HomeClientFragment extends Fragment {

    private FragmentHomeClientBinding binding;
    private View view;
    private TextView welcomeTv;
    private ImageView codQrImg, goToNotificationsActivity;

    private Person person;

    private OnIsActiveClientListener listener;

    public HomeClientFragment() {
        // Required empty public constructor
        person = new Person();
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
        welcomeTv = binding.welcomeTv;

        generateQR();


        FirebaseFirestore.getInstance().collection("Clientes").document(person.getId()).addSnapshotListener(
                (task, error) -> {
                    Person p = task.toObject(Person.class);
                    if (p!=null){
                        if(p.getIsActive().equals("Y")) {
                            listener.onIsActiveClient();
                            //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                        }
                    }
                }
        );

        return view;
    }

    private void generateQR() {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Gson gson = new Gson();
            String json = gson.toJson(person);
            Bitmap bitmap = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE, 750, 750);

            codQrImg.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public OnIsActiveClientListener getListener() {
        return listener;
    }

    public void setListener(OnIsActiveClientListener listener) {
        this.listener = listener;
    }

    public interface OnIsActiveClientListener {
        void onIsActiveClient();
    }
}