package Model;

import java.util.ArrayList;

/**
 * Interface for implementing methods used in the Model package
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
}
