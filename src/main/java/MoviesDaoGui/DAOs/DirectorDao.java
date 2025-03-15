package MoviesDaoGui.DAOs;

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
}
