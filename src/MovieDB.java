/************************************************
 * Name: Atlanta Daniel
 * Date: May 1, 2026
 * Assignment: SDC330 Course Project
 *
 * This class provides CRUD operations for the Movies table.
 *
 * Each Movie has a unique Movie_ID (PK autoincrement).
 * Additional properties include Title, Genre, Rating, and Runtime.
 * 
 * Pass venueID = 999 (ADMIN_CODE) to retrieve all movies.
 * Pass a specific venueID to retrieve only movies playing at that venue's screens
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MovieDB {
    //admin code
    private static final int ADMIN_CODE = 999;

    //FUNCTION:SQL to CREATE a new Movie record
    //PARAMS: Movie object
    public static void addMovie(Connection conn, Movie m) {
        String sql = "INSERT INTO Movies(Title, Genre, Rating, Runtime) " +
                     "VALUES(?, ?, ?, ?);";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, m.getTitle());
            pst.setString(2, m.getGenre());
            pst.setString(3, m.getRating());
            pst.setInt(4, m.getRuntime());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting movie: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to return a single Movie record by ID
    //PARAMS: movie ID
    //RETURNS: Movie object if found, null if not found
    public static Movie getMovie(Connection conn, int id) {
        String sql = "SELECT * FROM Movies " +
                     "WHERE Movie_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Movie(
                    rs.getInt("Movie_ID"),
                    rs.getString("Title"),
                    rs.getString("Genre"),
                    rs.getString("Rating"),
                    rs.getInt("Runtime")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving movie: " + e.getMessage());
        }

        return null;
    }

    //FUNCTION: SQL to READ all Movie records
    //PARAMS: none
    //RETURNS: ArrayList of Movie objects
    public static ArrayList<Movie> getAllMovies(Connection conn) {
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movies " +
                     "ORDER BY Movie_ID;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                movies.add(new Movie(rs.getInt("Movie_ID"), rs.getString("Title"), rs.getString("Genre"), rs.getString("Rating"), rs.getInt("Runtime")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving movies: " + e.getMessage());
        }

        return movies;
    }

    //FUNCTION: SQL to READ all Movie records for venueID
    //PARAMS: venueID - ADMIN_CODE returns all movies, venue ID returns only movies currently scheduled at that venue's screens
    //RETURNS:  ArrayList of Movie objects
    public static ArrayList<Movie> getMovies(Connection conn, int venueID) {
        if (venueID == ADMIN_CODE) {
            //admin - return every movie in the database
            return getAllMovies(conn);
        }

        //venue user - return only movies scheduled at their screens.
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT m.Movie_ID, m.Title, m.Genre, m.Rating, m.Runtime " +
                     "FROM Movies m " +
                     "JOIN Showtimes st ON m.Movie_ID = st.Movie_ID " +
                     "JOIN Screens sc ON st.Screen_ID = sc.Screen_ID " +
                     "JOIN Theaters t ON sc.Theater_ID = t.Theater_ID " +
                     "WHERE t.Venue_ID = ? " +
                     "ORDER BY m.Movie_ID;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, venueID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(rs.getInt("Movie_ID"), rs.getString("Title"), rs.getString("Genre"), rs.getString("Rating"), rs.getInt("Runtime")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving movies by venue: " + e.getMessage());
        }

        return movies;
    }

    //FUNCTION: SQL to UPDATE a Movie record
    //PARAMS: Movie object
    public static void updateMovie(Connection conn, Movie m) {
        String sql = "UPDATE Movies " +
                     "SET Title = ?, " +
                     "    Genre = ?, " +
                     "    Rating = ?, " +
                     "    Runtime = ? " +
                     "WHERE Movie_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, m.getTitle());
            pst.setString(2, m.getGenre());
            pst.setString(3, m.getRating());
            pst.setInt(4, m.getRuntime());
            pst.setInt(5, m.getMovieID());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating movie: " + e.getMessage());
        }
    }

    //FUNCTION:SQL to DELETE a Movie record
    //PARAMS: movie id
    public static void deleteMovie(Connection conn, int id) {
        String sql = "DELETE FROM Movies " +
                     "WHERE Movie_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
        }
    }

    //FUNCTION: print to the console
    //PARAMS: venueID
    public static void listMovies(Connection conn, int venueID) {
        ArrayList<Movie> movies = getMovies(conn, venueID);

        System.out.println();
        if (movies.isEmpty()) {
            System.out.println("  No movies found.");
            return;
        }

        System.out.printf("  %-4s %-32s %-18s %-6s %-8s%n",
            "ID", "Title", "Genre", "Rating", "Runtime");
        System.out.println("  " + "-".repeat(72));

        for (Movie m : movies) {
            System.out.printf("  %-4d %-32s %-18s %-6s %d min%n",
                m.getMovieID(),
                m.getTitle(),
                m.getGenre(),
                m.getRating(),
                m.getRuntime());
        }
    }

    

}
