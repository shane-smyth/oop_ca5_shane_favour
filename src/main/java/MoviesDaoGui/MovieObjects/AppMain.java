package MoviesDaoGui.MovieObjects;



import MoviesDaoGui.DAOs.MovieDaoInterface;
import MoviesDaoGui.DAOs.MySqlMovieDao;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) throws DaoException {
        Scanner keyboard = new Scanner(System.in);
        MovieDaoInterface IMovieDao = new MySqlMovieDao();

//        List<Movie> movies = IMovieDao.getMovies();
//        for (Movie movie : movies) {
//            System.out.println(movie.toString());
//        }

//        System.out.println("Enter a Movie ID: ");
//        int userMovieId = keyboard.nextInt();
//        Movie movieById = IMovieDao.getMovieById(userMovieId);
//        System.out.println(movieById.toString());

//        System.out.println("Enter a Movie ID: ");
//        int userMovieId = keyboard.nextInt();
//        IMovieDao.deleteMovieById(userMovieId);
//        List<Movie> m = IMovieDao.getMovies();
//        for (Movie movie : m) {
//            System.out.println(movie.toString());
//        }

//        System.out.println("Enter Title: ");
//        String title = keyboard.nextLine();
//        System.out.println("Enter Year: ");
//        int year = keyboard.nextInt();
//        System.out.println("Enter Rating: ");
//        double rating = keyboard.nextDouble();
//        keyboard.nextLine();
//        System.out.println("Enter Genre: ");
//        String genre = keyboard.nextLine();
//        System.out.println("Enter Duration: ");
//        int duration = keyboard.nextInt();
//        keyboard.nextLine();
//        System.out.println("Enter Director: ");
//        int director = keyboard.nextInt();
//        keyboard.nextLine();
//        Movie newMovie = new Movie(title, year, genre, rating, duration, director);
//        IMovieDao.addMovie(newMovie);
//        System.out.println("Movie Added\n" + newMovie.toString());

        System.out.println("Enter a movie ID: ");
        int movieId = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println("Enter modified title: ");
        String title = keyboard.nextLine();
        IMovieDao.updateTitle(movieId, title);

//        System.out.println("Enter a String to filter by Title: ");
//        String filter = keyboard.nextLine();
//        List<Movie> movies = IMovieDao.filterByTitle(filter);
//        if (movies.isEmpty()) {
//            System.out.println("No movies found");
//        } else {
//            for (Movie movie : movies) {
//                System.out.println(movie);
//            }
//        }
    }
}
