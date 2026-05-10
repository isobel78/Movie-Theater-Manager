/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 * 
 * This class represents a theater within a venue(chain). It has properties for the specific location and has getters and setters for the properties. It also implements the Displayable interface to provide a formatted string output of the theater.
 */

import java.util.ArrayList;

public class Theater extends Venue implements Displayable {
    //properties
    private int theaterID;
    private String address;
    private String city;
    private String state;
    private String zip;
    private ArrayList<Screen> screens;
    private ArrayList<Showtime> showtimes;

    //constructor
    public Theater(int theaterID, int venueID, String venueName, String address, String city, String state, String zip) {
        super(venueID, venueName);
        this.theaterID = theaterID;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.screens = new ArrayList<>();
        this.showtimes = new ArrayList<>();
    }

    //no-arg constructor
    public Theater() {
        super(0, ""); //default venue ID and name
        this.theaterID = 0;
        this.address = "";
        this.city = "";
        this.state = "";
        this.zip = "";
        this.screens = new ArrayList<>();
        this.showtimes = new ArrayList<>();
    }

    //getters and setters
    //theater ID
    public int getTheaterID() {
        return theaterID;
    }

    //address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //state
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //zip
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    //screens
    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public void addScreen(Screen s) {
        if (s != null) {
            screens.add(s);
        }
    }

    //showtimes
    public ArrayList<Showtime> getShowtimes() { 
        return showtimes; 
    }

    public void addShowtime(Showtime st) { 
        if (st != null) {
            showtimes.add(st); 
        }
    }

    //details method implementation from Venue
    @Override
    public String getDetails() {
        return String.format("Theater ID: %d | Name: %s | Address: %s, %s, %s %s | Screens: %d",
                theaterID, name, address, city, state, zip, screens.size());
    }

    //display method implementation from Displayable
    @Override
    public String display() {
        return String.format("%s, %s, %s, %s:", 
                             name, address, city, state);
    }
}
