package MoviesDaoGui.MovieObjects;

import MoviesDaoGui.DAOs.MovieDaoInterface;
import MoviesDaoGui.DAOs.MovieDao;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;
import MoviesDaoGui.Converters.JsonConverter;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        System.out.println("~ CA5 Movie Database App ~");

        AppMain app = new AppMain();
        app.start();
    }

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "0. Exit",
                "1. Display All Movies",
                "2. Find Movie by ID",
                "3. Delete Movie by ID",
                "4. Add New Movie",
                "5. Update Movie Title by ID",
                "6. Filter Movies by Title",
                "7. Convert Moviea List to JSON",
                "8. Convert Movie to JSON by ID",
                ""
        };

        MovieDaoInterface IMovieDao = new MovieDao();

        int menuChoice = -1;
        do {
            displayMenu(menuOptions, "\n--------- MENU ---------");
            try {
                menuChoice = getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:
                        System.out.println("- DISPLAY ALL MOVIES -");

                        List<Movie> movies = IMovieDao.getMovies();
                        if (movies.isEmpty()) {
                            System.out.println("No movies found !");
                        }
                        else {
                            for (Movie movie : movies) {
                                System.out.println("Movie :: " + movie.toString());
                            }
                        }
                        System.out.println("- COMPLETED DISPLAYING ALL MOVIES -");
                        break;

                    case 2:
                        System.out.println("- FIND MOVIE BY ID -");

                        System.out.print("Enter a Movie ID : ");
                        int movieId = keyboard.nextInt();
                        Movie movie = IMovieDao.getMovieById(movieId);
                        if (movie != null) {
                            System.out.println("Movie Found :: " + movie.toString());
                        } else {
                            System.out.println("Movie not found !");
                        }
                        System.out.println("- COMPLETED FINDING MOVIE BY ID -");
                        break;

                    case 3:
                        System.out.println("- DELETE MOVIE BY ID -");

                        System.out.print("Enter a Movie ID : ");
                        int deleteMovieId = keyboard.nextInt();
                        try {
                            Movie movieToDelete = IMovieDao.getMovieById(deleteMovieId);
                            if (movieToDelete != null) {
                                IMovieDao.deleteMovieById(deleteMovieId);
                                System.out.println("Movie Deleted :: " + movieToDelete.toString());
                            }
                            else {
                                System.out.println("No movie found !");
                            }
                        } catch (DaoException e) {
                            System.out.println(e.getMessage());
                            System.out.println("FAILED.");
                        }
                        System.out.println("- COMPLETED DELETING MOVIE BY ID -");
                        break;

                    case 4:
                        System.out.println("- ADD NEW MOVIE -");
                        keyboard.nextLine();

                        System.out.println("Enter Title : ");
                        String title = keyboard.nextLine();
                        System.out.println("Enter Year : ");
                        int year = keyboard.nextInt();
                        System.out.println("Enter Rating : ");
                        double rating = keyboard.nextDouble();
                        keyboard.nextLine();
                        System.out.println("Enter Genre : ");
                        String genre = keyboard.nextLine();
                        System.out.println("Enter Duration : ");
                        int duration = keyboard.nextInt();
                        keyboard.nextLine();
                        System.out.println("Enter Director : ");
                        int director = keyboard.nextInt();
                        keyboard.nextLine();

                        Movie newMovie = new Movie(title, year, genre, rating, duration, director);

                        Movie addedMovie = IMovieDao.addMovie(newMovie);
                        if (addedMovie != null) {
                            System.out.println("Movie Added :: " + addedMovie.toString());
                        }
                        else {
                            System.out.println("Failed to add new movie !");
                        }
                        System.out.println("- COMPLETED ADDING NEW MOVIE -");
                        break;

                    case 5:
                        System.out.println("- UPDATE MOVIE TITLE BY ID -");

                        System.out.print("Enter a movie ID : ");
                        int updateMovieId = keyboard.nextInt();
                        keyboard.nextLine();
                        System.out.print("Enter modified title : ");
                        String userTitle = keyboard.nextLine();
                        try {
                            Movie movieToUpdate = IMovieDao.getMovieById(updateMovieId);
                            if (movieToUpdate != null) {
                                IMovieDao.updateTitle(updateMovieId, userTitle);
                                Movie updatedMovie = IMovieDao.getMovieById(updateMovieId);
                                System.out.println("Movie Title Updated :: " + updatedMovie.toString());
                            }
                            else {
                                System.out.println("No movie found !");
                            }
                        } catch (DaoException e) {
                            System.out.println(e.getMessage());
                            System.out.println("FAILED.");
                        }
                        System.out.println("- COMPLETED UPDATING MOVIE TITLE BY ID -");
                        break;

                    case 6:
                        System.out.println("- FILTER MOVIES BY TITLE -");

                        System.out.println("Enter a String to filter by Title : ");
                        String filter = keyboard.nextLine();
                        List<Movie> filteredMovies = IMovieDao.filterByTitle(filter);
                        if (filteredMovies.isEmpty()) {
                            System.out.println("No movies found !");
                        }
                        else {
                            for (Movie filteredMovie : filteredMovies) {
                                System.out.println("Filtered Movie :: " + filteredMovie.toString());
                            }
                        }
                        System.out.println("- COMPLETED FILTERING MOVIES BY TITLE -");
                        break;

                    case 7:
                        System.out.println("- CONVERT MOVIES LIST TO JSON -");

                        List<Movie> moviesForJson = IMovieDao.getMovies();
                        if (moviesForJson.isEmpty()) {
                            System.out.println("No movies found !");
                        }
                        else {
                            String jsonString = JsonConverter.moviesListToJsonString(moviesForJson);
                            System.out.println("JSON Output :: " + jsonString);
                        }
                        System.out.println("- COMPLETED CONVERTING MOVIES LIST TO JSON -");
                        break;

                    case 8:
                        System.out.println("- CONVERT MOVIE TO JSON -");

                        System.out.print("Enter a Movie ID : ");
                        int movieIdForJson = keyboard.nextInt();
                        try {
                            Movie movieForJson = IMovieDao.getMovieById(movieIdForJson);
                            if (movieForJson != null) {
                                String movieJson = JsonConverter.movieToJsonObject(movieForJson);
                                System.out.println("JSON Output :: " + movieJson);
                            }
                            else {
                                System.out.println("No movie found !");
                            }
                        } catch (DaoException e) {
                            System.out.println(e.getMessage());
                            System.out.println("FAILED.");
                        }
                        System.out.println("- COMPLETED CONVERTING MOVIE TO JSON -");
                        break;

                    case 0:
                        System.out.println("Exiting the application...");
                        break;

                    default:
                        System.out.println("Invalid input - Please enter a valid option.");
                        break;
                }
            }
            catch ( InputMismatchException | DaoException e ) {
                System.out.println( "Invalid input - Please enter a valid option." );
                keyboard.nextLine();
            }
        }
        while (menuChoice != 0);

        System.out.println("\r\nYour program is disconnecting from the database - Goodbye !" );
    }

    public static void displayMenu( String[] menuOptions , String menuTitle ) {
        System.out.println( menuTitle );
        System.out.println( "Choose from one of the following options ::" );
        for ( String option: menuOptions ) {
            System.out.println( option );
        }
    }

    public static int getMenuChoice( int numItems ) {
        Scanner keyboard = new Scanner( System.in );
        int choice = keyboard.nextInt();
        while ( choice < 0 || choice > numItems ) {
            System.out.printf( "Please enter a valid option (1 - %d)\n" , numItems );
            choice = keyboard.nextInt();
        }
        return choice;
    }
}