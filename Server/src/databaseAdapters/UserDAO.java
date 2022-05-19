package databaseAdapters;

import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO
{
    User create(User user) throws SQLException;

    User readUsername(String username) throws SQLException;

    // User readByUsername() throws SQLException;

    ArrayList<User> getAllUsers() throws SQLException;

    void update(User user) throws SQLException;

    void delete(User user) throws SQLException;
}
