package com.example.proyectofinalapps.model;

public class Subscription {

    private String id;
    private boolean active;
    private long dateStart, dateEnd;
    private String state, membership;

    public Subscription() {
    }

    public Subscription(String id, boolean active, long dateStart, long dateEnd, String state, String membership) {
        this.id = id;
        this.active = active;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.state = state;
        this.membership = membership;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }
}
