package Model;

import databaseAdapters.*;

import java.sql.SQLException;
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
    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;
    private GameList games;
    private UserList users;

    public ModelManager() throws SQLException
    {
        this.games = new GameList();
        this.users = new UserList();
        this.transactions = TransactionList.getInstance();
        gameDAO = GameImpl.getInstance();
        userDAO = UserImpl.getInstance();
        transactionDAO = TransactionImpl.getInstance();
        refreshGameList();
        refreshUserList();
        refreshTransactionList();
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
        games.addGame(gameDAO.create(game));
        System.out.println("Game added: " + game.getName() + " on " + game.getConsole());
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
    public void removeGame(Game game) throws SQLException
    {
        gameDAO.delete(game);
        games.removeGame(game);
        System.out.println("Game removed: " + game.getName() + " on " + game.getConsole());
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
    public void rentGame(Game game, User user) throws SQLException
    {
        if (game == null)
        {
            throw new IllegalArgumentException("Game to rent cant be null");
        } else if (game.getEsrb().equals("M") && user.getAge() < 17)
        {
            throw new IllegalAccessError("You are too young to rent this game!");
        } else if (game.getEsrb().equals("AO") && user.getAge() < 18)
        {
            throw new IllegalAccessError("You are too young to rent this game!");
        } else
        {
            games.findGameInList(game).rentGame();
            gameDAO.rent(game, user);
            transactionDAO.create(new Transaction(user.getUsername(), "Rented " + game.getName()));
            refreshTransactionList();
            refreshGameList();
            System.out.println(user.getUsername() + " rented " + game.getName() + " on " + game.getConsole());
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

    @Override
    public Game getMostRecentGame() throws SQLException
    {
        return gameDAO.readMaxId();
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
     * Syncs gameList with database
     *
     * @throws SQLException
     */
    @Override
    public void refreshGameList() throws SQLException
    {
        GameList temp = new GameList();
        for (Game game : gameDAO.getAllGames())
        {
            gameDAO.updateRentalInfo(game);
            temp.addGame(game);
        }
        games = temp;
        System.out.println("Game list refreshed");
    }

    @Override
    public void refreshUserList() throws SQLException
    {
        UserList temp = new UserList();
        for (User user : userDAO.getAllUsers()
        )
        {
            temp.addUser(user);
        }
        users = temp;
    }

    @Override
    public void refreshTransactionList() throws SQLException
    {
        ArrayList<Transaction> temp = transactionDAO.getAllTransactions();
        transactions.setTransactions(temp);
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
     * Returns an array list containing games matching the parameters of the search.
     *
     * @param name    Part of the name of the games returned
     * @param console The selected console
     * @param esrb    The selected age restriction rating
     * @return The arraylist of games matching the parameters
     */
    @Override
    public ArrayList<Game> searchGames(String name, String console, String esrb)
    {
        ArrayList<Game> searchArray = new ArrayList<>(games.getAvailableGames());
        ArrayList<Game> returnArray = new ArrayList<>();
        // Runs in reverse order to avoid commodification exception (can't remove and browse at the same time)
        for (int i = searchArray.size() - 1; i >= 0; i--)
        {
            boolean add = true;
            if (name != null)
            {
                if (!searchArray.get(i).getName().contains(name))
                {
                    add = false;
                }
            }
            if (console != null)
            {
                if (!searchArray.get(i).getConsole().equals(console))
                {
                    add = false;
                }
            }
            if (esrb != null)
            {
                if (!searchArray.get(i).getEsrb().equals(esrb))
                {
                    add = false;
                }
            }
            if (add)
            {
                returnArray.add(searchArray.get(i));
            }
        }
        return returnArray;
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
                break;
            }
        }
        return ret;
    }

    /**
     * Signs up user on the system
     *
     * @param user User we get from client
     * @throws SQLException in case a database error
     */
    @Override
    public void signup(User user) throws SQLException
    {
        for (User temp : users.getUsers()
        )
        {
            if (temp.equals(user.getUsername()))
                throw new IllegalArgumentException("User already exists on the server!");
        }
        if (user.getUsername().length() > 30) throw new IllegalArgumentException("Username is too long!");
        User created = userDAO.create(user);
        users.addUser(created);
        System.out.println(user.getName() + " signed up with username " + user.getUsername());
    }

    @Override
    public boolean login(User user)
    {
        System.out.println(user.getUsername() + " logged in");
        if (users.login(user))
        {
            System.out.println(user.getUsername() + " logged in");
            return true;
        } else
        {
            System.out.println(user.getUsername() + " failed to log in");
            return false;
        }
    }

    @Override
    public UserList getUserList()
    {
        return users;
    }

    @Override
    public void updateGameInfo(Game gameOld, Game gameNew) throws SQLException
    {
        gameDAO.update(gameNew);
        refreshGameList();
        // games.updateGameInfo(gameOld, gameNew);
    }

    @Override
    public void removeUser(User user)
    {
        users.removeUser(user);
    }

    //todo remove

    /**
     * @deprecated USE SQL VERSION
     */
    @Override
    public void updateUserInfo(User oldUser, User newUser)
    {
        users.updateUserInfo(oldUser, newUser);
        //todo fix
        try
        {
            userDAO.update(newUser);
            refreshUserList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyBalance(int amount, User user) throws SQLException
    {
        users.modifyBalance(amount, user);
        updateUserWithSQL(user);
        Transaction transaction = new Transaction(user.getUsername(), "Changed balance by: " + amount);
        transactionDAO.create(transaction);
        refreshTransactionList();
    }

    @Override
    public void updateUserWithSQL(User user) throws SQLException
    {
        userDAO.update(users.findUserInList(user));
        refreshUserList();
    }

    @Override
    public void payForSubscription(User user) throws SQLException
    {
        users.payForSubscription(user);
        updateUserWithSQL(user);
        transactionDAO.create(new Transaction(user.getUsername(), "Payed subscription"));
        refreshTransactionList();
        System.out.println(user.getUsername() + " payed for their subscription");
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
    public ArrayList<Game> getGamesRentedByUser(User user) throws SQLException
    {
        return gameDAO.getRentedGamesByUser(user);
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
    public void returnGame(Game game, User user) throws SQLException
    {
        games.findGameInList(game).returnGame();
        transactionDAO.create(new Transaction(user.getUsername(), "Returned " + game.getName()));
        refreshTransactionList();
        gameDAO.returnGame(game);
        System.out.println(user.getUsername() + " returned " + game.getName() + " on " + game.getConsole());
    }

}
