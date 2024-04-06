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
public class SwimStudent {

    private String name;
    private String level;
    private List<SwimLesson> lessons;
    private List<Qualification> qualifications;

    // Constructor
    public SwimStudent(String name, String level) {
        this.name = name;
        this.level = level;
        this.lessons = new ArrayList<>();
        this.qualifications = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public List<SwimLesson> getLessons() {
        return lessons;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void addLesson(SwimLesson lesson) {
        this.lessons.add(lesson);
    }

    public void addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
    }
}
