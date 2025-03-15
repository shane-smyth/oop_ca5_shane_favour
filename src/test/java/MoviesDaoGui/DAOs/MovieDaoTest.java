package MoviesDaoGui.DAOs;

import MoviesDaoGui.Converters.JsonConverter;
import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class MovieDaoTest {

    @Test
    void getMovies() throws DaoException {
        MovieDao movieDao = new MovieDao();
        List<Movie> movies = movieDao.getMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    @Test
    void getMovieById() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieId = 1;
        Movie movie = movieDao.getMovieById(movieId);
        assertNotNull(movie);
        assertEquals(movieId, movie.getId());
    }

    //    @Test
    //    void deleteMovieById() throws DaoException {
    //        MovieDao movieDao = new MovieDao();
    //        int movieIdToDelete = 14; // have to change while testing cause if i rerun test it fails as it was deleted already , but i didn't want to end up with empty database so i commented but it works !
    //        Movie movieDelete = movieDao.getMovieById(movieIdToDelete);
    //        assertNotNull(movieDelete);
    //
    //        movieDao.deleteMovieById(movieIdToDelete);
    //        Movie deletedMovie = movieDao.getMovieById(movieIdToDelete);
    //        assertNull(deletedMovie);
    //    }

    @Test
    void addMovie() throws DaoException {
        MovieDao movieDao = new MovieDao();
        Movie newMovie = new Movie("TEST", 2003, "Drama", 8.5, 90, 1);
        Movie addedMovie = movieDao.addMovie(newMovie);
        assertNotNull(addedMovie);
        assertNotNull(addedMovie.getId());
    }

    @Test
    void updateTitle() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieIdToUpdate = 3;
        String newTitle = "updatedTitle";

        movieDao.updateTitle(movieIdToUpdate, newTitle);
        Movie updatedMovie = movieDao.getMovieById(movieIdToUpdate);
        assertNotNull(updatedMovie);
        assertEquals(newTitle, updatedMovie.getTitle());
    }

    @Test
    void filterByTitle() throws DaoException {
        MovieDao movieDao = new MovieDao();
        String filter = "AR";
        List<Movie> filteredMovies = movieDao.filterByTitle(filter);
        assertNotNull(filteredMovies);

        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found with title containing : " + filter);
        } else {
            for (Movie movie : filteredMovies) {
                assertTrue(movie.getTitle().toLowerCase().contains(filter.toLowerCase()));
            }
        }
    }

    @Test
    void moviesListToJsonString() throws DaoException {
        MovieDao movieDao = new MovieDao();
        List<Movie> movies = movieDao.getMovies();
        String jsonString = JsonConverter.moviesListToJsonString(movies);
        assertNotNull(jsonString);
        assertTrue(jsonString.startsWith("["));
        assertTrue(jsonString.endsWith("]"));
    }

    @Test
    void movieToJsonObject() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieId = 1;
        Movie movie = movieDao.getMovieById(movieId);
        assertNotNull(movie);

        String jsonString = JsonConverter.movieToJsonObject(movie);
        assertNotNull(jsonString);
        assertTrue(jsonString.startsWith("{"));
        assertTrue(jsonString.endsWith("}"));
    }
}