package databaseAdapters;

import Model.Game;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO
{
  User create(User user) throws SQLException;

  User readById(int id) throws SQLException;

  User readMaxId() throws SQLException;

  ArrayList<User> getAllUsers() throws SQLException;

  void update(User user) throws SQLException;

  void delete(User user) throws SQLException;
}
