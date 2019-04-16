package com.example.sen6_android.model;

public class Course {
    private int id;
    private Subject subject;
    private Teacher teacher;
    private boolean active;

    public Course(int id, Subject subject, Teacher teacher, boolean active) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}