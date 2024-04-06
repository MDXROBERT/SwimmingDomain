/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author iarin
 */
public abstract class Qualification {
    // This could be a common field if needed, or common methods that subclasses should implement.
    private String awardedBy; // The instructor who awarded the qualification

    // Constructor
    protected Qualification(String awardedBy) {
        this.awardedBy = awardedBy;
    }

    // Getter
    public String getAwardedBy() { return awardedBy; }

    // Setter
    public void setAwardedBy(String awardedBy) { this.awardedBy = awardedBy; }
}