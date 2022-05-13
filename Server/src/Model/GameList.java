package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class with an ArrayList of games, where games are added, removed and edited
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public class GameList implements Serializable {
  private ArrayList<Game> games;

  /**
   * constructor that initializes arraylist
   */
  public GameList() {
    games = new ArrayList<>();
  }

  /**
   * @param game Game object
   * @return returns the game that you search for
   */
  public Game getGame(Game game) {
    if (game == null)
      throw new IllegalArgumentException("Cant find an empty game");

    if (!games.contains(game))
      System.out.println("game not found");
    else {
      for (int i = 0; i < games.size(); i++) {
        if (games.get(i).equals(game)) {
          return games.get(i);
        }
      }
    }
    return null;
  }

  /**
   * Gets the game object from the game list using the name of the game.
   *
   * @param name The string name of the game
   * @return The game object with the same name as the parameter
   */
  public Game getGame(String name) {
    for (int i = 0; i < games.size(); i++) {
      if (games.get(i).getName().equals(name)) {
        System.out.println(games.get(i));
        return games.get(i);
      }
    }
    return null;
  }

  /**
   * Returns the contents as an ArrayList not as a GameList
   *
   * @return list of all the games in the game arraylist
   */
  public ArrayList<Game> getGamesArrayCopy() {
    return games;
  }

  /**
   * Adds a game to the arraylist
   *
   * @param game to be added to arraylist
   */
  public void addGame(Game game) {
    if (game == null)
      throw new IllegalArgumentException("Game cant be null");
    games.add(game);
  }

  /**
   * Removes a game from arraylist
   *
   * @param game to be removed from list
   */
  public void removeGame(Game game) {
    if (game == null)
      throw new IllegalArgumentException("Game to be removed cant be null");
    games.remove(game);
  }

  /**
   * Removes a game from arraylist
   *
   * @param name to be removed from list
   */
  public void removeGame(String name) {
    for (int i = 0; i < games.size(); i++) {
      if (games.get(i).getName().equals(name)) {
        games.remove(i);
        break;
      }
    }
  }

  /**
   * Gets all non rented game
   *
   * @return returns an arrayList of games
   */
  public ArrayList<Game> getAvailableGames() {
    ArrayList<Game> ret = new ArrayList<>();
    for (Game game : games) {
      if (!game.isRented())
        ret.add(game);
    }
    return ret;
  }

  /**
   * Decrements the days left in all games that are rented within the list.
   */
  public void decrementDayForRented() {
    for (int i = 0; i < games.size(); i++) {
      if (games.get(i).isRented()) {
        games.get(i).decrementDaysLeft();
      }
    }
  }

  /**
   * Rents a game using its name
   *
   * @param name game to be rented
   */
  public void rentGame(String name) {
    Game rentTemp = getGame(name);
    removeGame(name);
    rentTemp.rentGame();
    addGame(rentTemp);
  }

  /**
   * Returns a string containing the attributes of the game. If the game is not rented, the days left will not appear.
   *
   * @return The string with the information about the game object.
   */
  @Override
  public String toString() {

    String ret = "";

    for (Game game : games) {
      ret += "Game -> " + game.getName() + " : " + game.getProducer() + " : "
              + game.getEsrb() + " : Rented: " + game.isRented() + "\n";
      if (game.isRented()) {
        ret += game.getDaysLeft() + " days left\n";
      }
    }
    return ret;
  }

  public void updateGameInfo(Game gameOld, Game gameNew) {
    boolean foundOld = false;
    for (Game game : games) {
      if (gameOld.equals(game)) {
        if (game.isRented())
          throw new IllegalStateException(
                  "Game is rented, changing of information is not allowed!");
        game.setName(gameNew.getName());
        game.setEsrb(gameNew.getEsrb());
        game.setConsole(gameNew.getConsole());
        game.setProducer(gameNew.getProducer());
        foundOld = true;
      }
    }
    if (!foundOld)
      throw new IllegalArgumentException(
              "No game found on server that could be updated");
  }
}
