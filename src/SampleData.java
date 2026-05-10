/************************************************
 * Name: Atlanta Daniel
 * Assignment: SDC330 Course Project
 * Last Update: May 9, 2026
 *
 * This class contains sample data to populate the database on first run.
 * Call SampleData.populate(conn) from App.java when the database is empty.
 */

import java.sql.Connection;

public class SampleData {

    //FUNCTION: Populate the database with sample data
    //PARAMS: conn - the active database connection
    public static void populate(Connection conn) {

        /*
        VENUES  (Venue_ID assigned by DB autoincrement)
         1 = Bengie's Drive-In
         2 = Skyline Drive-In
         3 = Aut-O-Rama Twin Drive In
         4 = Becky's Drive In
         5 = West Wind Drive Ins
         6 = Ford Wyoming Drive In
         7 = Mahoning Drive In
        */
        VenueDB.addVenue(conn, new Theater(0, 0, "Bengie's Drive-In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Skyline Drive-In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Aut-O-Rama Twin Drive In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Becky's Drive In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "West Wind Drive Ins", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Ford Wyoming Drive In", "", "", "", ""));
        VenueDB.addVenue(conn, new Theater(0, 0, "Mahoning Drive In", "", "", "", ""));

        /*
        THEATERS  (Theater_ID assigned by DB autoincrement)
         1 = Bengie's — Middle River, MD
         2 = Skyline — Barstow, CA
         3 = Skyline — Greensburg, KY
         4 = Aut-O-Rama — North Ridgeville, OH
         5 = Becky's — Walnutport, PA
         6 = West Wind — San Jose, CA
         7 = West Wind — Concord, CA
         8 = West Wind — Sacramento, CA
         9 = Ford Wyoming — Dearborn, MI
         10 = Mahoning — Lehighton, PA
        */
        TheaterDB.addTheater(conn, new Theater(0, 1, "Bengie's Drive-In",
            "3417 Eastern Blvd", "Middle River", "MD", "21220"));
        TheaterDB.addTheater(conn, new Theater(0, 2, "Skyline Drive-In",
            "31175 Old Hwy 58", "Barstow", "CA", "92311"));
        TheaterDB.addTheater(conn, new Theater(0, 2, "Skyline Drive-In",
            "5600 Hodgenville Rd", "Greensburg", "KY", "42743"));
        TheaterDB.addTheater(conn, new Theater(0, 3, "Aut-O-Rama Twin Drive In",
            "33395 Lorain Rd", "North Ridgeville","OH", "44039"));
        TheaterDB.addTheater(conn, new Theater(0, 4, "Becky's Drive In",
            "4548 Lehigh Dr", "Walnutport",  "PA", "18088"));
        TheaterDB.addTheater(conn, new Theater(0, 5, "West Wind Drive Ins",
            "3630 Hillcap Ave", "San Jose", "CA", "95136"));
        TheaterDB.addTheater(conn, new Theater(0, 5, "West Wind Drive Ins",
            "1611 Solano Way", "Concord", "CA", "94520"));
        TheaterDB.addTheater(conn, new Theater(0, 5, "West Wind Drive Ins",
            "9616 Oates Dr", "Sacramento", "CA", "95827"));
        TheaterDB.addTheater(conn, new Theater(0, 6, "Ford Wyoming Drive In",
            "10400 Ford Rd", "Dearborn", "MI", "48126"));
        TheaterDB.addTheater(conn, new Theater(0, 7, "Mahoning Drive In",
            "635 Seneca Rd", "Lehighton", "PA", "18235"));

        /*
        SCREENS  (Screen_ID assigned by DB autoincrement)
        Screen(id, screenNumber, vehicleCapacity, theaterID)

        Theater 1 — Bengie's, Middle River MD 
        Theater 2 — Skyline, Barstow CA 
        Theater 3 — Skyline, Greensburg KY 
        Theater 4 — Aut-O-Rama, North Ridgeville OH
        Theater 5 — Becky's, Walnutport PA
        Theater 6 — West Wind, San Jose CA 
        Theater 7 — West Wind, Concord CA 
        Theater 8 — West Wind, Sacramento CA  
        Theater 9 — Ford Wyoming, Dearborn MI
        Theater 10 — Mahoning, Lehighton PA
        */

        // Theater 1 — Bengie's (2 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 500, 1));
        ScreenDB.addScreen(conn, new Screen(0, 2, 150, 1));

        // Theater 2 — Skyline Barstow (1 screen)
        ScreenDB.addScreen(conn, new Screen(0, 1, 300, 2));

        // Theater 3 — Skyline Greensburg (2 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 180, 3));
        ScreenDB.addScreen(conn, new Screen(0, 2, 200, 3));

        // Theater 4 — Aut-O-Rama (2 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 250, 4));
        ScreenDB.addScreen(conn, new Screen(0, 2, 250, 4));

        // Theater 5 — Becky's (2 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 200, 5));
        ScreenDB.addScreen(conn, new Screen(0, 2, 200, 5));

        // Theater 6 — West Wind San Jose (1 screen)
        ScreenDB.addScreen(conn, new Screen(0, 1, 350, 6));

        // Theater 7 — West Wind Concord (2 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 300, 7));
        ScreenDB.addScreen(conn, new Screen(0, 2, 300, 7));

        // Theater 8 — West Wind Sacramento (6 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 400, 8));
        ScreenDB.addScreen(conn, new Screen(0, 2, 400, 8));
        ScreenDB.addScreen(conn, new Screen(0, 3, 400, 8));
        ScreenDB.addScreen(conn, new Screen(0, 4, 400, 8));
        ScreenDB.addScreen(conn, new Screen(0, 5, 400, 8));
        ScreenDB.addScreen(conn, new Screen(0, 6, 400, 8));

        // Theater 9 — Ford Wyoming (5 screens)
        ScreenDB.addScreen(conn, new Screen(0, 1, 500, 9));
        ScreenDB.addScreen(conn, new Screen(0, 2, 500, 9));
        ScreenDB.addScreen(conn, new Screen(0, 3, 500, 9));
        ScreenDB.addScreen(conn, new Screen(0, 4, 500, 9));
        ScreenDB.addScreen(conn, new Screen(0, 5, 500, 9));

        // Theater 10 — Mahoning (1 screen)
        ScreenDB.addScreen(conn, new Screen(0, 1, 150, 10));

        /*
        MOVIES  (Movie_ID assigned by DB autoincrement)
         1  = Project Hail Mary
         2  = Michael
         3  = Lee Cronin's The Mummy
         4  = Lorne
         5  = Mortal Kombat II
         6  = The Devil Wears Prada 2
         7  = Hokum   
         8  = Mother Mary 
         9  = Deep Water 
         10 = Carrie 
         11 = Prom Night 
         12 = Hello Mary Lou: Prom Night II
        */
        MovieDB.addMovie(conn, new Movie(0, "Project Hail Mary", "Sci-Fi/Adventure", "PG-13", 157));
        MovieDB.addMovie(conn, new Movie(0, "Michael", "Biography/Drama", "PG-13", 127));
        MovieDB.addMovie(conn, new Movie(0, "Lee Cronin's The Mummy", "Horror", "R", 133));
        MovieDB.addMovie(conn, new Movie(0, "Lorne", "Documentary", "R", 101));
        MovieDB.addMovie(conn, new Movie(0, "Mortal Kombat II", "Action", "R", 120));
        MovieDB.addMovie(conn, new Movie(0, "The Devil Wears Prada 2", "Comedy/Drama", "PG-13", 110));
        MovieDB.addMovie(conn, new Movie(0, "Hokum", "Horror", "R", 107));
        MovieDB.addMovie(conn, new Movie(0, "Mother Mary", "Drama/Thriller", "R", 112));
        MovieDB.addMovie(conn, new Movie(0, "Deep Water", "Horror/Thriller", "R", 106));
        MovieDB.addMovie(conn, new Movie(0, "Carrie", "Horror", "R", 98));
        MovieDB.addMovie(conn, new Movie(0, "Prom Night", "Horror/Mystery", "R", 93));
        MovieDB.addMovie(conn, new Movie(0, "Hello Mary Lou: Prom Night II", "Horror", "R", 97));

        // SHOWTIMES
        //create movie objects
        Movie moviePHM = MovieDB.getMovie(conn, 1); // Project Hail Mary
        Movie movieMIC = MovieDB.getMovie(conn, 2); // Michael
        Movie movieMUM = MovieDB.getMovie(conn, 3); // Lee Cronin's The Mummy
        Movie movieLOR = MovieDB.getMovie(conn, 4); // Lorne
        Movie movieMK2 = MovieDB.getMovie(conn, 5); // Mortal Kombat II
        Movie movieDWP = MovieDB.getMovie(conn, 6); // The Devil Wears Prada 2
        Movie movieHOK  = MovieDB.getMovie(conn, 7); // Hokum
        Movie movieMOM  = MovieDB.getMovie(conn, 8); // Mother Mary
        Movie movieDEW  = MovieDB.getMovie(conn, 9); // Deep Water
        Movie movieCAR  = MovieDB.getMovie(conn, 10); // Carrie (1976)
        Movie moviePN   = MovieDB.getMovie(conn, 11); // Prom Night (1980)
        Movie movieHML  = MovieDB.getMovie(conn, 12); // Hello Mary Lou: Prom Night II

        //create screen objects
        Screen sc1  = ScreenDB.getScreen(conn, 1); // Bengie's screen 1
        Screen sc2  = ScreenDB.getScreen(conn, 2); // Bengie's screen 2

        Screen sc3  = ScreenDB.getScreen(conn, 3); // Skyline Barstow screen 1

        Screen sc4  = ScreenDB.getScreen(conn, 4); // Skyline Greensburg screen 1
        Screen sc5  = ScreenDB.getScreen(conn, 5); // Skyline Greensburg screen 2

        Screen sc6  = ScreenDB.getScreen(conn, 6); // Aut-O-Rama screen 1
        Screen sc7  = ScreenDB.getScreen(conn, 7); // Aut-O-Rama screen 2

        Screen sc8  = ScreenDB.getScreen(conn, 8); // Becky's screen 1
        Screen sc9  = ScreenDB.getScreen(conn, 9); // Becky's screen 2

        Screen sc10 = ScreenDB.getScreen(conn, 10); // West Wind San Jose screen 1

        Screen sc11 = ScreenDB.getScreen(conn, 11); // West Wind Concord screen 1
        Screen sc12 = ScreenDB.getScreen(conn, 12); // West Wind Concord screen 2

        Screen sc13 = ScreenDB.getScreen(conn, 13); // West Wind Sacramento screen 1
        Screen sc14 = ScreenDB.getScreen(conn, 14); // West Wind Sacramento screen 2
        Screen sc15 = ScreenDB.getScreen(conn, 15); // West Wind Sacramento screen 3
        Screen sc16 = ScreenDB.getScreen(conn, 16); // West Wind Sacramento screen 4
        Screen sc17 = ScreenDB.getScreen(conn, 17); // West Wind Sacramento screen 5
        Screen sc18 = ScreenDB.getScreen(conn, 18); // West Wind Sacramento screen 6

        Screen sc19 = ScreenDB.getScreen(conn, 19); // Ford Wyoming screen 1
        Screen sc20 = ScreenDB.getScreen(conn, 20); // Ford Wyoming screen 2
        Screen sc21 = ScreenDB.getScreen(conn, 21); // Ford Wyoming screen 3
        Screen sc22 = ScreenDB.getScreen(conn, 22); // Ford Wyoming screen 4
        Screen sc23 = ScreenDB.getScreen(conn, 23); // Ford Wyoming screen 5

        Screen sc24 = ScreenDB.getScreen(conn, 24); // Mahoning screen 1

        // Bengie's
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc1, "2026-05-01", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMUM, sc1, "2026-05-01", "23:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHOK, sc1, "2026-05-16", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDEW, sc1, "2026-05-16", "22:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieCAR, sc2, "2026-05-16", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePN,  sc2, "2026-05-16", "21:45"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHML, sc2, "2026-05-16", "23:30"));

        // Skyline Barstow
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc3, "2026-05-01", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMUM, sc3, "2026-05-01", "23:00"));

        // Skyline Greensburg
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMIC, sc4, "2026-05-01", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieLOR, sc4, "2026-05-01", "22:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMIC, sc5, "2026-05-01", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieLOR, sc5, "2026-05-01", "23:00"));

        // Aut-O-Rama Twin
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc6, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDWP, sc7, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHOK, sc6, "2026-05-16", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMOM, sc7, "2026-05-16", "21:00"));

        // Becky's Drive In
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc8, "2026-05-09", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMIC, sc9, "2026-05-09", "20:30"));

        // West Wind San Jose
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc10, "2026-05-09", "20:45"));

        // West Wind Concord
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDWP, sc11, "2026-05-09", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc12, "2026-05-09", "20:30"));

        // West Wind Sacramento
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc13, "2026-05-09", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDWP, sc14, "2026-05-09", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc15, "2026-05-09", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMIC, sc16, "2026-05-09", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc17, "2026-05-09", "22:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDWP, sc18, "2026-05-09", "22:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHOK, sc13, "2026-05-16", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDEW, sc14, "2026-05-16", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMOM, sc15, "2026-05-16", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieCAR, sc16, "2026-05-16", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePN,  sc17, "2026-05-16", "20:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHML, sc18, "2026-05-16", "20:00"));

        // Ford Wyoming 
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc19, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDWP, sc20, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc21, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMIC, sc22, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMK2, sc23, "2026-05-09", "23:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHOK, sc19, "2026-05-16", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieDEW, sc20, "2026-05-16", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMOM, sc21, "2026-05-16", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieCAR, sc22, "2026-05-16", "20:30"));
        // Screen 23: Prom Night -> Hello Mary Lou double feature
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePN,  sc23, "2026-05-16", "20:30"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieHML, sc23, "2026-05-16", "22:15"));

        // Mahoning Drive In
        ShowtimeDB.addShowtime(conn, new Showtime(0, moviePHM, sc24, "2026-05-09", "21:00"));
        ShowtimeDB.addShowtime(conn, new Showtime(0, movieMOM, sc24, "2026-05-16", "21:00"));
        
    }

}