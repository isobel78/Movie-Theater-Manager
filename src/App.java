/************************************************
 * Name: Atlanta Daniel
 * Date: April 24, 2026
 * Assignment: SDC330 Course Project
 * 
 * Main application class.
 */

import java.util.ArrayList;

public class App {

    static ArrayList<Theater> theaters = new ArrayList<>();
    static ArrayList<Movie> movies = new ArrayList<>();
    static ArrayList<Showtime> showtimes = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        System.out.println("\nWelcome to the Drive-In!\nTheater Management System\n");
        System.out.println("\nSample Menu\n");
        System.out.println("1. Manage Theaters\n");
        System.out.println("2. Manage Movies\n");
        System.out.println("3. Schedule Showtimes\n");
        System.out.println("0. Exit\n");

        //create theaters (remember Venue is your abstract class)
        Theater theater1 = new Theater(1, 2, "Bengie's Drive-In", "3417 Eastern Blvd", "Middle River", "MD", "21220");
        Theater theater2 = new Theater(2, 1, "Skyline Drive-In", "31175 Old Hwy 58", "Barstow", "CA", "92311");
        Theater theater3 = new Theater(3, 1, "Skyline Drive-In", "5600 Hodgenville Rd", "Greensburg", "KY", "42743");


        //create screens and add to theaters
        //theater 1
        Screen screen1 = new Screen(1, 1, 500, theater1.getTheaterID());
        theater1.addScreen(screen1);

        //theater 2
        Screen screen2 = new Screen(2, 1, 150, theater2.getTheaterID());
        Screen screen3 = new Screen(3, 2, 300, theater2.getTheaterID());
        theater2.addScreen(screen2);
        theater2.addScreen(screen3);

        //theater 3
        Screen screen4 = new Screen(4, 1, 180, theater3.getTheaterID());
        Screen screen5 = new Screen(5, 2, 200, theater3.getTheaterID());
        theater3.addScreen(screen4);
        theater3.addScreen(screen5);

        //add to tehaters arraylist
        theaters.add(theater1);
        theaters.add(theater2);
        theaters.add(theater3);


        //create movies
        Movie movie1 = new Movie(1, "Project Hail Mary", "Sci-Fi/Adventure", "PG-13", 157);
        Movie movie2 = new Movie(2, "Michael", "Biography/Drama", "PG-13", 127);
        Movie movie3 = new Movie(3,"Lee Cronin's The Mummy", "Horror", "R", 133);
        Movie movie4 = new Movie(4, "Lorne", "Documentary", "R", 101);

        //add to movies arraylist
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);


        //create showtimes and assign to screens
        //theater 1
        Showtime showTime1 = new Showtime(1, movie1, screen1, "2026-05-01",  "20:00");
        Showtime showTime2 = new Showtime(2, movie3, screen1, "2026-05-01", "23:00");
        theater1.addShowtime(showTime1);
        theater1.addShowtime(showTime2);

        //theater 3, has 2 screens
        //showtimes for screen 1
        Showtime showTime3 = new Showtime(3, movie2, screen4, "2026-05-01", "20:00");
        Showtime showTime4 = new Showtime(4, movie2, screen5, "2026-05-01", "20:30");
        theater3.addShowtime(showTime3);
        theater3.addShowtime(showTime4);
        //showtimes for screen 2
        Showtime showTime5 = new Showtime(3, movie4, screen4, "2026-05-01", "22:30");
        Showtime showTime6 = new Showtime(4, movie4, screen5, "2026-05-01", "23:00");
        theater3.addShowtime(showTime5);
        theater3.addShowtime(showTime6);

        //add to showtimes arraylist
        showtimes.add(showTime1); 
        showtimes.add(showTime2);
        showtimes.add(showTime3); 
        showtimes.add(showTime4);
        showtimes.add(showTime5); 
        showtimes.add(showTime6);



        //display theaters and their showtimes
        System.out.println("\nSample Theater Showtimes:\n");
        for (Theater t : theaters) {
            ArrayList<Showtime> tShowtimes = t.getShowtimes();
 
            //display theater info
            System.out.println(t.display());
 
            //display showtimes
            for (Showtime st : tShowtimes) {
                System.out.println(st.display());
            }

            System.out.println(); //blank line between theaters
        }


    }
}
