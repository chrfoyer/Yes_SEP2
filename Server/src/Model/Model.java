package Model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for implementing methods used in the Model package
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public interface Model {
  void addGame(Game game) throws SQLException;

  ArrayList<Game> getAllGames();

  void removeGame(Game game);

  void removeGame(String name);

  void decrementDay();

  void rentGame(Game game, User user);

  void returnGame(Game game, User user);

  Game getGame(String name);

  Game getGame(Game game);

  ArrayList<Game> getALlAvailableGames();

  GameList getGameList();

  boolean containsGame(String name);

  void signup(User user);

  boolean login(User user);

  UserList getUserList();

  void updateGameInfo(Game gameOld, Game gameNew);

  void addTransaction(Transaction transaction);

  void removeUser(User user);

  void updateUserInfo(User oldUser, User newUser);

  int getBalance(User user);

  void modifyBalance(int amount, User user);

  void payForSubscription(User user);

  TransactionList getTransactionList();

  void setSubscriptionStatus(User user, boolean status);

  void leaveReview(int review, Game game);

  float getReview(Game game);

  ArrayList<Game> getGamesRentedByUser(User user);
}
