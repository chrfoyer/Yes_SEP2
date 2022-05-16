package Model;

import java.util.ArrayList;

/**
 * Interface for implementing methods used in the Model package
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public interface Model
{
  void addGame(Game game);

  ArrayList<Game> getAllGames();

  void removeGame(Game game);

  void removeGame(String name);

  void decrementDay();

  void rentGame(Game game);

  void rentGame(String name);

  Game getGame(String name);

  Game getGame(Game game);

  ArrayList<Game> getALlAvailableGames();

  GameList getGameList();

  public boolean containsGame(String name);

  void signup(User user);

  boolean login(User user);

  public UserList getUserList();

  public void updateGameInfo(Game gameOld, Game gameNew);

  void addTransaction(Transaction transaction);

  public void removeUser(User user);

  public void updateUserInfo(User oldUser, User newUser);
}
