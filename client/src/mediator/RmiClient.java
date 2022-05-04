package mediator;

import Model.Model;

import java.beans.PropertyChangeSupport;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Model.*;

public class RmiClient
{

  private RemoteModel server;
  private Model model;

  public RmiClient() throws RemoteException
  {
    try
    {
      model = new ModelManager();
       server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Games");
      // UnicastRemoteObject.exportObject( this, 0);   for callback
      System.out.println("Stub pulled");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void rentGame(Game game) throws RemoteException {
    server.rentGame(game);
  }

  public GameList viewGames() throws RemoteException {
    return server.viewGames();
  }
}
