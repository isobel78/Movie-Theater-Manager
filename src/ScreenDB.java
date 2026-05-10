/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 *
 * This class provides CRUD operations for the Theaters table.
 *
 * Each Screen belongs to a Theater via Theater_ID (FK).
 * Properties include Screen_ID (PK autoincrement), Theater_ID (FK), the assigned Screen Number at the Theater Location, and Vehicle Capacity of the Screen.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScreenDB {
    //FUNCTION: SQL to CREATE a new Screen record
    //PARAMS: Screen object
    public static void addScreen(Connection conn, Screen s) {
        String sql = "INSERT INTO Screens(Theater_ID, Screen_Number, Vehicle_Capacity) " +
                     "VALUES(?, ?, ?);";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, s.getTheaterID());
            pst.setInt(2, s.getScreenNumber());
            pst.setInt(3, s.getVehicleCapacity());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting screen: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to READ a single Screen record by ID
    //PARAMS: ID, the screen ID to look up
    //RETURNS: Screen object if found, null if not found
    public static Screen getScreen(Connection conn, int id) {
        String sql = "SELECT * FROM Screens " +
                     "WHERE Screen_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Screen(
                    rs.getInt("Screen_ID"),
                    rs.getInt("Screen_Number"),
                    rs.getInt("Vehicle_Capacity"),
                    rs.getInt("Theater_ID")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving screen: " + e.getMessage());
        }

        return null;
    }

    //FUNCTION: SQL to READ all Screen records for a specific Theater
    //PARAMS: theaterID 
    //RETURNS: ArrayList of Screen objects belonging to that theater
    public static ArrayList<Screen> getScreensByTheater(Connection conn, int theaterID) {
        ArrayList<Screen> screens = new ArrayList<>();

        String sql = "SELECT * FROM Screens " +
                     "WHERE Theater_ID = ? " +
                     "ORDER BY Screen_Number;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, theaterID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                screens.add(new Screen(rs.getInt("Screen_ID"), rs.getInt("Screen_Number"), rs.getInt("Vehicle_Capacity"), rs.getInt("Theater_ID")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving screens by theater: " + e.getMessage());
        }

        return screens;
    }

    //FUNCTION: SQL to READ all Screen records
    //PARAMS: none
    //RETURNS: ArrayList of Screen objects
    public static ArrayList<Screen> getAllScreens(Connection conn) {
        ArrayList<Screen> screens = new ArrayList<>();
        String sql = "SELECT * FROM Screens " +
                     "ORDER BY Screen_ID;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                screens.add(new Screen(rs.getInt("Screen_ID"), rs.getInt("Screen_Number"), rs.getInt("Vehicle_Capacity"), rs.getInt("Theater_ID")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving screens: " + e.getMessage());
        }

        return screens;
    }

    //FUNCTION: SQL to UPDATE a Screen record
    //PARAMS: Screen object
    public static void updateScreen(Connection conn, Screen s) {
        String sql = "UPDATE Screens " +
                     "SET Theater_ID = ?, " +
                     "    Screen_Number = ?, " +
                     "Vehicle_Capacity = ? " +
                     "WHERE Screen_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, s.getTheaterID());
            pst.setInt(2, s.getScreenNumber());
            pst.setInt(3, s.getVehicleCapacity());
            pst.setInt(4, s.getScreenID());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating screen: " + e.getMessage());
        }
    }

    //FUNCTION: SQL to DELETE a Screen record
    //PARAMS: Screen ID
    public static void deleteScreen(Connection conn, int id) {
        String sql = "DELETE FROM Screens " +
                     "WHERE Screen_ID = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting screen: " + e.getMessage());
        }
    }

    //FUNCTION: print to the console
    //PARAMS:   theaterID
    public static void listScreensByTheater(Connection conn, int theaterID) {
        ArrayList<Screen> screens = getScreensByTheater(conn, theaterID);

        System.out.println();
        if (screens.isEmpty()) {
            System.out.println("No screens found for this theater.");
            return;
        }

        for (Screen s : screens) {
            System.out.println("\t[ID: " + s.getScreenID() + "]  Screen #" + s.getScreenNumber() + " (Capacity: " + s.getVehicleCapacity() + " vehicles)");
        }
    }

    
}
