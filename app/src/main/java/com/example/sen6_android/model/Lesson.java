package com.example.sen6_android.model;

public class Lesson {
    private int id;
    private Course course;
    private User user;
    private int schedule;
    private boolean active;

    public Lesson(int id, Course course, User user, int schedule, boolean active) {
        this.id = id;
        this.course = course;
        this.user = user;
        this.schedule = schedule;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSchedule(){
        return schedule;
    }

    public void setSchedule(int schedule){
        this.schedule = schedule;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
