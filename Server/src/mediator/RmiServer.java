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
 */
public class RmiServer implements RemoteModel
{

  private Model model;
  private GameList gameList;

  /**
   * Creates new GameList and RmiServer object, Model interface and starts the Server
   *
   * @throws RemoteException when there is an issue with the connection with the client
   * @throws MalformedURLException when stub is unsuccessfully created
   */
  public RmiServer() throws RemoteException, MalformedURLException{
    gameList = new GameList(); // to be deleted
    model = new ModelManager(new GameList());
    startServer();
  }

  /**
   * Creates a new Registry on port 1099
   *
   * @throws RemoteException when there is an issue with the connection with the client
   */
  private void startRegistry() throws RemoteException
  {
    try{
      Registry reg = LocateRegistry.createRegistry(1099);
      System.out.println("Registry started....");
    }
    catch (java.rmi.server.ExportException e)
    {
      System.out.println("Registry already started? " + e.getMessage());
    }
  }

  /**
   * Use this to start the server
   * Note: This method also calls startRegistry()
   *
   * @throws RemoteException when there is an issue with the connection with the client
   * @throws MalformedURLException when stub is unsuccessfully created
   */
  private void startServer() throws RemoteException, MalformedURLException {
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
  @Override public void rentGame(Game game)
  {
    if (game == null)
      throw new IllegalArgumentException("Game to rent cant be null");
    model.rentGame(game);
  }

  /**
   * Rents a game using its name
   *
   * @param name name of the game to be rented
   * @throws RemoteException when there is an issue with the connection with the client
   */
  @Override
  public void rentGame(String name) throws RemoteException {
    model.rentGame(name);
  }

  /**
   * Returns all the games in the system
   * @return a GameList object
   */
  @Override public GameList viewGames()
  {
    return model.getGameList();
  }

  /**
   * Adds a game to the GameList
   *
   * @param game game to be added
   */
  public void addGame(Game game) {
    model.addGame(game);
  }

  /**
   * Decreases the days left in the rental period. If the game is not rented, an exception is thrown.
   */
  public void decrementDay() {
    model.decrementDay();
  }

  /**
   * Gets a game using its name
   *
   * @param name name of the game
   * @return a Game object
   */
  public Game getGame(String name) {
    return model.getGame(name);
  }

  /**
   * Removes a game from the GameList using its name
   *
   * @param name name of the game to be removed
   */
  public void removeGame(String name) {
    model.removeGame(name);
  }

  /**
   * Removes a game
   *
   * @param game game to be removed
   */
  public void removeGame(Game game) {
    model.removeGame(game);
  }
}
