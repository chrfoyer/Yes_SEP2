package Model;

import java.util.ArrayList;

public interface Model
{
  public void addGame(Game game);
  public ArrayList<Game> getAllGames();
  public void removeGame(Game game);
  public void removeGame(String name);
  public void decrementDay();
  public void rentGame(Game game);
  public Game getGame(String name);
  public Game getGame(Game game);
  public ArrayList<Game> getALlAvailableGames();
  public GameList getGameList();
}
