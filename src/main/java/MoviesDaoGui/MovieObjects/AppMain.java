package MoviesDaoGui.MovieObjects;



import MoviesDaoGui.DAOs.MovieDaoInterface;
import MoviesDaoGui.DAOs.MySqlMovieDao;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;

import java.util.List;

public class AppMain {
    public static void main(String[] args) throws DaoException {
        MovieDaoInterface IMovieDao = new MySqlMovieDao();

        List<Movie> movies = IMovieDao.getMovies();
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
    }
}
