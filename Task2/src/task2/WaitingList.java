/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author iarin
 */
// ... other imports
import java.util.List;
import java.util.ArrayList;

public class WaitingList {
    private List<SwimStudent> students;

    // Constructor
    public WaitingList() {
        this.students = new ArrayList<>();
    }

    // Method to add a student to the waiting list
    public void addStudent(SwimStudent student) { this.students.add(student); }

    // Method to remove a student from the waiting list
    public void removeStudent(SwimStudent student) { this.students.remove(student); }

    // Getter
    public List<SwimStudent> getStudents() { return students; }
}

    

