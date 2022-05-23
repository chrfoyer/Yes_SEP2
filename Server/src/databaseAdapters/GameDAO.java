package databaseAdapters;

import Model.Game;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * An interface to assist with accessing the database for the game object.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public interface GameDAO
{

    Game create(Game game) throws SQLException;

    Game readMaxId() throws SQLException;

    ArrayList<Game> getAllGames() throws SQLException;

    ArrayList<Game> getRentedGamesByUser(User user) throws SQLException;

    void updateRentalInfo(Game game) throws SQLException;

    void rent(Game game, User user) throws SQLException;

    void returnGame(Game game) throws SQLException;

    void update(Game game) throws SQLException;

    void delete(Game game) throws SQLException;
}
