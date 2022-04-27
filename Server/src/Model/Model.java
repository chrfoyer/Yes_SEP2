package Model;

import java.util.ArrayList;

public interface Model
{
  public void addGame(Game game);
  public ArrayList<Game> getAllGames();
  public void removeGame(Game game);
}
