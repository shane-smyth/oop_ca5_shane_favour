package MoviesDaoGui.DAOs;

import MoviesDaoGui.DTOs.Director;
import MoviesDaoGui.Exceptions.DaoException;

public interface DirectorDaoInterface {
    public String getDirectorNameById(int id) throws DaoException;
    public Director addDirector(Director director) throws DaoException;
}
