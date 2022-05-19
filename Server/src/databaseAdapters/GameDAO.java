package databaseAdapters;

import Model.Game;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GameDAO
{

  Game create(String name, String producer, String console, String esrb) throws SQLException;

  Game create(Game game) throws SQLException;

  Game readById(int id) throws SQLException;

  Game readMaxId() throws SQLException;

  ArrayList<Game> getAllGames() throws SQLException;

  void rent(Game game, User user) throws SQLException;

  void update(Game game) throws SQLException;

  void delete(Game game) throws SQLException;
}
