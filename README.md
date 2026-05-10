# Drive-In Theater Management System

## Project Description
The Drive-In Theater Management System is a Java console application for managing drive-in theater operations across multiple venue locations. The system supports two access levels: **venue users**, who log in with their Venue ID to manage their own theaters and showtimes, and **administrators**, who log in with a special code (999) to manage all records across every venue in the system.

The application provides full CRUD functionality for venues, theaters, screens, movies, and showtimes, all backed by a persistent SQLite database that is automatically created and seeded with sample data on first run.

## Project Tasks
- **Task 1: Design the class hierarchy**
  - Define the abstract `Venue` base class with shared properties
  - Extend `Venue` with a concrete `Theater` class
  - Create model classes for `Screen`, `Movie`, and `Showtime`
  - Define the `Displayable` interface and implement it across all model classes

- **Task 2: Set up the database layer**
  - Implement `SQLiteDatabase` to handle connection and table initialization
  - Create all five tables in dependency order: Venues, Theaters, Screens, Movies, Showtimes
  - Enforce foreign key relationships between related tables

- **Task 3: Implement CRUD operations**
  - Build `VenueDB`, `TheaterDB`, `ScreenDB`, `MovieDB`, and `ShowtimeDB` database classes
  - Write parameterized SQL queries for Create, Read, Update, and Delete operations
  - Scope all read queries to the logged-in venue or return all records for admin

- **Task 4: Build the application menu system**
  - Implement a login screen supporting both venue and admin access
  - Build the main menu and four sub-menus (Theaters, Movies, Showtimes, Venues)
  - Display context-sensitive options based on the user's access level

- **Task 5: Implement cascading deletes**
  - Delete all showtimes for a screen before removing the screen
  - Delete all screens and their showtimes before removing a theater
  - Delete all showtimes for a movie before removing the movie

- **Task 6: Populate sample data**
  - Create `SampleData` to seed the database on first launch
  - Include 7 real-world drive-in venue chains, 10 theater locations, 24 screens, 12 movies, and over 50 showtimes

- **Task 7: Add input validation and error handling**
  - Handle invalid integer input gracefully using a shared `readInt()` helper
  - Validate theater and venue ownership before allowing edits or deletes
  - Confirm destructive actions (deletes) with user prompts

- **Task 8: Implement access control**
  - Restrict venue users to records belonging to their own Venue ID
  - Grant administrators unrestricted access to all records across all venues
  - Conditionally show the Manage Venues menu only to admin users

## Project Skills Learned
- Object-oriented design with abstract classes, inheritance, and interfaces
- JDBC and SQLite database integration in Java
- Parameterized SQL queries and prepared statements
- Multi-table relational database design with foreign keys
- Role-based access control in a console application
- Cascading delete logic managed at the application layer
- Console UI design with color-coded output using ANSI escape codes
- Defensive input handling and user input validation

## Language and Tools Used
- **Java**: Core application language for all classes and logic
- **SQLite**: Embedded relational database for persistent data storage (via `sqlite-jdbc` driver)
- **JDBC**: Java API used for all database connectivity and SQL execution

## Database Schema
The application uses five tables in the following dependency order:

| Table | Primary Key | Foreign Key(s) | Description |
|---|---|---|---|
| `Venues` | `Venue_ID` | — | Theater chains / venue names |
| `Theaters` | `Theater_ID` | `Venue_ID` | Physical theater locations |
| `Screens` | `Screen_ID` | `Theater_ID` | Individual screens within a theater |
| `Movies` | `Movie_ID` | — | Movie catalog |
| `Showtimes` | `Showtime_ID` | `Movie_ID`, `Screen_ID` | Scheduled showings linking movies to screens |

## Class Structure

| Class / Interface | Type | Description |
|---|---|---|
| `Venue` | Abstract Class | Base class with `venueID` and `name`; defines abstract `getDetails()` |
| `Theater` | Concrete Class | Extends `Venue`; adds location fields and implements `Displayable` |
| `Screen` | Concrete Class | Represents a screen within a theater; implements `Displayable` |
| `Movie` | Concrete Class | Movie catalog entry; implements `Displayable` |
| `Showtime` | Concrete Class | Links a `Movie` to a `Screen` on a date and time; implements `Displayable` |
| `Displayable` | Interface | Defines the `display()` contract for formatted string output |
| `VenueDB` | DB Class | CRUD operations for the Venues table |
| `TheaterDB` | DB Class | CRUD operations for the Theaters table |
| `ScreenDB` | DB Class | CRUD operations for the Screens table |
| `MovieDB` | DB Class | CRUD operations for the Movies table |
| `ShowtimeDB` | DB Class | CRUD operations for the Showtimes table |
| `SQLiteDatabase` | Utility Class | Handles database connection and table initialization |
| `SampleData` | Utility Class | Seeds the database with sample venues, theaters, and showtimes |
| `App` | Main Class | Entry point; handles login flow and all console menus |

## Development Process Used
- **Iterative Development**: Features were built and tested incrementally — starting with the data model and database layer, then building the menu system on top.

## How to Run

### Prerequisites
- Java 11 or higher
- SQLite JDBC driver (`sqlite-jdbc-*.jar`) on the classpath

### Compile and Run
```bash
# Compile all source files
javac -cp sqlite-jdbc.jar *.java

# Run the application
java -cp .:sqlite-jdbc.jar App
```

> On Windows, replace `:` with `;` in the classpath: `-cp .;sqlite-jdbc.jar`

### Login
- **Venue User**: Enter your Venue ID at the login prompt to manage only your venue's theaters and showtimes.
- **Admin**: Enter `999` to access all records across all venues, including the Manage Venues menu.

The SQLite database file (`DriveInSystem.db`) is created automatically in the working directory on first launch and is pre-populated with sample data.

## Notes
- The database is seeded with sample data only when empty. To reset to sample data, delete `DriveInSystem.db` and relaunch.
- Deleting a theater will also remove all of its screens and their associated showtimes.
- Deleting a movie will also remove all of its scheduled showtimes.
- The movie list visible to venue users is scoped to movies currently scheduled at their theaters.

## License
This project is licensed under the MIT License.
