package mediator;

import Model.Model;

import java.beans.PropertyChangeSupport;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import Model.*;

public class RmiClient implements RemoteModel
{

  private RemoteModel server;
  private Model model;

  public RmiClient()
  {
    try
    {
      model = new ModelManager();
       server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Case");
      UnicastRemoteObject.exportObject( this, 0);
      System.out.println("Stub pulled");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }



  @Override public void rentGame()
  {

  }

  @Override public String viewGames()
  {
    return null;
  }
}
