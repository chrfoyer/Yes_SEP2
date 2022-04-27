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
   *
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

  /**
   * Decrements the days left in the rental period for all games in the list
   */
  @Override public void decrementDay()
  {
    games.decrementDayForRented();
  }

  /**
   * Sets the rented to true for given game
   *
   * @param game to be rented
   */
  @Override public void rentGame(Game game)
  {
    if (game == null)
      throw new IllegalArgumentException("Game to rent cant be null");
    game.rentGame();
  }

  @Override
  public Game getGame(String name) {
    return games.getGame(name);
  }

  @Override
  public Game getGame(Game game) {
    return games.getGame(game);
  }

  @Override public ArrayList<Game> getALlAvailableGames()
  {
    return games.getAvailableGames();
  }
}
