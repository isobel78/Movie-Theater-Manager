/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 * 
 * This class represents a showtime for a movie in a specific theater. It has properties for the showtime ID, movie , screen, and the date and time of the showtime. It includes getters and setters for these properties. There is a display() method implementation from the Displayable interface to provide formatted output of the showtime details.
 */

public class Showtime implements Displayable {
    //properties
    private int showtimeID;
    private Movie movie;
    private Screen screen;
    private String showDate;
    private String showTime;

    //constructor
    public Showtime(int id, Movie movie, Screen screen, String showDate, String showTime) {
        this.showtimeID = id;
        this.movie = movie;
        this.screen = screen;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    //getters and setters
    //showtime ID
    public int getShowtimeID() {
        return showtimeID;
    }

    //movie
    public Movie getMovie() {
        return movie;
    }

    //screen
    public Screen getScreen() {
        return screen;
    }

    //show date
    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String date) {
        this.showDate = date;
    }

    //show time
    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String time) {
        this.showTime = time;
    }

    //display method implementation from Displayable
    @Override
    public String display() {
        return String.format("    Screen %d  |  %s - %s, %s, %d min", 
                             screen.getScreenNumber(), movie.getTitle(), showDate, showTime, movie.getRuntime());
    }
}
