/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 * 
 * Main application class.
 * 
 * Users login with their Venue ID to manage theaters and showtimes at their specific venue. Admins can login with the code 999 to manage all records across venues.
 */

import java.sql.Connection;
import java.util.Scanner;
import java.util.ArrayList;

public class App {

    //constants
    private static final int ADMIN_CODE = 999;
    private static Connection conn;
    private static Scanner scanner;
    private static int venueID; 
    private static boolean isAdmin;

    // Color constants
    public static final String RESET = "\u001b[0m";
    public static final String CYAN = "\u001b[36m";
    public static final String GREEN = "\u001b[32m";
    public static final String YELLOW = "\u001b[33m";
    public static final String RED = "\u001b[31m";
    public static final String PURPLE = "\u001b[35m";
    
    public static void main(String[] args) throws Exception {

        //connect/create database
        final String dbName = "DriveInSystem.db";
        SQLiteDatabase.initializeDatabase(dbName);
        conn = SQLiteDatabase.connect(dbName);

        if (conn == null) {
            System.out.println(RED + "Failed to connect to the database. Exiting." + RESET);
            return;
        }

        scanner = new Scanner(System.in);

        //populate sample data only when the database is empty
        if (TheaterDB.getAllTheaters(conn, ADMIN_CODE).isEmpty()) {
            SampleData.populate(conn);
        }

        //initial login screen
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                      Welcome to the Drive-In!                         " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                  Your Theater Management System                       " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        //call login() - enter Venue ID or 999 for admin access
        login();

        //display the main menu - options will differ based on admin vs venue login
        mainMenu();

        //close connection and scanner before exiting
        conn.close();
        scanner.close();

    }


    // USER LOGIN
    private static void login() {
        System.out.println("\nEnter your Venue ID to log in.");

        while (true) {
            System.out.print("\nVenue ID: ");
            int id = readInt();

            if (id == -1) {
                System.out.println(RED + "  Invalid input. Please enter a number." + RESET);
                continue;
            }

            //admin login
            if (id == ADMIN_CODE) {
                isAdmin = true;
                venueID = ADMIN_CODE;
                System.out.println(GREEN + "\nLogged in as Administrator." + RESET);
                System.out.println();
                return;
            }

            //venue login 
            Object[] venue = VenueDB.getVenue(conn, id);
            String venueName = venue != null ? venue[1].toString() : null;
            if (venueName != null) {
                isAdmin = false;
                venueID = id;
                System.out.println(GREEN + "\nLogged in as: " + venueName + " (Venue ID: " + id + ")" + RESET);
                System.out.println();
                return;
            }

            System.out.println(RED + "Venue ID " + id + " not found. Please try again." + RESET);
            System.out.println();
        }
    }

    // MAIN MENU
    private static void mainMenu() {
        while (true) {
            String label = isAdmin ? "Admin - All Venues" : "Venue ID " + venueID;

            System.out.println();
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW +"-*" + RESET + "                            Main Menu                                  " + YELLOW + "*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(GREEN + "\n(Logged in as: " + label + ")" + RESET);
            System.out.println();
            System.out.println("  1. Manage Theaters");
            System.out.println("  2. Manage Movies");
            System.out.println("  3. Manage Showtimes");
            if (isAdmin) System.out.println("  4. Manage Venues");
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
                case "4": if (isAdmin) { 
                            venueMenu(); 
                          } else { 
                            System.out.println(RED + "  Invalid choice." + RESET);
                          }
                          break;
                case "0": goodbye();
                          return;
                default:
                    System.out.println(RED + "  Invalid choice. Please enter a number between 0 and 3." + RESET);
                    System.out.println();
            }
        }
    }

    private static void goodbye() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "      Thank you for using the Drive-In Theater Management System!      " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                               Goodbye                                 " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println();
    }


    // MENU - 1. MANAGE THEATERS
    private static void theaterMenu() {
        String label = isAdmin ? "Admin - All Venues" : "Venue ID " + venueID;

        while (true) {
            System.out.println();
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW +"-*" + RESET + "                           Manage Theaters                             " + YELLOW + "*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(GREEN + "\n(Logged in as: " + label + ")" + RESET);
            System.out.println();
            System.out.println("  1. List Theaters");
            System.out.println("  2. Add Theater");
            System.out.println("  3. Edit Theater");
            System.out.println("  4. Delete Theater");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": listTheaters(); 
                          pressEnterToContinue();
                          break;
                case "2": addTheater();
                          pressEnterToContinue();
                          break;
                case "3": if (editTheater()) {
                            pressEnterToContinue();
                          }
                          break;          
                case "4": if (deleteTheater()) {
                            pressEnterToContinue();
                          }
                          break;
                case "0": return;
                default:  System.out.println(RED + "  Invalid choice. Please enter a number between 0 and 4." + RESET);
            }
        }
    }

    // MENU - 1. MANAGE THEATERS --> 1. LIST THEATERS
    private static void listTheaters() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                            All Theaters                               " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        
        TheaterDB.listTheaters(conn, venueID);
    }   

    // MENU - 1. MANAGE THEATERS --> 2. ADD THEATER
    private static void addTheater() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                           Add New Theater                             " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        //admin needs to pick a venue; venue users are locked to their own venue
        int newVenueID;
        if (isAdmin) {
            System.out.println();
            System.out.println("Choose a Venue by ID");
            VenueDB.listVenues(conn);
            System.out.print("Venue ID: ");
            newVenueID = readInt();
            if (newVenueID <= 0) return;
        } else {
            newVenueID = venueID;
        }

        // VenueDB validates the venue and returns its name
        Object[] venue = VenueDB.getVenue(conn, newVenueID);
        String venueName = (String) venue[1];

        if (venueName == null) {
            System.out.println(RED + "  Venue ID " + newVenueID + " not found. Theater not added." + RESET);
            return;
        }

        System.out.print("\tAddress: ");
        String address = scanner.nextLine().trim();

        System.out.print("\tCity: ");
        String city = scanner.nextLine().trim();

        System.out.print("\tState (2-letter): ");
        String state = scanner.nextLine().trim().toUpperCase();

        System.out.print("\tZip: ");
        String zip = scanner.nextLine().trim();

        int newTheaterID = TheaterDB.addTheater(conn, new Theater(0, newVenueID, venueName, address, city, state, zip));
        System.out.println(CYAN + "\nTheater added successfully." + RESET);

        // Prompt for number of screens to add to this theater
        System.out.print("\n\nHow many screens does this theater have? ");
        int numScreens = readInt();
        if (numScreens <= 0) {
            System.out.println(RED + "  No screens added. You can add them later via Edit Theaters." + RESET);
            return;
        }

        for (int i = 1; i <= numScreens; i++) {
            System.out.print("\tScreen " + i + " - Vehicle Capacity: ");
            int capacity = readInt();
            if (capacity <= 0) {
                System.out.println(RED + "  Invalid capacity. Skipping screen " + i + "." + RESET);
                continue;
            }
            ScreenDB.addScreen(conn, new Screen(0, i, capacity, newTheaterID));
        }

        System.out.println(CYAN + "\n" + numScreens + " screen(s) added to the new theater." + RESET);
    }

    // MENU - 1. MANAGE THEATERS --> 3. EDIT THEATER
    private static boolean editTheater() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                           Edit A Theater                              " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        TheaterDB.listTheaters(conn, venueID);

        System.out.print("\nEnter Theater ID to edit (or 0 to cancel): ");
        int id = readInt();
        if (id <= 0) {
            return false; // user cancelled edit
        }

        //retrieve theater record and verify it belongs to this user's venue
        Theater t = TheaterDB.getTheater(conn, id);

        if (t == null || (!isAdmin && t.getVenueID() != venueID)) {
            System.out.println(RED + "  Theater not found or not accessible." + RESET);
            return true;
        }

        //edit theater address
        System.out.println("\tCurrent values shown in brackets. Press Enter to keep.\n");

        System.out.print("\tAddress:  [" + t.getAddress() + "]  ");
        String address = blankOr(scanner.nextLine().trim(), t.getAddress());

        System.out.print("\tCity:  [" + t.getCity() + "]  ");
        String city = blankOr(scanner.nextLine().trim(), t.getCity());

        System.out.print("\tState  [" + t.getState() + "]  ");
        String rawState = scanner.nextLine().trim();
        String state = rawState.isEmpty() ? t.getState() : rawState.toUpperCase();

        System.out.print("\tZip  [" + t.getZip() + "]  ");
        String zip = blankOr(scanner.nextLine().trim(), t.getZip());

        TheaterDB.updateTheater(conn, new Theater(id, t.getVenueID(), t.getVenueName(), address, city, state, zip));
        System.out.println(CYAN + "\nTheater updated successfully!" + RESET);

        //manage screens for this theater
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                      Manage Theater Screens                           " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        while (true) {
            System.out.println("\nCurrent Screens:");
            ScreenDB.listScreensByTheater(conn, id);
            System.out.println();
            System.out.println("\t1. Add Screen");
            System.out.println("\t2. Delete Screen");
            System.out.println("\t0. Done");
            System.out.print("\n\tEnter your choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": // Add a new screen
                          System.out.println("\nAdd a New Screen");
                          System.out.print("\tVehicle Capacity: ");
                          int capacity = readInt();
                          if (capacity <= 0) {
                                System.out.println(RED + "\nInvalid capacity. Screen not added." + RESET);
                                pressEnterToContinue();
                                break;
                          }
                    
                          // Determine the next screen number (max existing + 1)
                          ArrayList<Screen> existing = ScreenDB.getScreensByTheater(conn, id);
                          int nextNum = 1;
                          for (Screen s : existing) {
                                if (s.getScreenNumber() >= nextNum) nextNum = s.getScreenNumber() + 1;
                          }

                          ScreenDB.addScreen(conn, new Screen(0, nextNum, capacity, id));
                          System.out.println(CYAN + "\nScreen #" + nextNum + " added." + RESET);
                          pressEnterToContinue();
                          break;

                case "2": // Delete a screen
                          System.out.print("\nEnter Screen ID to delete: ");
                          int delScreenID = readInt();
                          if (delScreenID <= 0) break;
                          
                          Screen toDel = ScreenDB.getScreen(conn, delScreenID);
                          if (toDel == null || toDel.getTheaterID() != id) {
                                System.out.println(RED + "\t  Screen not found at this theater." + RESET);
                                pressEnterToContinue();
                                break;
                          }
                    
                          System.out.println(RED + "\tDelete Screen #" + toDel.getScreenNumber() + "?" + RESET);
                          System.out.print(RED + "\tThis will also remove all its showtimes. (y/n): " + RESET);
                          if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                                ShowtimeDB.deleteShowtimesByScreen(conn, delScreenID);
                                ScreenDB.deleteScreen(conn, delScreenID);
                                System.out.println(CYAN + "\nScreen deleted." + RESET);
                                pressEnterToContinue();
                          } else {
                                System.out.println(RED + "\nCancelled." + RESET);
                                pressEnterToContinue();
                          }
                          break;

                case "0": return true;

                default:  System.out.println(RED + "  Invalid choice. Please enter a number 1, 2, or 0." + RESET);
            }
        }
    }

    // MENU - 1. MANAGE THEATERS --> 4. DELETE THEATER
    private static boolean deleteTheater() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                          Delete A Theater                             " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        TheaterDB.listTheaters(conn, venueID);

        System.out.print("\nEnter Theater ID to delete (or 0 to cancel): ");
        int id = readInt();
        if (id <= 0) {
            return false; // user cancelled edit
        }

        Theater t = TheaterDB.getTheater(conn, id);
        if (t == null || (!isAdmin && t.getVenueID() != venueID)) {
            System.out.println(RED + "\tTheater not found or not accessible." + RESET);
            return true;
        }

        System.out.println(RED + "\tDelete " + t.getVenueName() + " - " + t.getCity() + "?" + RESET);
        System.out.println(RED + "\tThe following screens (and all their showtimes) will also be removed:" + RESET);
        ArrayList<Screen> screens = ScreenDB.getScreensByTheater(conn, id);
        if (screens.isEmpty()) {
            System.out.println("\t(no screens)");
        } else {
            ScreenDB.listScreensByTheater(conn, id);
        }
        System.out.print(RED + "\n\tConfirm delete? (y/n): " + RESET);
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println(RED + "\nDelete Cancelled." + RESET);
            return true;
        }

        //Remember: TheaterDB.deleteTheater handles the cascade (showtimes -> screens -> theater)
        TheaterDB.deleteTheater(conn, id);
        System.out.println(CYAN + "\nTheater deleted." + RESET);

        return true;
    }

    // MENU - 1. MANAGE THEATERS --> 4. MANAGE VENUES (Admin only)
    private static void venueMenu() {
        while (true) {
            System.out.println();
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW +"-*" + RESET + "                           Manage Venues                               " + YELLOW + "*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(GREEN + "\n(Logged in as: Admin)" + RESET);
            System.out.println();
            System.out.println("  1. List Venues");
            System.out.println("  2. Add Venue");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nEnter your choice: ");

            switch (scanner.nextLine().trim()) {
                case "1": listVenues();
                          pressEnterToContinue();
                          break;
                case "2": addVenue();
                          pressEnterToContinue();
                          break;
                case "0": return;
                default:  System.out.println(RED + "  Invalid choice. Please enter a number 1, 2, or 0." + RESET);
            }
        }
    }

    // MENU - 1. MANAGE THEATERS --> 4. MANAGE VENUES --> 1. LIST VENUES
    private static void listVenues() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                             All Venues                                " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println();
        VenueDB.listVenues(conn);
    }

    // MENU - 1. MANAGE THEATERS --> 4. MANAGE VENUES --> 2. ADD VENUE
    private static void addVenue() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET); 
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                            Add New Venue                              " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        System.out.println("\nExisting venues:");
        VenueDB.listVenues(conn);

        System.out.print("\tVenue Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(RED + "  Name cannot be blank. Venue not added." + RESET);
            return;
        }

        int newID = VenueDB.addVenue(conn, name);
        if (newID > 0) {
            System.out.println("\nVenue added successfully. (Venue ID: " + newID + ")");
        } else {
            System.out.println(RED + "\nFailed to add venue." + RESET);
        }
    }


    // MENU - 2. MANAGE MOVIES
    private static void movieMenu() {
        String label = isAdmin ? "Admin - All Venues" : "Venue ID " + venueID;

        while (true) {
            System.out.println();
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW +"-*" + RESET + "                            Manage Movies                              " + YELLOW + "*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(GREEN + "\n(Logged in as: " + label + ")" + RESET);
            System.out.println();
            System.out.println("  1. List Movies");
            System.out.println("  2. Add Movie");
            System.out.println("  3. Edit Movie");
            System.out.println("  4. Delete Movie");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nEnter your Choice: ");
            
            switch (scanner.nextLine().trim()) {
                case "1": listMovies();
                          pressEnterToContinue();
                          break;
                case "2": addMovie();
                          pressEnterToContinue();
                          break;
                case "3": if (editMovie()) {
                            pressEnterToContinue();
                          }
                          break;
                case "4": if (deleteMovie()) {
                            pressEnterToContinue();
                          }
                          break;
                case "0": return;
                default:  System.out.println(RED + "  Invalid choice. Please enter a number between 0 and 4." + RESET);
            }
        }
    }

    // MENU - 2. MANAGE MOVIES --> 1. LIST MOVIES
    private static void listMovies() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                             All Movies                                " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        
        MovieDB.listMovies(conn, venueID); 
    }

    // MENU - 2. MANAGE MOVIES --> 2. ADD MOVIE
    private static void addMovie() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                            Add New Movie                              " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        System.out.print("\tTitle:  ");
        String title = scanner.nextLine().trim();

        System.out.print("\tGenre:  ");
        String genre = scanner.nextLine().trim();

        System.out.print("\tRating (G/PG/PG-13/R/NC-17/NR):  ");
        String rating = scanner.nextLine().trim().toUpperCase();

        System.out.print("\tRuntime (minutes):  ");
        int runtime = readInt();
        if (runtime <= 0) return;

        MovieDB.addMovie(conn, new Movie(0, title, genre, rating, runtime));
        System.out.println(CYAN + "\nMovie added successfully." + RESET);
    }

    // MENU - 2. MANAGE MOVIES --> 3. EDIT MOVIE
    private static boolean editMovie() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                           Edit A Movie                                " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        MovieDB.listMovies(conn, venueID);

        System.out.print("\nEnter Movie ID to edit (or 0 to cancel): ");
        int id = readInt();

        if (id <= 0) {
            return false; // user cancelled edit
        }

        Movie m = MovieDB.getMovie(conn, id);
        if (m == null) {
            System.out.println(RED + "  Movie ID " + id + " not found." + RESET);
            return true;
        }

        System.out.println("\nCurrent values shown in brackets. Press Enter to keep.");

        System.out.print("\tTitle:  [" + m.getTitle() + "]  ");
        String title = blankOr(scanner.nextLine().trim(), m.getTitle());

        System.out.print("\tGenre:  [" + m.getGenre() + "]  ");
        String genre = blankOr(scanner.nextLine().trim(), m.getGenre());

        System.out.print("\tRating:  [" + m.getRating() + "]  ");
        String rawRating = scanner.nextLine().trim();
        String rating = rawRating.isEmpty() ? m.getRating() : rawRating.toUpperCase();

        System.out.print("\tRuntime: [" + m.getRuntime() + " min]  ");
        String rawRuntime = scanner.nextLine().trim();
        int runtime;
        if (rawRuntime.isEmpty()) {
            // User hit Enter, keep the original value
            runtime = m.getRuntime();
        } else {
            // update runtime with validation
            try {
                runtime = Integer.parseInt(rawRuntime);
            } catch (NumberFormatException e) {
                System.out.println(RED + "  Invalid number. Keeping original value." + RESET);
                runtime = m.getRuntime();
            }
        }

        MovieDB.updateMovie(conn, new Movie(id, title, genre, rating, runtime));
        System.out.println(CYAN + "\nMovie updated successfully." + RESET);

        return true;
    }   
        
    // MENU - 2. MANAGE MOVIES --> 4. DELETE MOVIE
    private static boolean deleteMovie() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                          Delete A Movie                               " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        MovieDB.listMovies(conn, venueID);

        System.out.print("\nEnter Movie ID to delete (or 0 to cancel): ");
        int id = readInt();
        
        if (id <= 0) {
            return false; // user cancelled edit
        }

        Movie m = MovieDB.getMovie(conn, id);
        if (m == null) {
            System.out.println(RED + "  Movie ID " + id + " not found." + RESET);
            return true;
        }

        System.out.println(RED + "  Delete \"" + m.getTitle() + "\"?" + RESET);
        System.out.print(RED + "  This will also remove all its showtimes. (y/n): " + RESET);
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println(RED + "\nDelete Cancelled." + RESET);
            return true;
        }

        // Delete all showtimes for this movie first, then delete the movie
        ShowtimeDB.deleteShowtimesByMovie(conn, id);
        MovieDB.deleteMovie(conn, id);
        System.out.println(CYAN + "\nMovie deleted." + RESET);

        return true;
    }


    // MENU - 3. MANAGE SHOWTIMES
    private static void showtimeMenu() {
        String label = isAdmin ? "Admin - All Venues" : "Venue ID " + venueID;

        while (true) {
            System.out.println();
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW +"-*" + RESET + "                          Manage Showtimes                             " + YELLOW + "*-" + RESET);
            System.out.println(YELLOW + "-*                                                                       *-" + RESET);
            System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
            System.out.println(GREEN + "\n(Logged in as: " + label + ")" + RESET);
            System.out.println();
            System.out.println("  1. View Showtimes");
            System.out.println("  2. Add Showtime");
            System.out.println("  3. Edit Showtime");
            System.out.println("  4. Delete Showtime");
            System.out.println("  0. Back to Main Menu");
            System.out.print("\nEnter your Choice: ");
        
            switch (scanner.nextLine().trim()) {
                case "1": listShowtimes();
                          pressEnterToContinue();
                          break;
                case "2": addShowtime();  
                          pressEnterToContinue();                         
                          break;
                case "3": if (editShowtime()) {
                            pressEnterToContinue();
                          }
                          break; 
                case "4": deleteShowtime();
                          pressEnterToContinue();                       
                          break;
                case "0": return;
                default:  System.out.println(RED + "  Invalid choice. Please enter 0-4." + RESET);
            }
        }
     }

    // MENU - 3. MANAGE SHOWTIMES --> 1. LIST SHOWTIMES
    private static void listShowtimes() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                           All Showtimes                               " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        
        ShowtimeDB.listShowtimes(conn, venueID); 
    }

    // MENU - 3. MANAGE SHOWTIMES --> 2. ADD SHOWTIME
    private static void addShowtime() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                          Add New Showtime                             " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);

        // Pick theater
        System.out.println("Choose a Theater by ID");
        TheaterDB.listTheaters(conn, venueID);
        System.out.print("\nTheater ID: ");
        int theaterID = readInt();
        if (theaterID <= 0) return;

        Theater t = TheaterDB.getTheater(conn, theaterID);
        if (t == null || (!isAdmin && t.getVenueID() != venueID)) {
            System.out.println(RED + "  Theater not found or not accessible." + RESET);
            return;
        }

        // Pick screen (ScreenDB lists screens for the selected theater)
        ScreenDB.listScreensByTheater(conn, theaterID);
        System.out.print("\nScreen ID: ");
        int screenID = readInt();
        if (screenID <= 0) return;

        Screen screen = ScreenDB.getScreen(conn, screenID);
        if (screen == null || screen.getTheaterID() != theaterID) {
            System.out.println(RED + "  Screen not found at this theater." + RESET);
            return;
        }

        // Pick movie (admin sees all; venue user sees their scoped list)
        MovieDB.listMovies(conn, venueID);
        System.out.print("\nMovie ID: ");
        int movieID = readInt();
        if (movieID <= 0) return;

        Movie movie = MovieDB.getMovie(conn, movieID);
        if (movie == null) {
            System.out.println(RED + "  Movie ID " + movieID + " not found." + RESET);
            return;
        }

        System.out.print("  Show Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("  Show Time (HH:MM): ");
        String time = scanner.nextLine().trim();

        ShowtimeDB.addShowtime(conn, new Showtime(0, movie, screen, date, time));
        System.out.println(CYAN + "\nShowtime added successfully." + RESET);
    }

    
    // MENU - 3. MANAGE SHOWTIMES --> 3. EDIT SHOWTIME
    private static boolean editShowtime() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                          Edit A Showtime                              " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        ShowtimeDB.listShowtimesWithID(conn, venueID);

        System.out.print("Enter Showtime ID to edit (or 0 to cancel): ");
        int id = readInt();
        
        if (id <= 0) {
            return false; // user cancelled edit
        }

        Showtime st = ShowtimeDB.getShowtime(conn, id);
        if (st == null || !canAccessShowtime(st)) {
            System.out.println(RED + " Showtime not found or not accessible." + RESET);
            return true;
        }

        System.out.println("\tCurrent values shown in brackets. Press Enter to keep.");

        //option to change the movie (if empty, keep the original)
        System.out.print("\tMovie ID: [" + st.getMovie().getMovieID()
            + " - " + st.getMovie().getTitle() + "] ");
        String movieInput = scanner.nextLine().trim();

        Movie movie;
        if (movieInput.isEmpty()) {
            movie = st.getMovie();
        } else {
            try {
                int newMovieID = Integer.parseInt(movieInput);
                movie = MovieDB.getMovie(conn, newMovieID);
            } catch (NumberFormatException e) {
                System.out.println(RED + "  Invalid Movie ID - returning to menu." + RESET);
                return true;
            }
        }

        if (movie == null) {
            System.out.println(RED + "  Movie not found." + RESET);
            return true;
        }

        System.out.print("\tShow Date: [" + st.getShowDate() + "] ");
        String date = blankOr(scanner.nextLine().trim(), st.getShowDate());

        System.out.print("\tShow Time: [" + st.getShowTime() + "] ");
        String time = blankOr(scanner.nextLine().trim(), st.getShowTime());

        ShowtimeDB.updateShowtime(conn, new Showtime(id, movie, st.getScreen(), date, time));
        System.out.println(CYAN + "\nShowtime updated successfully." + RESET);

        return true;
    }
    
    // MENU - 3. MANAGE SHOWTIMES --> 4. DELETE SHOWTIME
    private static void deleteShowtime() {
        System.out.println();
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET); 
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW +"-*" + RESET + "                          Delete A Showtime                            " + YELLOW + "*-" + RESET);
        System.out.println(YELLOW + "-*                                                                       *-" + RESET);
        System.out.println(YELLOW + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-" + RESET);
        ShowtimeDB.listShowtimesWithID(conn, venueID);

        System.out.print("Enter Showtime ID to delete (or 0 to cancel): ");
        int id = readInt();
        if (id <= 0) return;

        Showtime st = ShowtimeDB.getShowtime(conn, id);
        if (st == null || !canAccessShowtime(st)) {
            System.out.println(RED + "  Showtime not found or not accessible." + RESET);
            return;
        }

        System.out.print(RED +"  Delete showtime for \"" + st.getMovie().getTitle()
            + "\" on " + st.getShowDate() + " at " + st.getShowTime() + "? (y/n): " + RESET);
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println(RED + "\nCancelled." + RESET);
            return;
        }

            ShowtimeDB.deleteShowtime(conn, id);
            System.out.println(CYAN + "\nShowtime deleted." + RESET);
    }

    //checks whether the logged-in user can access a given showtime
    private static boolean canAccessShowtime(Showtime st) {
        if (isAdmin) return true;
        Theater t = TheaterDB.getTheater(conn, st.getScreen().getTheaterID());
        return t != null && t.getVenueID() == venueID;
    }



    // HELPER FUNCTIONS

    //FUNCTION: helper method to clear the console and wait for the user to hit Enter
    private static void pressEnterToContinue() {
        System.out.print(PURPLE + "\n--- Press Enter to return to the menu ---" + RESET);
        scanner.nextLine(); // Waits for the user to hit Enter
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