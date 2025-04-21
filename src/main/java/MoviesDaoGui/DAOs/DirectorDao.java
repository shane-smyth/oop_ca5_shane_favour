package MoviesDaoGui.DAOs;

import MoviesDaoGui.DTOs.Director;
import MoviesDaoGui.Exceptions.DaoException;

import java.sql.*;

public class DirectorDao extends MySqlDao implements DirectorDaoInterface {
    @Override
    public String getDirectorNameById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String directorName = null;

        try {
            connection = this.getConnection();

            String query = "SELECT name FROM directors WHERE director_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                directorName = resultSet.getString("name");
            }
        }
        catch (SQLException e) {
            throw new DaoException("getDirectorNameById("+id+") " + e.getMessage());
        }
        finally {
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
                throw new DaoException("getDirectorNameById("+id+") " + e.getMessage());
            }
        }
        return (directorName != null) ? directorName : "Unknown Director";
    }

    @Override
    public Director addDirector(Director director) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = this.getConnection();
            String query = "INSERT INTO directors (name, country) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, director.getName());
            preparedStatement.setString(2, director.getCountry());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating director failed, no rows affected.");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                director.setDirector_id(generatedKeys.getInt(1));
                return director;
            } else {
                throw new DaoException("Creating director failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DaoException("addDirector() " + e.getMessage());
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) freeConnection(connection);
            } catch (SQLException e) {
                throw new DaoException("addDirector() " + e.getMessage());
            }
        }
    }
}
