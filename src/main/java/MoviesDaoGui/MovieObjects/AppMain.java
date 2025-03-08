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

        List<Movie> movies = IMovieDao.getMovies();
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }

//        System.out.println("Enter a Movie ID: ");
//        int userMovieId = keyboard.nextInt();
//        Movie movieById = IMovieDao.getMovieById(userMovieId);
//        System.out.println(movieById.toString());

        System.out.println("Enter a Movie ID: ");
        int userMovieId = keyboard.nextInt();
        IMovieDao.deleteMovieById(userMovieId);
        List<Movie> m = IMovieDao.getMovies();
        for (Movie movie : m) {
            System.out.println(movie.toString());
        }
    }
}
