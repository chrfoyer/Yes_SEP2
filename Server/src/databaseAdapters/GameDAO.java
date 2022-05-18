package databaseAdapters;

import Model.Game;

import java.sql.SQLException;

public interface GameDAO
{

  Game create(String name, String producer, String console, String esrb) throws SQLException;

  Game create(Game game) throws SQLException;

  Game readById(int id) throws SQLException;

  Game readMaxId() throws SQLException;

  void update(Game game) throws SQLException;

  void delete(Game game) throws SQLException;
}
