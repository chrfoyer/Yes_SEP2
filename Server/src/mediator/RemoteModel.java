package mediator;

import Model.Game;
import Model.GameList;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *Interface used to establish Client - Server connection using RMI
 */
public interface RemoteModel extends Remote
{
  void rentGame(Game game) throws RemoteException;
  void rentGame(String name) throws RemoteException;
  Game getGame(String name) throws RemoteException;
  GameList viewGames() throws RemoteException;
}
