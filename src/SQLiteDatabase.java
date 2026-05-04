/************************************************
 * Name: Atlanta Daniel
 * Date: May 1, 2026
 * Assignment: SDC330 Course Project
 *
 * Class to handle database interactions with a SQLite database.
 * The connect method will either connect to an existing database or create the database if it doesn't exist.
 * Also creates all necessary tables if they don't already exist.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase {
    public static Connection connect(String database) {
        String url = "jdbc:sqlite:" + database;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    //Create all five tables in dependency order (Venues, Theaters, Screens, Movies, Showtimes)
    public static void initializeDatabase(String database) {
        String createVenues =
            "CREATE TABLE IF NOT EXISTS Venues " +
            "    (Venue_ID  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "     Name VARCHAR(100) NOT NULL);";

        String createTheaters =
            "CREATE TABLE IF NOT EXISTS Theaters " +
            "    (Theater_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "     Venue_ID INTEGER NOT NULL," +
            "     Address VARCHAR(100) NOT NULL," +
            "     City VARCHAR(50) NOT NULL," +
            "     State VARCHAR(2) NOT NULL," +
            "     Zip VARCHAR(10) NOT NULL," +
            "     FOREIGN KEY (Venue_ID) REFERENCES Venues(Venue_ID));";

        String createScreens =
            "CREATE TABLE IF NOT EXISTS Screens " +
            "    (Screen_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "     Theater_ID INTEGER NOT NULL," +
            "     Screen_Number INTEGER NOT NULL," +
            "     Vehicle_Capacity INTEGER NOT NULL," +
            "     FOREIGN KEY (Theater_ID) REFERENCES Theaters(Theater_ID));";

        String createMovies =
            "CREATE TABLE IF NOT EXISTS Movies " +
            "    (Movie_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "     Title VARCHAR(100) NOT NULL," +
            "     Genre VARCHAR(50)," +
            "     Rating VARCHAR(5)," +
            "     Runtime INTEGER NOT NULL);";

        String createShowtimes =
            "CREATE TABLE IF NOT EXISTS Showtimes " +
            "    (Showtime_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "     Movie_ID INTEGER NOT NULL," +
            "     Screen_ID INTEGER NOT NULL," +
            "     Show_Date VARCHAR(25) NOT NULL," +
            "     Show_Time VARCHAR(25) NOT NULL," +
            "     FOREIGN KEY (Movie_ID) REFERENCES Movies(Movie_ID)," +
            "     FOREIGN KEY (Screen_ID) REFERENCES Screens(Screen_ID)" +
            ");";

        try (Connection conn = connect(database); Statement stmt = conn.createStatement()) {
            stmt.execute(createVenues);
            stmt.execute(createTheaters);
            stmt.execute(createScreens);
            stmt.execute(createMovies);
            stmt.execute(createShowtimes);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
