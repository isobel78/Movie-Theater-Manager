/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 *
 * This class provides CRUD operations for the Theaters table.
 *
 * Theaters are specific locations that belong to a Venue via the Venue_ID foreign key.
 * The table contains it's own ID that is the primary key and is auto-incremented by the database. Additional columns include Venue_ID (FK), Address, City, State, and Zip.
 * 
 * Pass venueID = 999 (ADMIN_CODE) to retrieve records across all venues.
 * Pass a specific venueID to retrieve only that venue's theaters.
 * 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TheaterDB {

    //admin code - when passed as venueID, all records are returned
    private static final int ADMIN_CODE = 999;

    //FUNCTION: SQL to CREATE a new Theater record
    //PARAMS: Theater object
    public static int addTheater(Connection conn, Theater t) {
        String sql = "INSERT INTO Theaters(Venue_ID, Address, City, State, Zip) " +
                     "VALUES(?, ?, ?, ?, ?);";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {         
            pst.setInt(1, t.getVenueID());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getCity());
            pst.setString(4, t.getState());
            pst.setString(5, t.getZip());
            pst.executeUpdate();

            try (ResultSet keys = pst.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting theater: " + e.getMessage());
        }

        return -1;
    }

    //FUNCTION: SQL to READ a single Theater record by ID
    //PARAMS: ID, the theater ID to look up
    //RETURNS: Theater object if found, null if not found
    public static Theater getTheater(Connection conn, int id) {
        String sql = "SELECT t.Theater_ID, t.Venue_ID, v.Name AS venue_name, " +
                     "       t.Address, t.City, t.State, t.Zip " +
                     "FROM Theaters t " +
                     "JOIN Venues v ON t.Venue_ID = v.Venue_ID " +
                     "WHERE t.Theater_ID = ?;";

        try  (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Theater(
                        rs.getInt("Theater_ID"),
                        rs.getInt("Venue_ID"),
                        rs.getString("venue_name"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("Zip")
                    );
                }
            }        
        } catch (SQLException e) {
            System.err.println("Error retrieving theater: " + e.getMessage());
        }

        return null;
    }

    //FUNCTION: SQL to READ all Theater records
    //PARAMS: venueID, if 999 (admin) then all theaters are returned, otherwise only theaters matching the venueID are returned
    //RETURNS: ArrayList of Theater objects
    public static ArrayList<Theater> getAllTheaters(Connection conn, int venueID) {
        ArrayList<Theater> theaters = new ArrayList<>();

        String sql;

        if (venueID == ADMIN_CODE) {
            //admin - return all theaters
            sql = "SELECT t.Theater_ID, t.Venue_ID, v.Name AS Venue_Name, " +
                "       t.Address, t.City, t.State, t.Zip " +
                "FROM Theaters t " +
                "JOIN Venues v ON t.Venue_ID = v.Venue_ID " +
                "ORDER BY t.Venue_ID, t.Theater_ID;";

        } else {
            //venue specific - return only theaters matching the venueID
            sql = "SELECT t.Theater_ID, t.Venue_ID, v.Name AS Venue_Name, " +
                "       t.Address, t.City, t.State, t.Zip " +
                "FROM Theaters t " +
                "JOIN Venues v ON t.Venue_ID = v.Venue_ID " +
                "WHERE t.Venue_ID = ? " +
                "ORDER BY t.Theater_ID;";  
        }

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            if (venueID != ADMIN_CODE) {
                pst.setInt(1, venueID);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    theaters.add(new Theater(rs.getInt("Theater_ID"), rs.getInt("Venue_ID"), rs.getString("Venue_Name"), rs.getString("Address"), rs.getString("City"), rs.getString("State"), rs.getString("Zip")));
                }
            } 
        } catch (SQLException e) {
            System.err.println("Error retrieving theaters: " + e.getMessage());
        }

        return theaters;
    }

    //FUNCTION: SQL to UPDATE a Theater record
    //PARAMS: Theater object
    public static void updateTheater(Connection conn, Theater t) {
        String sql = "UPDATE Theaters " +
                     "SET Venue_ID = ?, " +
                     "    Address = ?, " +
                     "    City = ?, " +
                     "    State = ?, " +
                     "    Zip = ? " +
                     "WHERE Theater_ID = ?;";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, t.getVenueID());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getCity());
            pst.setString(4, t.getState());
            pst.setString(5, t.getZip());
            pst.setInt(6, t.getTheaterID());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating theater: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE a Theater record
    //PARAMS: theater id
    //Note: Screens and Showtimes referencing this theater must be removed first. (foreign-key enforcement is on)
    public static void deleteTheater(Connection conn, int id) {
        //first cascade-delete all showtimes on each screen of this theater
        ArrayList<Screen> screens = ScreenDB.getScreensByTheater(conn, id);
        
        for (Screen s : screens) {
            ShowtimeDB.deleteShowtimesByScreen(conn, s.getScreenID());
            ScreenDB.deleteScreen(conn, s.getScreenID());
        }

        // Now delete the theater itself
        String sql = "DELETE FROM Theaters " +
                     "WHERE Theater_ID = ?;";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting theater: " + e.getMessage());
        }
    }

    // FUNCTION: print to the console
    // PARAMS: venueID
    public static void listTheaters(Connection conn, int venueID) {
        ArrayList<Theater> theaters = getAllTheaters(conn, venueID);

        System.out.println();
        if (theaters.isEmpty()) {
            System.out.println("  No theaters found.");
            return;
        } else {
             for (Theater t : theaters) {
                System.out.println("[ID: " + t.getTheaterID() + "]  " +
                                t.getVenueName() + "\t" +
                                t.getAddress() + ", " +
                                t.getCity() + ", " +   
                                t.getState());
            }
        }

        System.out.println();
    }

}
