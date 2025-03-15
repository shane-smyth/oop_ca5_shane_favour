package MoviesDaoGui.DAOs;

import MoviesDaoGui.Exceptions.DaoException;

public interface DirectorDaoInterface {
    public String getDirectorNameById(int id) throws DaoException;
}
