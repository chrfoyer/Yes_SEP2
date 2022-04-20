package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModelManager implements Model
{
  private GameList games;

  public ModelManager()
  {
    this.games = new GameList();
  }

  /**
   * Adds a new Game to the list
   *
   * @param game is the Game to be added to the list
   */
  @Override public void addGame(Game game)
  {
    games.addGame(game);
  }

  /**
   * returns all the games
   * @return arrayList<Game> of all the games
   */
  @Override public ArrayList<Game> getAllGames()
  {
    return games.getGames();
  }

  /**
   * We remove a specific game from the list
   *
   * @param game is the game to be removed
   */
  @Override public void removeGame(Game game)
  {
    games.removeGame(game);
  }
}
