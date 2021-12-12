package com.example.proyectofinalapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Person;
import com.example.proyectofinalapps.viewholders.AdminView;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminView> {

    private ArrayList<Person> instructors;

    public AdminAdapter(){
        instructors = new ArrayList<>();
    }

    public void addInstructor(Person instructor){
        instructors.add(instructor);
        notifyItemInserted(instructors.size()-1);
    }

    @NonNull
    @Override
    public AdminView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adminrow, parent, false);
        AdminView skeleton = new AdminView(view);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminView skeleton, int position) {
        Person instructor = instructors.get(position);
        skeleton.setInstructor(instructor);
        skeleton.getInstructorName().setText(instructor.getFullName());
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }
}
