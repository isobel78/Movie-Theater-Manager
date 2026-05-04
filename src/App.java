/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 2, 2026
 * 
 * Main application class.
 * 
 * Users login with their Venue ID to manage theaters and showtimes at their specific venue. Admins can login with the code 999 to manage all records across venues.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    //constants
    private static final int ADMIN_CODE = 999;
    private static Connection conn;
    private static Scanner scanner;
    private static int venueID; 
    private static boolean isAdmin;
    
    public static void main(String[] args) throws Exception {
        //connect/create database
        final String dbName = "DriveInSystem.db";
        SQLiteDatabase.initializeDatabase(dbName);
        conn = SQLiteDatabase.connect(dbName);

        if (conn == null) {
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }

        scanner = new Scanner(System.in);


        //populate sample data only when the database is empty
        if (TheaterDB.getAllTheaters(conn, ADMIN_CODE).isEmpty()) {
            sampleData();
        }

        //initial login screen
        System.out.println();
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*                      Welcome to the Drive-In!                         *-");
        System.out.println("-*                   Your Theater Management System                      *-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

        //call login() - enter Venue ID or 999 for admin access
        login();

        //display temporary sample data
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*-*- Sample Theater Showtimes to demonstrate Database Implementation -*-*-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println();
        for (Theater t : TheaterDB.getAllTheaters(conn, ADMIN_CODE)) {
            System.out.println(t.display());
            ArrayList<Screen> screens = ScreenDB.getScreensByTheater(conn, t.getTheaterID());
            for (Screen s : screens) {
                for (Showtime st : ShowtimeDB.getShowtimesByScreen(conn, s.getScreenID())) {
                    System.out.println(st.display());
                }
            }
            System.out.println();
        }
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");

        //display the main menu - options will differ based on admin vs venue login
        mainMenu();

        //close connection and scanner before exiting
        conn.close();
        scanner.close();

    }


    //FUNCTION to populate sample data to demonstrate Database Implementation
    private static void sampleData() {
        // Venues
        VenueDB.addVenue(conn, new Theater(0, 0, "Bengie's Drive-In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Skyline Drive-In",  "", "", "", ""));

        // Theaters
        TheaterDB.addTheater(conn, new Theater(0, 1, "Bengie's Drive-In",
            "3417 Eastern Blvd",   "Middle River", "MD", "21220"));
        TheaterDB.addTheater(conn, new Theater(0, 2, "Skyline Drive-In",
            "31175 Old Hwy 58",    "Barstow",      "CA", "92311"));
        TheaterDB.addTheater(conn, new Theater(0, 2, "Skyline Drive-In",
            "5600 Hodgenville Rd", "Greensburg",   "KY", "42743"));

        // Screens
        ScreenDB.addScreen(conn, new Screen(0, 1, 500, 1));
        ScreenDB.addScreen(conn, new Screen(0, 1, 150, 2));
        ScreenDB.addScreen(conn, new Screen(0, 2, 300, 2));
        ScreenDB.addScreen(conn, new Screen(0, 1, 180, 3));
        ScreenDB.addScreen(conn, new Screen(0, 2, 200, 3));

        // Movies
        MovieDB.addMovie(conn, new Movie(0, "Project Hail Mary", "Sci-Fi/Adventure", "PG-13", 157));
        MovieDB.addMovie(conn, new Movie(0, "Michael", "Biography/Drama", "PG-13", 127));
        MovieDB.addMovie(conn, new Movie(0, "Lee Cronin's The Mummy", "Horror", "R", 133));
        MovieDB.addMovie(conn, new Movie(0, "Lorne", "Documentary", "R", 101));

        // Showtimes
        Movie  movie1  = MovieDB.getMovie(conn, 1);
        Movie  movie2  = MovieDB.getMovie(conn, 2);
        Movie  movie3  = MovieDB.getMovie(conn, 3);
        Movie  movie4  = MovieDB.getMovie(conn, 4);
        Screen screen1 = ScreenDB.getScreen(conn, 1);
        Screen screen4 = ScreenDB.getScreen(conn, 4);
        Screen screen5 = ScreenDB.getScreen(conn, 5);

        ShowtimeDB.addShowtime(conn, new Showtime(0, movie1, screen1, "2026-05-01", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movie3, screen1, "2026-05-01", "23:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movie2, screen4, "2026-05-01", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movie4, screen4, "2026-05-01", "22:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movie2, screen5, "2026-05-01", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movie4, screen5, "2026-05-01", "23:00"));
    }


    //USER LOGIN
    private static void login() {
        System.out.println("\nEnter your Venue ID to log in.");
        System.out.println("(or enter 999 for full admin access)");

        while (true) {
            System.out.print("\nVenue ID: ");
            int id = readInt();

            if (id == -1) {
                System.out.println("  Invalid input. Please enter a number.");
                continue;
            }

            //admin login
            if (id == ADMIN_CODE) {
                isAdmin = true;
                venueID = ADMIN_CODE;
                System.out.println("\nLogged in as Administrator.");
                System.out.println();
                return;
            }

            //venue login 
            Object[] venue = VenueDB.getVenue(conn, id);
            String venueName = venue != null ? venue[1].toString() : null;
            if (venueName != null) {
                isAdmin = false;
                venueID = id;
                System.out.println("\nLogged in as: " + venueName + " (Venue ID: " + id + ")");
                System.out.println();
                return;
            }

            System.out.println("Venue ID " + id + " not found. Please try again.");
            System.out.println();
        }
    }

    //MAIN MENU
    private static void mainMenu() {
        while (true) {
            System.out.println("Main Menu (Work In Progress)");
            System.out.println("  1. Manage Theaters (Almost Complete)");
            System.out.println("  2. Manage Movies (Coming Soon)");
            System.out.println("  3. Manage Showtimes (Coming Soon)");
            System.out.println("  0. Exit");
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": theaterMenu(); 
                          break;
                case "2": movieMenu(); 
                          break;
                case "3": showtimeMenu(); 
                          break;
                case "0": System.out.println("\nThank you for using the Drive-In Theater Management System!");
                          System.out.println("Goodbye!\n");
                          return;
                default:
                    System.out.println("  Invalid choice. Please enter a number between 0 and 3.");
                    System.out.println();
            }
        }
    }

    //MENU - 1. MANAGE THEATERS
    private static void theaterMenu() {
        String label = isAdmin ? "(Admin - All Venues)" : "(Venue ID " + venueID + ")";

        while (true) {
            System.out.println("\nManage Theaters " + label);
            System.out.println("  1. List Theaters");
            System.out.println("  2. Add Theater");
            System.out.println("  3. Edit Theater");
            System.out.println("  4. Delete Theater");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": TheaterDB.listTheaters(conn, venueID);  
                          break;
                case "2": addTheater();                           break;
                case "3": editTheater();                          break;
                case "4": deleteTheater();                        break;
                case "0": return;
                default:  System.out.println("  Invalid choice. Please enter a number between 0 and 4.");
            }
        }
    }


    //MENU - 1. MANAGE THEATERS --> 2. ADD THEATER
    private static void addTheater() {
        System.out.println("\n--- Add Theater ---");

        //admin needs to pick a venue; venue users are locked to their own venue
        int newVenueID;
        if (isAdmin) {
            VenueDB.listVenues(conn);
            System.out.print("  Venue ID: ");
            newVenueID = readInt();
            if (newVenueID <= 0) return;
        } else {
            newVenueID = venueID;
        }

        // VenueDB validates the venue and returns its name
        Object[] venue = VenueDB.getVenue(conn, newVenueID);
        String venueName = (String) venue[1];

        if (venueName == null) {
            System.out.println("  Venue ID " + newVenueID + " not found. Theater not added.");
            return;
        }

        System.out.print("  Address:          ");
        String address = scanner.nextLine().trim();

        System.out.print("  City:             ");
        String city = scanner.nextLine().trim();

        System.out.print("  State (2-letter): ");
        String state = scanner.nextLine().trim().toUpperCase();

        System.out.print("  Zip:              ");
        String zip = scanner.nextLine().trim();

        TheaterDB.addTheater(conn, new Theater(0, newVenueID, venueName, address, city, state, zip));
        System.out.println("  Theater added successfully.");
    }

    //MENU - 1. MANAGE THEATERS --> 3. EDIT THEATER
    private static void editTheater() {
        System.out.println("\n--- Edit Theater ---");
        TheaterDB.listTheaters(conn, venueID);

        System.out.print("\nEnter Theater ID to edit (or 0 to cancel): ");
        int id = readInt();
        if (id <= 0) return;

        //retrieve theater record and verify it belongs to this user's venue
        Theater t = TheaterDB.getTheater(conn, id);

        if (t == null || (!isAdmin && t.getVenueID() != venueID)) {
            System.out.println("  Theater not found or not accessible.");
            return;
        }

        System.out.println("  Current values shown in brackets. Press Enter to keep.");

        System.out.print("  Address  [" + t.getAddress() + "]: ");
        String address = blankOr(scanner.nextLine().trim(), t.getAddress());

        System.out.print("  City     [" + t.getCity() + "]: ");
        String city = blankOr(scanner.nextLine().trim(), t.getCity());

        System.out.print("  State    [" + t.getState() + "]: ");
        String rawState = scanner.nextLine().trim();
        String state = rawState.isEmpty() ? t.getState() : rawState.toUpperCase();

        System.out.print("  Zip      [" + t.getZip() + "]: ");
        String zip = blankOr(scanner.nextLine().trim(), t.getZip());

        TheaterDB.updateTheater(conn, new Theater(id, t.getVenueID(), t.getVenueName(), address, city, state, zip));
        System.out.println("  Theater updated successfully.");
    }

    //MENU - 1. MANAGE THEATERS --> 4. DELETE THEATER
    private static void deleteTheater() {
        System.out.println("\n--- Delete Theater ---");
        TheaterDB.listTheaters(conn, venueID);

        System.out.print("\nEnter Theater ID to delete (or 0 to cancel): ");
        int id = readInt();
        if (id <= 0) return;

        Theater t = TheaterDB.getTheater(conn, id);
        if (t == null || (!isAdmin && t.getVenueID() != venueID)) {
            System.out.println("  Theater not found or not accessible.");
            return;
        }

        System.out.print("  Delete \"" + t.getVenueName() + " - " + t.getCity()
            + "\"? This will also remove all its screens and showtimes. (y/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("  Cancelled.");
            return;
        }

        //Remember: TheaterDB.deleteTheater handles the cascade (showtimes -> screens -> theater)
        TheaterDB.deleteTheater(conn, id);
        System.out.println("  Theater deleted.");
    }


    //MENU - 2. MANAGE MOVIES
    private static void movieMenu() {
        String label = isAdmin ? "(Admin - All Movies)" : "(Venue ID " + venueID + ")";

        while (true) {
            System.out.println("Manage Movies " + label);
            System.out.println("  1. List Movies");
            System.out.println("  2. Add Movie");
            System.out.println("  3. Edit Movie");
            System.out.println("  4. Delete Movie");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nChoice: ");
            
            switch (scanner.nextLine().trim()) {
                /*
                case "1": MovieDB.listMovies(conn, venueID); break;
                case "2": addMovie();                        break;
                case "3": editMovie();                       break;
                case "4": deleteMovie();                     break;
                */
                case "0": return;
                default:  System.out.println("  Invalid choice. Please enter 0-4.");
            }
        }
    }

    //MENU - 2. MANAGE MOVIES --> 1. LIST MOVIES
    //MENU - 2. MANAGE MOVIES --> 2. ADD MOVIE
    //MENU - 2. MANAGE MOVIES --> 3. EDIT MOVIE
    //MENU - 2. MANAGE MOVIES --> 4. DELETE MOVIE

    //MENU - 3. MANAGE SHOWTIMES
     private static void showtimeMenu() {
        String label = isAdmin ? "(Admin - All Theaters)" : "(Venue ID " + venueID + ")";

        while (true) {
            System.out.println("Schedule Showtimes " + label);
            System.out.println("  1. View Showtimes");
            System.out.println("  2. Add Showtime");
            System.out.println("  3. Edit Showtime");
            System.out.println("  4. Delete Showtime");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nChoice: ");
        
            switch (scanner.nextLine().trim()) {
                /*
                case "1": ShowtimeDB.listShowtimes(conn, venueID); break;
                case "2": addShowtime();                           break;
                case "3": editShowtime();                          break;
                case "4": deleteShowtime();                        break;
                 */
                case "0": return;
                default:  System.out.println("  Invalid choice. Please enter 0-4.");
            }
        }

        //MENU - 3. MANAGE SHOWTIMES --> 1. VIEW SHOWTIMES
        //MENU - 3. MANAGE SHOWTIMES --> 2. ADD SHOWTIME
        //MENU - 3. MANAGE SHOWTIMES --> 3. EDIT SHOWTIME
        //MENU - 3. MANAGE SHOWTIMES --> 4. DELETE SHOWTIME

    }


    //FUNCTION: Input helper - read an integer from the console with error handling
    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number - returning to menu.");
            return -1;
        }
    }

    //FUNCTION: Input helper - Return newVal if non-empty, otherwise keep the existing value
    private static String blankOr(String newVal, String existing) {
        return newVal.isEmpty() ? existing : newVal;
    }

}