package com.example.proyectofinalapps.model;

public class User {

    private String name;
    private String id;
    private String email;
    private String uid;
    private String rol;

    public User() {
    }

    public User(String name, String id, String email, String uid, String rol) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.uid = uid;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
