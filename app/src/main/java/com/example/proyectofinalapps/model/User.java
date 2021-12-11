package com.example.proyectofinalapps.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String rol;

    public User() {
    }

    public User(String id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
