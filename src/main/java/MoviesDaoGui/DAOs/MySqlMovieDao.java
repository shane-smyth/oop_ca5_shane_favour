package MoviesDaoGui.DAOs;

import MoviesDaoGui.DTOs.Movie;
import MoviesDaoGui.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface {
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
                throw new DaoException("getAllTasksWithTag() " + e.getMessage());
            }
        }
        return movie;
    }
}
