/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author iarin
 */
public class DistanceSwim extends Qualification {
    private int distance;

    // Constructor
    public DistanceSwim(String awardedBy, int distance) {
        super(awardedBy);
        this.distance = distance;
    }


    // Getter
    public int getDistance() { return distance; }

    // Setter
    public void setDistance(int distance) { this.distance = distance; }
}