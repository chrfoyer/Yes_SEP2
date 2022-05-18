package Model;

import databaseAdapters.GameDAO;
import databaseAdapters.GameImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the methods listed in the Model interface
 *
 * @author Chris, Martin, Levente, Kruno
 * @version 0.2 5/5/22
 */
public class ModelManager implements Model
{
  private final TransactionList transactions;
  private final GameDAO gameDAO;
  private GameList games;
  private UserList users;

  public ModelManager() throws SQLException
  {
    this.games = new GameList();
    this.users = new UserList();
    this.transactions = TransactionList.getInstance();
    gameDAO = GameImpl.getInstance();

    //todo remove test
    users.addUser(new User("admin", "admin"));
    User bob = new User("bob", "test");
    bob.setHasSubscription(true);
    users.addUser(bob);
    LocalDate date = LocalDate.of(1997, 3, 3);
    users.addUser(new User("martin", "maxmax1", "asdf@", "afdadf", "martin r", date));
    games.addGame(new Game("Minecraft", "Mojang", "PC", "E"));
    games.addGame(new Game("CockAndBalls", "ShitFart", "Xbox", "E"));
    Transaction transaction = new Transaction("rent", "admin", 3.5);
  }

  public void setGames(GameList games)
  {
    this.games = games;
  }

  public void setUsers(UserList users)
  {
    this.users = users;
  }

  /**
   * Adds a new Game to the list
   *
   * @param game is the Game to be added to the list
   */
  @Override
  public void addGame(Game game) throws SQLException
  {
    games.addGame(gameDAO.create(game)); // todo migrate to db
  }

  /**
   * returns all the games
   *
   * @return arrayList<Game> of all the games
   */
  @Override
  public ArrayList<Game> getAllGames()
  {
    return games.getGamesArrayCopy();
  }

  /**
   * We remove a specific game from the list
   *
   * @param game is the game to be removed
   */
  @Override
  public void removeGame(Game game)
  {
    games.removeGame(game);
  }

  /**
   * Removes a game from the GameList using its name
   *
   * @param name name of the game to be removed
   */
  @Override
  public void removeGame(String name)
  {
    games.removeGame(name);
  }

  /**
   * Decrements the days left in the rental period for all games in the list
   */
  @Override
  public void decrementDay()
  {
    games.decrementDayForRented();
  }

  /**
   * Sets the rented to true for given game
   *
   * @param game to be rented
   */
  @Override
  public void rentGame(Game game, User user)
  {
    if (game == null)
    {
      throw new IllegalArgumentException("Game to rent cant be null");
    } else
    {
      games.findGameInList(game).rentGame();
      new Transaction(game, "Rent", user.getUsername());

    }
  }

  /**
   * method to get a Gmae from GameList using a Game object
   *
   * @param name of the game to be searched for
   * @return the selected game from the GameList
   */
  @Override
  public Game getGame(String name)
  {
    return games.getGame(name);
  }

  /**
   * method to get a Game from GameList using its name
   *
   * @param game to be searched for
   * @return the selected game from the GameList
   */
  @Override
  public Game getGame(Game game)
  {
    return games.getGame(game);
  }

  /**
   * Method to get all non-rented games
   *
   * @return an ArrayList containing all non-rented Games
   */

  @Override
  public ArrayList<Game> getALlAvailableGames()
  {
    return games.getAvailableGames();
  }

  /**
   * Method to get the GameList property for easier server usage
   *
   * @return GameList containing everything
   */
  @Override
  public GameList getGameList()
  {
    return games;
  }

  /**
   * Method to check if a given name is in the gameList
   *
   * @param name is the name of the game
   * @return true if the gameList contains the game false if it does not
   * @author Raedrim
   */
  @Override
  public boolean containsGame(String name)
  {
    boolean ret = false;
    for (Game game : games.getGamesArrayCopy())
    {
      if (game.getName().equals(name))
      {
        ret = true;
      }
    }
    return ret;
  }

  @Override
  public void signup(User user)
  {
    if (users.contains(user))
      throw new IllegalArgumentException(
              "Given user is already registered in the system!");
    else
      users.addUser(user);
  }

  @Override
  public boolean login(User user)
  {
    return users.login(user);
  }

  @Override
  public UserList getUserList()
  {
    return users;
  }

  @Override
  public void updateGameInfo(Game gameOld, Game gameNew)
  {
    games.updateGameInfo(gameOld, gameNew);
  }

  @Override
  public void removeUser(User user)
  {
    users.removeUser(user);
  }

  @Override
  public void updateUserInfo(User oldUser, User newUser)
  {
    users.updateUserInfo(oldUser, newUser);
  }

  @Override
  public void modifyBalance(int amount, User user)
  {
    users.modifyBalance(amount, user);
    Transaction transaction = new Transaction("Add money", user.getUsername(),
            amount);
  }

  @Override
  public void payForSubscription(User user)
  {
    users.payForSubscription(user);
  }

  @Override
  public TransactionList getTransactionList()
  {
    return transactions;
  }

  @Override
  public void setSubscriptionStatus(User user, boolean status)
  {
    users.findUserInList(user).setHasSubscription(status);
  }

  @Override
  public void leaveReview(int review, Game game)
  {
    games.findGameInList(game).leaveReview(review);
  }

  @Override
  public float getReview(Game game)
  {
    return games.findGameInList(game).getReview();
  }

  @Override
  public ArrayList<Game> getGamesRentedByUser(User user)
  {
    // TODO: 2022. 05. 18. waiting for database
    return null;
  }

  @Override
  public int getBalance(User user)
  {
    return users.getBalance(user);
  }

  @Override
  public void addTransaction(Transaction transaction)
  {
    transactions.addTransaction(transaction);
  }

  @Override
  public void returnGame(Game game, User user)
  {
    games.findGameInList(game).returnGame();
    new Transaction(game, "Return", user.getUsername());
  }

}
