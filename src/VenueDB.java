/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 *
 * This class provides CRUD operations for the Venues table.
 *
 * Venues are theater names/chains that own one or more Theater locations.
 * The table contains ID and Name columns. The ID is the primary key and is auto-incremented by the database.
 * 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VenueDB {
    //FUNCTION: SQL to CREATE a new record
    //PARAMS: Venue object
    public static void addVenue(Connection conn, Venue v) {
        addVenue(conn, v.name);
    }

    //FUNCTION: SQL to CREATE a new record by name
    //PARAMS: venue name string
    //RETURNS: generated Venue_ID, or -1 on failure
    //used to add new venues via the Admin UI
    public static int addVenue(Connection conn, String name) {
        String sql = "INSERT INTO Venues(Name) " +
                     "VALUES(?);";

        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, name);
            pst.executeUpdate();

            try (ResultSet keys = pst.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting venue: " + e.getMessage());
        }
        return -1;
    }

    //FUNCTION: SQL to READ a single venue by ID
    //PARAMS: ID, the venue ID to look up
    //RETURNS: Object array of venue ID and Name if found, null if not found
    public static Object[] getVenue(Connection conn, int id) {
        String sql = "SELECT * FROM Venues " +
                     "WHERE Venue_ID = ?;";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                //since Venue is an abstract class, create an empty object to hold the result
                return new Object[]{rs.getInt("Venue_ID"),
                                    rs.getString("Name")};
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving venue: " + e.getMessage());
        }

        return null;
    }

    //FUNCTION: SQL to READ all venue records
    //PARAMS: none
    //RETURNS: ArrayList of object arrays
    public static ArrayList<Object[]> getAllVenues(Connection conn) {
        ArrayList<Object[]> venues = new ArrayList<>();
        String sql = "SELECT * FROM Venues " +
                     "ORDER BY Venue_ID;";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                venues.add(new Object[]{rs.getInt("Venue_ID"),
                                        rs.getString("Name")});
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving venues: " + e.getMessage());
        }

        return venues;
    }

    //FUNCTION: SQL to UPDATE a record
    //PARAMS: Venue object
    public static void updateVenue(Connection conn, Venue v) {
        String sql = "UPDATE Venues " +
                     "SET Name = ? " +
                     "WHERE Venue_ID = ?;";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, v.name);
            pst.setInt(2, v.venueID);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating venue: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE a record
    //PARAMS: venue id
    //Note: Theaters referencing this venue must be removed first. (foreign-key enforcement is on)
    public static void deleteVenue(Connection conn, int id) {
        String sql = "DELETE FROM Venues " +
                     "WHERE Venue_ID = ?;";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting venue: " + e.getMessage());
        }
    }

    // FUNCTION: print to the console
    // Used during login and when an admin creates a new theater
    public static void listVenues(Connection conn) {
        ArrayList<Object[]> venues = getAllVenues(conn);

        for (Object[] v : venues) {
            System.out.println("\t[ID: " + v[0] + "]\t" + v[1]);
        }
        System.out.println();
    }

    

}
