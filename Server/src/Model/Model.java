package Model;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for implementing methods used in the Model package
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public interface Model
{
    void addGame(Game game) throws SQLException;

    ArrayList<Game> getAllGames();

    void removeGame(Game game) throws SQLException;

    void removeGame(String name);

    void decrementDay();

    void rentGame(Game game, User user) throws SQLException;

    void returnGame(Game game, User user) throws SQLException;

    Game getGame(String name);

    Game getMostRecentGame() throws SQLException;

    Game getGame(Game game);

    ArrayList<Game> getALlAvailableGames();

    void refreshGameList() throws SQLException;

    void refreshUserList() throws SQLException;

    void refreshTransactionList() throws SQLException;

    GameList getGameList();

    ArrayList<Game> searchGames(String name, String console, String esrb);

    boolean containsGame(String name);

    void signup(User user) throws SQLException;

    boolean login(User user);

    UserList getUserList();

    void updateGameInfo(Game gameOld, Game gameNew) throws SQLException;

    void addTransaction(Transaction transaction);

    void removeUser(User user);

    void updateUserInfo(User oldUser, User newUser);

    void updateUserWithSQL(User user) throws SQLException;

    int getBalance(User user);

    void modifyBalance(int amount, User user) throws SQLException;

    void payForSubscription(User user) throws SQLException;

    TransactionList getTransactionList();

    void setSubscriptionStatus(User user, boolean status);

    void leaveReview(int review, Game game);

    float getReview(Game game);

    ArrayList<Game> getGamesRentedByUser(User user) throws SQLException;
}
