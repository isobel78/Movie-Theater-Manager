/************************************************
 * Name: Atlanta Daniel
 * Date: April 24, 2026
 * Assignment: SDC330 Course Project
 * 
 * This class represents a movie with properties for its title, genre, duration, and rating. It includes getters and setters for these properties. There is a display() method implementation from the Displayable interface to provide formatted output of the movie details.
 */

public class Movie implements Displayable {
    //properties
    private int movieID;
    private String title;
    private String genre;
    private String rating;
    private int runtime;

    //constructor
    public Movie(int id, String title, String genre, String rating, int runtime) {
        this.movieID = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.runtime = runtime;
    }

    //getters and setters
    //movie ID
    public int getMovieID() {
        return movieID;
    }

    //title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    //rating
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    //runtime
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int minutes) {
        this.runtime = minutes;
    }

    //display method implementation from Displayable
    @Override
    public String display() {
        return String.format("Movie ID: %d | Title: %s | Genre: %s | Rating: %s | Runtime: %d min",
                movieID, title, genre, rating, runtime);
    }
    
}
