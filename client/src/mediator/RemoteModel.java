package mediator;

import Model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface used to establish Client - Server connection using RMI
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.3 5/5/22
 */
public interface RemoteModel extends Remote
{
    void rentGame(Game game, User user) throws RemoteException, SQLException;

    Game getGame(String name) throws RemoteException;

    GameList viewGames() throws RemoteException;

    ArrayList<Game> searchGames(String name, String console, String esrb) throws RemoteException;

    ArrayList<Game> getGamesRentedByUser(User user) throws SQLException, RemoteException;

    Game getMostRecentGame() throws SQLException, RemoteException;

    boolean containsGame(String name) throws RemoteException;

    void signup(User user) throws RemoteException, SQLException;

    boolean login(User user) throws RemoteException;

    UserList getUserList() throws RemoteException;

    void updateGameInfo(Game gameOld, Game gameNew) throws RemoteException, SQLException;

    void addGame(Game game) throws RemoteException, SQLException;

    void removeGame(Game game) throws RemoteException, SQLException;

    void removeUser(User user) throws RemoteException;

    void updateUserInfo(User oldUser, User newUser) throws RemoteException;

    void addTransaction(Transaction transaction) throws RemoteException;

    int getBalance(User user) throws RemoteException;

    void modifyBalance(int amount, User user) throws RemoteException;

    void payForSubscription(User user) throws RemoteException;

    void setSubscription(User user, boolean status) throws RemoteException;

    TransactionList getTransactionList() throws RemoteException;

    void leaveReview(int review, Game game) throws RemoteException;

    float getReview(Game game) throws RemoteException;

    void returnGame(Game game, User user) throws RemoteException, SQLException;

}
