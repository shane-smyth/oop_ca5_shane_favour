package MoviesDaoGui.DAOs;

import MoviesDaoGui.Exceptions.DaoException;
import MoviesDaoGui.DTOs.Movie;

import java.util.List;

public interface MovieDaoInterface {
    public List<Movie> getMovies() throws DaoException;

    public Movie getMovieById(int id) throws DaoException;
}
