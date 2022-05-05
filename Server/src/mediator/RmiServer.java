package mediator;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Model.*;

public class RmiServer implements RemoteModel
{

  private Model model;
  private GameList gameList;

  public RmiServer() throws RemoteException, MalformedURLException{
    gameList = new GameList();
    model = new ModelManager(gameList);
    startServer();
  }

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
   * @throws RemoteException
   * @throws MalformedURLException
   */
  private void startServer() throws RemoteException, MalformedURLException {
    startRegistry();
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Games", this);
    System.out.println("server started...");
  }

  @Override public void rentGame(Game game)
  {
    gameList.removeGame(game);
    if (game == null)
      throw new IllegalArgumentException("Game to rent cant be null");
    game.rentGame();
    gameList.addGame(game);
  }

  @Override public GameList viewGames()
  {
    return model.getGameList();
  }

  public void addGame(Game game) {
    model.addGame(game);
  }

  public void decrementDay() {
    model.decrementDay();
  }

  public Game getGame(String name) {
    return model.getGame(name);
  }

  public void removeGame(String name) {
    model.removeGame(name);
  }

  public void removeGame(Game game) {
    model.removeGame(game);
  }
}
