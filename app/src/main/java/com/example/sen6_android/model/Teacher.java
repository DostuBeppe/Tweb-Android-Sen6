package com.example.sen6_android.model;

public class Teacher {
    private int id;
    private String name;
    private String surname;
    private boolean active;

    public Teacher(int id, String name, String surname, boolean active) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
