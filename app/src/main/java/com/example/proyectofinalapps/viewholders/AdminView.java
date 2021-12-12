package com.example.proyectofinalapps.viewholders;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.activities.SplashActivity;
import com.example.proyectofinalapps.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminView extends RecyclerView.ViewHolder {

    private Person instructor;

    private TextView instructorName;
    private Button deleteInstructor;
    protected FirebaseUser firebaseUser;

    public AdminView(@NonNull View itemView) {
        super(itemView);

        instructorName = itemView.findViewById(R.id.instructorName);
        deleteInstructor = itemView.findViewById(R.id.deleteInstructor);

        deleteInstructor.setOnClickListener(this::deleteInstructor);

    }

    public void deleteInstructor(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Eliminar instructor")
                .setMessage("¿Esta seguro que desea eliminar a "+ instructor.getFullName()+"?")
                .setNegativeButton("NO", (dialog, id) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("SI", (dialog, id) -> {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseFirestore.getInstance().collection("Staff").document(instructor.getId()).delete();
                    dialog.dismiss();
                });
        builder.show();
    }

    public interface OnDeleteInstructor{
        void onDelete(Person instructor);
    }


    public TextView getInstructorName() {
        return instructorName;
    }

    public Button getDeleteInstructor() {
        return deleteInstructor;
    }

    public void setInstructor(Person instructor){
        this.instructor = instructor;
    }
}
