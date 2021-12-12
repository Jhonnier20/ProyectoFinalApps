package com.example.proyectofinalapps.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinalapps.R;
import com.example.proyectofinalapps.model.Person;

public class AdminView extends RecyclerView.ViewHolder {

    private Person instructor;

    private ImageView imageInstructor;
    private TextView instructorName;
    private Button deleteInstructor;

    public AdminView(@NonNull View itemView) {
        super(itemView);

        imageInstructor = itemView.findViewById(R.id.imageInstructor);
        instructorName = itemView.findViewById(R.id.instructorName);
        deleteInstructor = itemView.findViewById(R.id.deleteInstructor);

        deleteInstructor.setOnClickListener(this::deleteInstructor);
    }

    public void deleteInstructor(View view){
        //TODO con firebase
    }

    public interface OnDeleteInstructor{
        void onDelete(Person instructor);
    }

    public ImageView getImageInstructor() {
        return imageInstructor;
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
