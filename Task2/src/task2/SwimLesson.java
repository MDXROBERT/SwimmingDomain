/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author iarin
 */
public class SwimLesson {

    private String day;
    private String startTime;
    private String level;
    private Instructor takenBy;
    private List<SwimStudent> attendees;

    public SwimLesson(String day, String startTime, String level) {

        this.day = day;
        this.startTime = startTime;
        this.level = level;
        this.attendees = new ArrayList<>();
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLevel() {
        return level;
    }

    public Instructor getTakenBy() {
        return takenBy;
    }

    public List<SwimStudent> getAttendees() {
        return attendees;
    }

    // Setters
    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTakenBy(Instructor takenBy) {
        this.takenBy = takenBy;
    }

    public void addAttendee(SwimStudent student) {
        this.attendees.add(student);
    }

}
