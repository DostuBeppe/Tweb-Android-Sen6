package com.example.sen6_android.model;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private Course course;
    private List<Boolean> schedule;
    private int availableLessons;

    public Scheduler(Course course, int now) {
        this.course = course;
        this.schedule = new ArrayList<>(20);
        for(int i=now;i<20;i++) {
            schedule.set(i,Boolean.TRUE);
        }
        this.availableLessons = 20-now;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Boolean> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Boolean> schedule) {
        this.schedule = schedule;
    }

    public void setFalse(int i){
        if(i<20){
            schedule.set(i,Boolean.FALSE);
            availableLessons--;
        }
    }
    public int getAvailableLessons() {
        return availableLessons;
    }

    public void setAvailableLessons(int availableLessons) {
        this.availableLessons = availableLessons;
    }
}
