package MoviesDaoGui.DAOs;

import MoviesDaoGui.DTOs.Director;
import MoviesDaoGui.Exceptions.DaoException;

import java.util.List;

public interface DirectorDaoInterface {
    public String getDirectorNameById(int id) throws DaoException;
    public Director addDirector(Director director) throws DaoException;
    public List<Director> getAllDirectors() throws DaoException;
}
