package mediator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Model.*;

/**
 * Connected to by the RmiClient class to establish the Client - Server relationship
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public class RmiServer implements RemoteModel
{

  private Model model;
  private GameList gameList;

  /**
   * Creates new GameList and RmiServer object, Model interface and starts the Server
   *
   * @throws RemoteException       when there is an issue with the connection with the client
   * @throws MalformedURLException when stub is unsuccessfully created
   */
  public RmiServer() throws RemoteException, MalformedURLException
  {
    gameList = new GameList(); // to be deleted
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
    }
    catch (java.rmi.server.ExportException e)
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
   */
  @Override public void rentGame(Game game, User user)
  {
    if (game == null)
      throw new IllegalArgumentException("Game to rent cant be null");
    model.rentGame(game,user);
  }


  /**
   * Returns all the games in the system
   *
   * @return a GameList object
   */
  @Override public GameList viewGames()
  {
    return model.getGameList();
  }

  @Override public boolean containsGame(String name) throws RemoteException
  {
    return model.containsGame(name);
  }

  @Override public void signup(User user) throws RemoteException
  {
    model.signup(user);
  }

  @Override public boolean login(User user) throws RemoteException
  {
    return model.login(user);
  }

  @Override public UserList getUserList() throws RemoteException
  {
    return model.getUserList();
  }

  @Override public void updateGameInfo(Game gameOld, Game gameNew)
  {
    model.updateGameInfo(gameOld, gameNew);
  }

  /**
   * Adds a game to the GameList
   *
   * @param game game to be added
   */
  public void addGame(Game game)
  {
    model.addGame(game);
  }

  /**
   * Decreases the days left in the rental period. If the game is not rented, an exception is thrown.
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
   */
  public void removeGame(Game game)
  {
    model.removeGame(game);
  }

  @Override public void removeUser(User user) throws RemoteException
  {
    model.removeUser(user);
  }

  @Override public void updateUserInfo(User oldUser, User newUser)
      throws RemoteException
  {
    model.updateUserInfo(oldUser, newUser);
  }

  @Override public void addTransaction(Transaction transaction)
      throws RemoteException
  {
    model.addTransaction(transaction);
  }

  @Override public void modifyBalance(int amount, User user)
      throws RemoteException
  {
    model.modifyBalance(amount, user);
  }

  @Override public void payForSubscription(User user) throws RemoteException
  {
    model.payForSubscription(user);
  }

  @Override public void setSubscription(User user, boolean status)
  {
    model.setSubscriptionStatus(user, status);
  }

  @Override public TransactionList getTransactionList() throws RemoteException
  {
    return model.getTransactionList();
  }

  @Override public void leaveReview(int review, Game game) throws RemoteException
  {
    model.leaveReview(review,game);
  }

  @Override public float getReview(Game game) throws RemoteException
  {
    return model.getReview(game);
  }

  @Override public void returnGame(Game game, User user)
      throws RemoteException
  {
    model.returnGame(game,user);
  }

  @Override public int getBalance(User user) throws RemoteException
  {
    return model.getBalance(user);
  }

}
