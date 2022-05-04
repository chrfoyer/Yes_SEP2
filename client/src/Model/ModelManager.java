package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModelManager implements Model {
  private GameList games;

  public ModelManager() {
    this.games = new GameList();
  }

  /**
   * Adds a new Game to the list
   *
   * @param game is the Game to be added to the list
   */
  @Override
  public void addGame(Game game) {
    games.addGame(game);
  }

  /**
   * returns all the games
   *
   * @return arrayList<Game> of all the games
   */
  @Override
  public ArrayList<Game> getAllGames() {
    return games.getGames();
  }

  /**
   * We remove a specific game from the list
   *
   * @param game is the game to be removed
   */
  @Override
  public void removeGame(Game game) {
    games.removeGame(game);
  }

  /**
   * Decrements the days left in the rental period for all games in the list
   */
  @Override
  public void decrementDay() {
    games.decrementDayForRented();
  }

  /**
   * Sets the rented to true for given game
   *
   * @param game to be rented
   */
  @Override
  public void rentGame(Game game) {
    if (game == null)
      throw new IllegalArgumentException("Game to rent cant be null");
    game.rentGame();
  }

  /**
   * method to get a Gmae from GameList using a Game object
   * 
   * @param name of the game to be searched for
   * @return the selected game from the GameList
   */
  @Override
  public Game getGame(String name) {
    return games.getGame(name);
  }

  /**
   * method to get a Game from GameList using its name
   * 
   * @param game to be searched for
   * @return the selected game from the GameList
   */
  @Override
  public Game getGame(Game game) {
    return games.getGame(game);
  }

  /**
   * Method to get all non-rented games
   * 
   * @return an ArrayList containing all non-rented Games
   */

  @Override
  public ArrayList<Game> getALlAvailableGames() {
    return games.getAvailableGames();
  }

  /**
   * Method to get the GameList property for easier server usage
   * @return GameList containing everything
   */
  @Override
  public GameList getGameList() {
    return games;
  }
}
