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
    model = new ModelManager();
    gameList = new GameList();
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

  private void startServer() throws RemoteException, MalformedURLException {
    startRegistry();
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Case", this);
    System.out.println("server started...");
  }



  @Override public void rentGame()
  {

  }

  @Override public String viewGames()
  {
    return null;
  }
}
