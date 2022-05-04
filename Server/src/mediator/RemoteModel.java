package mediator;

import Model.Game;
import Model.GameList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteModel extends Remote
{
  public void rentGame(Game game) throws RemoteException;
  public GameList viewGames() throws RemoteException;
}
