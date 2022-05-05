package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object that stores an ArrayList of games, given by the server when a client wants to view the games
 */
public class GameList implements Serializable {
    private ArrayList<Game> games;

    /**
     * Constructor that initializes arraylist
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
     * Loops through all the games it holds and removes the given game
     *
     * @param name game to be removed from the ArrayList
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
     * Gives the ArrayList in a given format
     * "Game -> [Name] : [X] days left
     *  Rented: true / false";
     *
     * @return formatted ArrayList
     */
    @Override
    public String toString() {

        String ret = "";

        for (Game game : games
        ) {
            ret += "Game -> " + game.getName() + " : " + "Rented: " + game.isRented() + "\n";
            if (game.isRented())
            {
                ret += game.getDaysLeft() + " days left\n";
            }
        }
        return ret;
    }
}
