package com.example.proyectofinalapps.model;

import java.io.Serializable;

public class Client implements Serializable {

    private String id;
    private String fullname;
    private int identification;
    private String email;
    private String password;
    private String idGym;
    private boolean activate;

    public Client() {

    }

    public Client(String id, String fullname, int identification, String email, String password, boolean activate) {
        this.id = id;
        this.fullname = fullname;
        this.identification = identification;
        this.email = email;
        this.password = password;
        this.activate = activate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getIdentification() {
        return identification;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdGym() {
        return idGym;
    }

    public void setIdGym(String idGym) {
        this.idGym = idGym;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }
}
