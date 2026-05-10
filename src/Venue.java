/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 * 
 * This abstract class represents a venue name, ie. a theater chain. It serves as a base class for theaters, which will inherit the venue ID and venue name.
 */

public abstract class Venue {
    //properties
    protected int venueID;
    protected String name;

    //2-parameter constructor
    protected Venue(int id, String name) {
        this.venueID = id;
        this.name = name;
    }

    //getters and setters
    //venue ID
    public int getVenueID() {
        return venueID;
    }

    //venue Name
    public String getVenueName() {
        return name;
    }

    public void setVenueName(String name) {
        this.name = name;
    }

    //abstract method to be implemented by subclasses
    public abstract String getDetails();
}
