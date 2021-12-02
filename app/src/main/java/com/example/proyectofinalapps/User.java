package com.example.proyectofinalapps;

public class User {

    private String name;
    private String id;
    private String email;
    private String uid;

    public User() {
    }

    public User(String name, String id, String email, String uid) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.uid = uid;
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
}
