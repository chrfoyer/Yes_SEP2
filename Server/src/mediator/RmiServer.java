package mediator;

import Model.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Connected to by the RmiClient class to establish the Client - Server relationship
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.5 5/23/22
 */
public class RmiServer implements RemoteModel
{

    private final Model model;

    /**
     * Creates new GameList and RmiServer object, Model interface and starts the Server
     *
     * @throws RemoteException       when there is an issue with the connection with the client
     * @throws MalformedURLException when stub is unsuccessfully created
     */
    public RmiServer() throws RemoteException, MalformedURLException, SQLException
    {
        model = new ModelManager();
        startServer();
    }

    /**
     * Creates a new Registry on port 1099
     *
     * @throws RemoteException when there is an issue with the connection with the client
     */
    private void startRegistry() throws RemoteException
    {
        try
        {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started....");
        } catch (java.rmi.server.ExportException e)
        {
            System.out.println("Registry already started? " + e.getMessage());
        }
    }

    /**
     * Use this to start the server Note: This method also calls startRegistry()
     *
     * @throws RemoteException       when there is an issue with the connection with the client
     * @throws MalformedURLException when stub is unsuccessfully created
     */
    private void startServer() throws RemoteException, MalformedURLException
    {
        startRegistry();
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("Games", this);
        System.out.println("server started...");
    }

    /**
     * Rents a game
     *
     * @param game game to be rented
     * @throws SQLException Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void rentGame(Game game, User user) throws SQLException
    {
        if (game == null)
            throw new IllegalArgumentException("Game to rent cant be null");
        model.rentGame(game, user);
    }


    /**
     * Returns all the games in the system
     *
     * @return a GameList object
     */
    @Override
    public GameList viewGames()
    {
        return model.getGameList();
    }

    /**
     * Returns an array list containing games matching the parameters of the search.
     *
     * @param name    Part of the name of the games returned
     * @param console The selected console
     * @param esrb    The selected age restriction rating
     * @return The arraylist of games matching the parameters
     * @throws RemoteException Thrown when issues relating to communication across the server and client
     */
    @Override
    public ArrayList<Game> searchGames(String name, String console, String esrb) throws RemoteException
    {
        return model.searchGames(name, console, esrb);
    }

    /**
     * A method to get all the active rentals by a user.
     *
     * @param user The user to search the rentals of
     * @return An array list with all the games rented by the user
     * @throws SQLException Thrown when the connection to the database fails or the query is malformed
     * @implNote Should be the currently logged-in user
     */
    @Override
    public ArrayList<Game> getGamesRentedByUser(User user) throws SQLException
    {
        return model.getGamesRentedByUser(user);
    }

    /**
     * Gets the most recent game to be added to the database, and therefore with the highest serial id.
     *
     * @return The most recent game
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     * @throws RemoteException Thrown when issues occur with the remote model
     */
    @Override
    public Game getMostRecentGame() throws SQLException, RemoteException
    {
        return model.getMostRecentGame();
    }

    /**
     * Returns a boolean representing when a game containing the searched name exists
     *
     * @param name The name of the game to search
     * @return Whether the game is present
     * @throws RemoteException Thrown when issues occur with the remote model
     */
    @Override
    public boolean containsGame(String name) throws RemoteException
    {
        return model.containsGame(name);
    }

    /**
     * Signs the user up by adding them to the user list and the database.
     *
     * @param user The user to sign up
     * @throws RemoteException Thrown when issues occur with the remote model
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void signup(User user) throws RemoteException, SQLException
    {
        model.signup(user);
    }

    /**
     * Logs the user in and returns a boolean representing whether the log-in was successful
     *
     * @param user The user to log-in
     * @return A boolean representing whether the log-in was successful
     * @throws RemoteException Thrown when issues occur with the remote model
     */
    @Override
    public boolean login(User user) throws RemoteException
    {
        return model.login(user);
    }

    /**
     * A method to get the user list object containing all the users in the database
     *
     * @return A user list object of all the users in the system
     * @throws RemoteException Thrown when issues occur with the remote model
     */
    @Override
    public UserList getUserList() throws RemoteException
    {
        return model.getUserList();
    }

    /**
     * Update the information about a game within the database.
     *
     * @param gameOld The old version of the game before the update
     * @param gameNew The new version of the game after the update
     * @throws SQLException Thrown when the connection to the database fails or the query is malformed
     * @implNote Should only be called by the admin
     */
    @Override
    public void updateGameInfo(Game gameOld, Game gameNew) throws SQLException
    {
        model.updateGameInfo(gameOld, gameNew);
    }

    /**
     * Adds a game to the game list and database.
     *
     * @param game The game to add
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     * @throws RemoteException Thrown when an issue occurs with the remote model
     */
    @Override
    public void addGame(Game game) throws SQLException, RemoteException
    {
        model.addGame(game);
    }

    /**
     * Decreases the days left in the rental period across all games
     */
    public void decrementDay()
    {
        model.decrementDay();
    }

    /**
     * Gets a game using its name
     *
     * @param name name of the game
     * @return a Game object
     */
    public Game getGame(String name)
    {
        return model.getGame(name);
    }

    /**
     * Removes a game
     *
     * @param game game to be removed
     * @throws SQLException Thrown when the connection to the database fails or the query is malformed
     */
    public void removeGame(Game game) throws SQLException
    {
        model.removeGame(game);
    }

    /**
     * Removes a user
     *
     * @param user The user to be removed
     * @throws RemoteException Thrown when an issue with the remote model occurs
     */
    @Override
    public void removeUser(User user) throws RemoteException
    {
        model.removeUser(user);
    }

    /**
     * @param oldUser The old version of the user
     * @param newUser The new version of the user
     * @throws RemoteException Thrown when an issue with the remote model occurs
     * @deprecated Updates the user info in the user list
     */
    @Override
    public void updateUserInfo(User oldUser, User newUser)
            throws RemoteException
    {
        model.updateUserInfo(oldUser, newUser);
    }

    /**
     * Update the user info both in the user list and the database.
     *
     * @param user The user to be updated
     * @throws RemoteException Thrown when an issue with the remote model occurs
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void updateUserWithSQL(User user) throws RemoteException, SQLException
    {
        model.updateUserWithSQL(user);
    }

    /**
     * Add a transaction to the database
     *
     * @param transaction The transaction to add
     * @throws RemoteException Thrown when an issue with the remote model occurs
     */
    @Override
    public void addTransaction(Transaction transaction)
            throws RemoteException
    {
        model.addTransaction(transaction);
    }

    /**
     * Modifies the balance of a user
     *
     * @param amount The amount to add if positive or remove if negative
     * @param user   The user to modify the balance of
     * @throws RemoteException Thrown when an issue with the remote model occurs
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void modifyBalance(int amount, User user)
            throws RemoteException, SQLException
    {
        model.modifyBalance(amount, user);
    }

    /**
     * Pays for the subscription for a user so that they can rent games.
     *
     * @param user The user to activate the subscription for
     * @throws RemoteException Thrown when an issue with the remote model occurs
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void payForSubscription(User user) throws RemoteException, SQLException
    {
        model.payForSubscription(user);
    }

    /**
     * Sets the the subscriptions status of a user.
     *
     * @param user   The user to alter
     * @param status The status to set the subscription to
     */
    @Override
    public void setSubscription(User user, boolean status)
    {
        model.setSubscriptionStatus(user, status);
    }

    /**
     * Gets the transaction list object containing all the transactions
     *
     * @return Transaction list object with all transactions
     * @throws RemoteException Thrown when an issue occurs with the remote model
     */
    @Override
    public TransactionList getTransactionList() throws RemoteException
    {
        return model.getTransactionList();
    }


    /**
     * Leave a review between 1 and 5 rating the game the user has rented. The average review score can then be seen by
     * users browsing the available games.
     *
     * @param review The integer between 1 and 5 rating the experience. 1 is the worst and 5 is the best.
     * @param game   The game that the review relates to
     * @throws RemoteException Thrown when an issue occurs with the remote model
     */
    @Override
    public void leaveReview(int review, Game game) throws RemoteException
    {
        model.leaveReview(review, game);
    }

    /**
     * Gets the cumulative average of the review scores for a game.
     *
     * @param game The game for which the reviews relate to
     * @return A float with the average review between 1.0 and 5.0
     * @throws RemoteException Thrown when an issue occurs with the remote model
     */
    @Override
    public float getReview(Game game) throws RemoteException
    {
        return model.getReview(game);
    }

    /**
     * Allows the user to return a game. This will make it available for other users to rent.
     *
     * @param game The game to return
     * @param user The user returning the game
     * @throws RemoteException Thrown when an issue occurs with the remote model
     * @throws SQLException    Thrown when the connection to the database fails or the query is malformed
     */
    @Override
    public void returnGame(Game game, User user)
            throws RemoteException, SQLException
    {
        model.returnGame(game, user);
    }

    /**
     * Gets the balance on the user account.
     *
     * @param user The user to check the balance of
     * @return An in representing the balance of available funds for the user.
     * @throws RemoteException Thrown when an issue occurs with the remote model
     */
    @Override
    public int getBalance(User user) throws RemoteException
    {
        return model.getBalance(user);
    }

}
