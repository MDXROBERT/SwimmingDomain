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
public class Instructor {

    private String name;
    private List<SwimLesson> classes;
    private List<SwimStudent> assessedStudents;

    // Constructor
    public Instructor(String name) {
        this.name = name;
        this.classes = new ArrayList<>();
        this.assessedStudents = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<SwimLesson> getClasses() {
        return classes;
    }

    public List<SwimStudent> getAssessedStudents() {
        return assessedStudents;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void addClass(SwimLesson swimLesson) {
        this.classes.add(swimLesson);
    }

    public void addAssessedStudent(SwimStudent student) {
        this.assessedStudents.add(student);
    }

}
