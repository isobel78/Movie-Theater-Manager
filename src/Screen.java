/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 * 
 * This class represents a specific screen within a theater. It has properties for the screen number and capacity. It includes getters and setters for these properties. There is a toString() override to provide formatted output.
 */

public class Screen implements Displayable {
    //properties
    private int screenID;
    private int screenNumber;
    private int vehicleCapacity;
    private int theaterID;

    //constructor
    public Screen(int id, int screenNumber, int vehicleCapacity, int theaterID) {
        this.screenID = id;
        this.screenNumber = screenNumber;
        this.vehicleCapacity = vehicleCapacity;
        this.theaterID = theaterID;
    }

    //getters and setters
    //screen ID
    public int getScreenID() {
        return screenID;
    }

    //screen number
    public int getScreenNumber() {
        return screenNumber;
    }

    //vehicle capacity
    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(int capacity) {
        this.vehicleCapacity = capacity;
    }

    //theater ID
    public int getTheaterID() {
        return theaterID;
    }

    //toString override for formatted output
    @Override
    public String display() {
        return String.format("\tScreen #%d | Capacity: %d vehicles", screenNumber, vehicleCapacity);
    }
    
}
