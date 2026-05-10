/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 *
 * This class provides CRUD operations for the Showtimes table.
 *
 * A Showtime links one Movie to one Screen on a specific date and time.
 * Properties include Showtime_ID (PK autoincrement), Movie_ID (FK), Screen_ID (FK), Show_Date, and Show_Time.
 * 
 * Pass venueID = 999 (ADMIN_CODE) to retrieve all showtimes.
 * Pass a specific venueID to retrieve only showtimes at that venue's theaters.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ShowtimeDB {
    //admin code
    //private static final int ADMIN_CODE = 999;

    //FUNCTION:SQL to CREATE a new Showtime record
    //PARAMS: Showtime object
    public static void addShowtime(Connection conn, Showtime s) {
        String sql = "INSERT INTO Showtimes(Movie_ID, Screen_ID, Show_Date, Show_Time) " +
                     "VALUES(?, ?, ?, ?);";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, s.getMovie().getMovieID());
            pst.setInt(2, s.getScreen().getScreenID());
            pst.setString(3, s.getShowDate());
            pst.setString(4, s.getShowTime());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting showtime: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to READ a single Showtime record by ID
    //PARAMS: showtime ID to look up
    //RETURNS: Showtime object if found, null if not found
    public static Showtime getShowtime(Connection conn, int id) {
        String sql = "SELECT * FROM Showtimes " +
                     "WHERE Showtime_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Movie movie = MovieDB.getMovie(conn, rs.getInt("Movie_ID"));
                Screen screen = ScreenDB.getScreen(conn, rs.getInt("Screen_ID"));

                return new Showtime(
                    rs.getInt("Showtime_ID"),
                    movie,
                    screen,
                    rs.getString("Show_Date"),
                    rs.getString("Show_Time")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving showtime: " + e.getMessage());
        }

        return null;
    }

    //FUNCTION: SQL to READ all Showtime records for a Screen id
    //PARAMS: screen id
    //RETURNS: ArrayList of Showtime objects
    public static ArrayList<Showtime> getShowtimesByScreen(Connection conn, int screenID) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT * FROM Showtimes " +
                     "WHERE Screen_ID = ? " +
                     "ORDER BY Show_Date, Show_Time;";
        PreparedStatement pst;

        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, screenID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Movie movie  = MovieDB.getMovie(conn, rs.getInt("Movie_ID"));
                Screen screen = ScreenDB.getScreen(conn, rs.getInt("Screen_ID"));
                showtimes.add(new Showtime(
                    rs.getInt("Showtime_ID"), movie, screen,
                    rs.getString("Show_Date"), rs.getString("Show_Time")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving showtimes: " + e.getMessage());
        }

        return showtimes;
    }

      

    //FUNCTION: SQL to READ all Showtime records by Theater id
    //PARAMS: theater id
    //RETURNS: ArrayList of Showtime objects
    public static ArrayList<Showtime> getAllShowtimes(Connection conn, int theaterID) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT st.Showtime_ID, st.Movie_ID, st.Screen_ID, " +
                      "       st.Show_Date, st.Show_Time " +
                      "FROM Showtimes st " +
                      "JOIN Screens sc ON st.Screen_ID  = sc.Screen_ID " +
                      "JOIN Theaters t ON sc.Theater_ID = t.Theater_ID " +
                      "WHERE t.Theater_ID = ? " +
                      "ORDER BY st.Show_Date, st.Show_Time;";
        PreparedStatement pst;

        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, theaterID);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Movie movie = MovieDB.getMovie(conn, rs.getInt("Movie_ID"));
                Screen screen = ScreenDB.getScreen(conn, rs.getInt("Screen_ID"));
                showtimes.add(new Showtime(
                    rs.getInt("Showtime_ID"), movie, screen,
                    rs.getString("Show_Date"), rs.getString("Show_Time")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving showtimes: " + e.getMessage());
        }

        return showtimes;
    }

    //FUNCTION: SQL to UPDATE a Showtime record
    //PARAMS: Showtime object
    public static void updateShowtime(Connection conn, Showtime s) {
        String sql = "UPDATE Showtimes " +
                     "SET Movie_ID = ?, " +
                     "    Screen_ID = ?, " +
                     "    Show_Date = ?, " +
                     "    Show_Time = ? " +
                     "WHERE Showtime_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, s.getMovie().getMovieID());
            pst.setInt(2, s.getScreen().getScreenID());
            pst.setString(3, s.getShowDate());
            pst.setString(4, s.getShowTime());
            pst.setInt(5, s.getShowtimeID());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating showtime: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE a Showtime record
    //PARAMS: Showtime ID
    public static void deleteShowtime(Connection conn, int id) {
        String sql = "DELETE FROM Showtimes " +
                     "WHERE Showtime_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting showtime: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE all Showtimes for a given Movie_ID
    //PARAMS: movieID 
    //(called by MovieDB.deleteMovie() before removing the movie row)
    public static void deleteShowtimesByMovie(Connection conn, int movieID) {
        String sql = "DELETE FROM Showtimes " +
                     "WHERE Movie_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, movieID);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting showtimes by movie: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE all Showtimes for a given Screen_ID
    //PARAMS: screen ID 
    //(called by TheaterDB.deleteTheater() before removing each screen row)
    
    public static void deleteShowtimesByScreen(Connection conn, int screenID) {
        String sql = "DELETE FROM Showtimes " +
                     "WHERE Screen_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, screenID);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting showtimes by screen: " + e.getMessage());
        }
    }

    //FUNCTION: print to console all showtimes grouped by theater and screen
    //PARAMS: venueID (999 admin sees all showtimes)
    public static void listShowtimes(Connection conn, int venueID) {
        //get the theaters visible to this user
        ArrayList<Theater> theaters = TheaterDB.getAllTheaters(conn, venueID);

        if (theaters.isEmpty()) {
            System.out.println("  No theaters found.");
            return;
        }

        boolean anyShown = false;

        System.out.println();

        for (Theater t : theaters) {
            //get all screens for this theater
            ArrayList<Screen> allScreens = ScreenDB.getScreensByTheater(conn, t.getTheaterID());
            if (allScreens.isEmpty()) continue;

            boolean theaterPrinted = false;

            for (Screen s : allScreens) {
                //get all showtimes for this screen
                ArrayList<Showtime> allShowtimes = getShowtimesByScreen(conn, s.getScreenID());
                if (allShowtimes.isEmpty()) continue;

                //print theater header before showtimes
                if (!theaterPrinted) {
                    System.out.println(t.display());
                    theaterPrinted = true;
                    anyShown = true;
                }

                // Print screen sub-header
                System.out.println(s.display());

                //print showtimes by screen                
                for (Showtime st : allShowtimes) {
                    System.out.println("\t\t" + st.getShowDate() + 
                                       "\t" + st.getShowTime() +
                                       "\t" +st.getMovie().getTitle());
                }

                System.out.println();
            }

            if (theaterPrinted) System.out.println();
        }

        if (!anyShown) System.out.println("  No showtimes found.");
    }

    //FUNCTION: print to console all showtimes grouped by theater and screen
    //PARAMS: venueID (999 admin sees all showtimes)
    public static void listShowtimesWithID(Connection conn, int venueID) {
        //get the theaters visible to this user
        ArrayList<Theater> theaters = TheaterDB.getAllTheaters(conn, venueID);

        if (theaters.isEmpty()) {
            System.out.println("  No theaters found.");
            return;
        }

        boolean anyShown = false;

        System.out.println();

        for (Theater t : theaters) {
            //get all screens for this theater
            ArrayList<Screen> allScreens = ScreenDB.getScreensByTheater(conn, t.getTheaterID());
            if (allScreens.isEmpty()) continue;

            boolean theaterPrinted = false;

            for (Screen s : allScreens) {
                //get all showtimes for this screen
                ArrayList<Showtime> allShowtimes = getShowtimesByScreen(conn, s.getScreenID());
                if (allShowtimes.isEmpty()) continue;

                //print theater header before showtimes
                if (!theaterPrinted) {
                    System.out.println(t.display());
                    theaterPrinted = true;
                    anyShown = true;
                }

                // Print screen sub-header
                System.out.printf("\tScreen #%d  (Capacity: %d vehicles)%n",
                                  s.getScreenNumber(), s.getVehicleCapacity());

                //print showtimes by screen                
                for (Showtime st : allShowtimes) {
                    System.out.println("\t\t" + "[ID: " + st.getShowtimeID() + "] " + 
                                       "\t" + st.getShowDate() + 
                                       "\t" + st.getShowTime() +
                                       "\t" +st.getMovie().getTitle());
                }

                System.out.println();
            }

            if (theaterPrinted) System.out.println();
        }

        if (!anyShown) System.out.println("  No showtimes found.");
    }

}
