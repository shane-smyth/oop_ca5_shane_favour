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
        int expected = movieId;
        Movie actual = movieDao.getMovieById(movieId);
        assertEquals(expected, actual.getId());
    }

    @Test
    void getMovieById_invalidId() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieId = -1;
        Movie actual = movieDao.getMovieById(movieId);
        assertNull(actual); // expecting false/null
    }

    //step 5 says :Test your features by creating a number of meaningful JUnit tests. (These tests
    //form part of your submission)

//    @Test
//    void deleteMovieById() throws DaoException {
//        MovieDao movieDao = new MovieDao();
//        int movieIdToDelete = 16; // have to change while testing cause if i rerun test it fails as it was deleted already , but i didn't want to end up with empty database so i commented but it works !
//        movieDao.deleteMovieById(movieIdToDelete);
//        Movie actual = movieDao.getMovieById(movieIdToDelete);
//        assertNull(actual);
//    }

    //    @Test
//    void addMovie() throws DaoException {
//        MovieDao movieDao = new MovieDao();
//        Movie newMovie = new Movie("TEST", 2003, "Drama", 8.5, 90, 1);
//        String expected = "TEST";
//        Movie actual = movieDao.addMovie(newMovie);
//        assertEquals(expected, actual.getTitle());
//    }
    @Test
    void addMovie_invalidYear() {
        MovieDao movieDao = new MovieDao();
        Movie newMovie = new Movie("TEST", -1, "Drama", 8.5, 90, 1); // Invalid release year
        Movie actual = null;
        try {
            actual = movieDao.addMovie(newMovie);
        } catch (DaoException e) {

        }
        assertNull(actual); // expecting null because the movie should not be added
    }


    @Test
    void updateTitle() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieIdToUpdate = 3;
        String newTitle = "updatedTitle";
        movieDao.updateTitle(movieIdToUpdate, newTitle);
        String expected = newTitle;
        Movie actual = movieDao.getMovieById(movieIdToUpdate);
        assertEquals(expected, actual.getTitle());
    }

    @Test
    void updateTitle_invalidId() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieIdToUpdate = -1;
        String newTitle = "updatedTitle";
        movieDao.updateTitle(movieIdToUpdate, newTitle);
        Movie actual = movieDao.getMovieById(movieIdToUpdate);
        assertNull(actual);
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
    void filterByTitle_noMatch() throws DaoException {
        MovieDao movieDao = new MovieDao();
        String filter = "notRealMovie";
        List<Movie> filteredMovies = movieDao.filterByTitle(filter);
        assertNotNull(filteredMovies);
        assertEquals(0, filteredMovies.size()); // expecting no movies
    }

    @Test
    void moviesListToJsonString() throws DaoException {
        MovieDao movieDao = new MovieDao();
        List<Movie> movies = movieDao.getMovies();
        char expectedStart = '[';
        char expectedEnd = ']';
        String actual = JsonConverter.moviesListToJsonString(movies);
        assertEquals(expectedStart, actual.charAt(0));
        assertEquals(expectedEnd, actual.charAt(actual.length() - 1));
    }

    @Test
    void movieToJsonObject() throws DaoException {
        MovieDao movieDao = new MovieDao();
        int movieId = 1;
        Movie movie = movieDao.getMovieById(movieId);
        char expectedStart = '{';
        char expectedEnd = '}';
        String actual = JsonConverter.movieToJsonObject(movie);
        assertEquals(expectedStart, actual.charAt(0));
        assertEquals(expectedEnd, actual.charAt(actual.length() - 1));
    }
}