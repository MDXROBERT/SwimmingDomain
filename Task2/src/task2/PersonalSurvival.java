/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author iarin
 */
public class PersonalSurvival extends Qualification {
    private String level;

    // Constructor
     public PersonalSurvival(String awardedBy, String level) {
        super(awardedBy);
        this.level = level;
    }
    // Getter
    public String getLevel() { return level; }

    // Setter
    public void setLevel(String level) { this.level = level; }
}
