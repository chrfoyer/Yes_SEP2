package Model;

import java.util.ArrayList;

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
