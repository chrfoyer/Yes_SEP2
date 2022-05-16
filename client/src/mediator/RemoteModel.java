package mediator;

import Model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used to establish Client - Server connection using RMI
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.3 5/5/22
 */
public interface RemoteModel extends Remote {
  void rentGame(Game game) throws RemoteException;

  void rentGame(String name) throws RemoteException;

  Game getGame(String name) throws RemoteException;

  GameList viewGames() throws RemoteException;

  boolean containsGame(String name) throws RemoteException;

  void signup(User user) throws RemoteException;

  boolean login(User user) throws RemoteException;

  UserList getUserList() throws RemoteException;

  void updateGameInfo(Game gameOld, Game gameNew) throws RemoteException;

  void addGame(Game game)throws RemoteException;

  void removeGame(Game game)throws RemoteException;

  void addTransaction(Transaction transaction)throws RemoteException;
}
