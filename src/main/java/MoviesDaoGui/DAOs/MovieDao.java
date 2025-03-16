package MoviesDaoGui.DAOs;

import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDao extends MySqlDao implements MovieDaoInterface {
    @Override
    public List<Movie> getMovies() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Movie> movies = new ArrayList<>();

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM movies";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int movieId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("release_year");
                double rating = resultSet.getDouble("rating");
                String genre = resultSet.getString("genre");
                int duration = resultSet.getInt("duration");
                int director = resultSet.getInt("director_id");
                Movie m = new Movie(movieId, title, year, genre, rating, duration, director);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("getMovies() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return movies;
    }


    @Override
    public Movie getMovieById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Movie movie = null;

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM movies WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int movieId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("release_year");
                double rating = resultSet.getDouble("rating");
                String genre = resultSet.getString("genre");
                int duration = resultSet.getInt("duration");
                int director = resultSet.getInt("director_id");
                movie = new Movie(movieId, title, year, genre, rating, duration, director);
            }
        } catch (SQLException e) {
            throw new DaoException("getMovieById(" + id + ") " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("getMovieById() " + e.getMessage());
            }
        }
        return movie;
    }


    @Override
    public void deleteMovieById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();
            String query = "DELETE FROM movies WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("deleteExpense() " + e.getMessage());
            }
        }
    }


    @Override
    public Movie addMovie(Movie movie) throws DaoException {
        // validating input
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new DaoException("Movie title cannot be empty !");
        }
        if (movie.getRelease_year() <= 0) {
            throw new DaoException("Release year must be a positive number !");
        }
        if (movie.getRating() < 0 || movie.getRating() > 10) {
            throw new DaoException("Rating must be between 0 and 10 !");
        }
        if (movie.getGenre() == null || movie.getGenre().trim().isEmpty()) {
            throw new DaoException("Genre cannot be empty !");
        }
        if (movie.getDuration() <= 0) {
            throw new DaoException("Duration must be a positive number !");
        }
        if (movie.getDirector_id() <= 0) {
            throw new DaoException("Director ID must be a positive number !");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        Movie newMovie = null;

        try {
            connection = this.getConnection();

            String query = "INSERT INTO movies (title, release_year, rating, genre, duration, director_id) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); //https://www.ibm.com/docs/en/db2/11.5?topic=applications-retrieving-auto-generated-keys-insert-statement

            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getRelease_year());
            preparedStatement.setDouble(3, movie.getRating());
            preparedStatement.setString(4, movie.getGenre());
            preparedStatement.setInt(5, movie.getDuration());
            preparedStatement.setInt(6, movie.getDirector_id());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Creating movie failed.");
            }

            // retrieve the auto-generated ID created by the database for the new movie
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int movieId = generatedKeys.getInt(1);
                newMovie = new Movie(movieId, movie.getTitle(), movie.getRelease_year(), movie.getGenre(), movie.getRating(), movie.getDuration(), movie.getDirector_id());
            } else {
                throw new DaoException("Creating movie failed. No id given.");
            }

        } catch (SQLException e) {
            throw new DaoException("addMovie() " + e.getMessage());
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("addMovie() " + e.getMessage());
            }
        }
        return newMovie;
    }


    @Override
    public void updateTitle(int id, String change) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();
            String query = "UPDATE movies SET title = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, change);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("updateTitle(" + id + ") " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e) {
                throw new DaoException("updateTitle(" + id + ")  " + e.getMessage());
            }
        }
    }


    @Override
    public List<Movie> filterByTitle(String filter) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Movie> movies = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM movies WHERE title LIKE ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, '%'+filter+'%');
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int year = resultSet.getInt("release_year");
                double rating = resultSet.getDouble("rating");
                String genre = resultSet.getString("genre");
                int duration = resultSet.getInt("duration");
                int director = resultSet.getInt("director_id");
                Movie m = new Movie(movieId, title, year, genre, rating, duration, director);
                movies.add(m);
            }
        } catch (SQLException e) {
            throw new DaoException("filterByTitle(" + filter + ") " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("getMovieById() " + e.getMessage());
            }
        }
        return movies;
    }
}
