package mediator;

import Model.Game;
import Model.GameList;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *Interface used to establish Client - Server connection using RMI
 *
 * @author Chris,Levente,Kruno,Martin
 * @version 0.2 5/5/22
 */
public interface RemoteModel extends Remote
{
  void rentGame(Game game) throws RemoteException;
  void rentGame(String name) throws RemoteException;
  Game getGame(String name) throws RemoteException;
  GameList viewGames() throws RemoteException;
  boolean containsGame(String name) throws RemoteException;
}
