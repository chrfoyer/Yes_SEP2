package Model;

import databaseAdapters.*;
import mediator.PasswordEncryptor;

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
    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;
    private GameList games;
    private UserList users;

    /**
     * A zero argument constructor implementing the model and creating a model manager object.
     *
     * @throws SQLException If database errors occur when data access objects
     */
    public ModelManager() throws SQLException
    {
        this.games = new GameList();
        this.users = new UserList();
        this.transactions = TransactionList.getInstance();
        gameDAO = GameImpl.getInstance();
        userDAO = UserImpl.getInstance();
        transactionDAO = TransactionImpl.getInstance();


        //we set up the Administrator account when we run for the first time
        // TODO: 2022. 05. 25. When we delete bob from ddl we have to decrease the initial size
        refreshUserList();
        if (users.size()==0)
        {


            System.out.println("First run detected, creating test users,and administrator");
            User admin = new User("admin", "admin");
            User bob=new User("Bob","test","bob@steffen.com","yes no maybe?","Bob the builder", LocalDate.of(1990,1,1), PasswordEncryptor.getNewSalt());
            User young=new User("Zoomer","fellowkids","bob@steffen.com","yolo Street 10","Jacklin", LocalDate.of(2008,4,20), PasswordEncryptor.getNewSalt());
            User oldMan=new User("boomer","back","older@facebook.com","Emil Møllers gade 20","Herning XYZ", LocalDate.of(1950,5,10), PasswordEncryptor.getNewSalt());
            User jesus=new User("jesus","messiah","son@of.god","Jerusalem","Jesus Christ", LocalDate.of(0,0,0), PasswordEncryptor.getNewSalt());


            userDAO.create(admin);
            userDAO.create(bob);
            userDAO.create(young);
            userDAO.create(oldMan);
            userDAO.create(jesus);

            refreshUserList();
        }

        refreshGameList();
        refreshTransactionList();

    }

    /**
     * Sets the games within the game list
     *
     * @param games The games list to set instance of the game list to
     */
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
     * @return array list of all the games
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
     * method to get a Game from GameList using a Game object
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
     * Gets the most recently added game
     *
     * @return The most recently added game
     * @throws SQLException Thrown when database issues occur
     */
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
     * @throws SQLException Thrown when issues occur with the database
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

    /**
     * Syncs userList with database
     *
     * @throws SQLException Thrown when issues occur with the database
     */
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

    /**
     * Syncs transactionList with database
     *
     * @throws SQLException Thrown when errors occur with the database
     */
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

    /**
     * Tries to log in returns true if successfully
     *
     * @param username String username
     * @param password String password
     * @return boolean depending on success
     */
    @Override
    public boolean login(String username, String password) throws Exception
    {
        System.out.println(username + " logged in");
        if (users.login(username, password))
        {
            System.out.println(username + " logged in");
            return true;
        } else
        {
            System.out.println(username + " failed to log in");
            return false;
        }
    }

    /**
     * Gets the user list
     *
     * @return The user list of the system
     */
    @Override
    public UserList getUserList()
    {
        return users;
    }

    /**
     * Updates the info of the game
     *
     * @param gameOld The old version of the game
     * @param gameNew The new version of the game after the update
     * @throws SQLException Thrown by errors with the database
     */
    @Override
    public void updateGameInfo(Game gameOld, Game gameNew) throws SQLException
    {
        gameDAO.update(gameNew);
        refreshGameList();
        // games.updateGameInfo(gameOld, gameNew);
    }

    /**
     * Remove the specified user
     *
     * @param user The user to remove
     */
    @Override
    public void removeUser(User user) throws SQLException
    {
        if (!userDAO.hasRental(user))
        {
            userDAO.delete(user);
            refreshUserList();
        } else
        {
            throw new IllegalArgumentException("User can not be deleted because they have a rental!");
        }

    }

    //todo remove

    /**
     * @param oldUser The old version of the user
     * @param newUser The new version of the user
     * @deprecated USE SQL VERSION
     * Updates the information of the user
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

    /**
     * Modifies user balance
     *
     * @param amount int value to modify by
     * @param user   the user we are making the change on
     * @throws SQLException in case of database errors
     */
    @Override
    public void modifyBalance(int amount, User user) throws SQLException
    {
        users.modifyBalance(amount, user);
        updateUserWithSQL(user);
        Transaction transaction = new Transaction(user.getUsername(), "Changed balance by: " + amount);
        transactionDAO.create(transaction);
        refreshTransactionList();
    }

    /**
     * Syncs local user with database version
     *
     * @param user is the user we want to sync
     * @throws SQLException in case of database errors
     */
    @Override
    public void updateUserWithSQL(User user) throws SQLException
    {
        //todo is this needed
        //users.findUserInList()
        userDAO.update(user);
        refreshUserList();
    }


    /**
     * Sets hasSubscription to true on the user
     *
     * @param user User which we make the changes on
     * @throws SQLException in case of database errors
     */
    @Override
    public void payForSubscription(User user) throws SQLException
    {
        users.payForSubscription(user);
        updateUserWithSQL(user);
        transactionDAO.create(new Transaction(user.getUsername(), "Payed subscription"));
        refreshTransactionList();
        System.out.println(user.getUsername() + " payed for their subscription");
    }

    /**
     * Simple getter for TransactionList
     *
     * @return TransactionList
     */
    @Override
    public TransactionList getTransactionList()
    {
        return transactions;
    }

    /**
     * Sets the subscriptionStatus of a user
     *
     * @param user   User to make the changes on
     * @param status true or false
     */
    @Override
    public void setSubscriptionStatus(User user, boolean status)
    {
        users.findUserInList(user).setHasSubscription(status);
    }

    /**
     * Leaves review for a game
     *
     * @param review int 1-5
     * @param game   is the Game to make the changes on
     */
    @Override
    public void leaveReview(int review, Game game)
    {
        game.leaveReview(review);
        try
        {
            gameDAO.update(game);
            refreshGameList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets reviews for a game
     *
     * @param game Game used
     * @return float 1.0 - 5.0
     */
    @Override
    public float getReview(Game game)
    {
        return games.findGameInList(game).getReview();
    }

    /**
     * Gets all the games rented by a specific user
     *
     * @param user the User we want to find the games for
     * @return Array list of the games rented by the user
     * @throws SQLException in case of Databse errors
     */
    @Override
    public ArrayList<Game> getGamesRentedByUser(User user) throws SQLException
    {
        return gameDAO.getRentedGamesByUser(user);
    }

    /**
     * Extends the selected game
     *
     * @param game Game selected
     * @param user User that makes the extension on allowed time
     * @throws SQLException in case of database errors
     */
    @Override
    public void extendGame(Game game, User user) throws SQLException
    {
        gameDAO.extend(game);
        transactionDAO.create(new Transaction(user.getUsername(), "extend"));
        refreshGameList();
    }

    /**
     * Gets balance for user
     *
     * @param user is the user we get from
     * @return int balance
     */
    @Override
    public int getBalance(User user)
    {
        return users.getBalance(user);
    }

    /**
     * Adds a new Transaction to the TransactionList
     *
     * @param transaction is a Transaction to be added
     */
    @Override
    public void addTransaction(Transaction transaction)
    {
        transactions.addTransaction(transaction);
    }

    /**
     * Returns a game to the system
     *
     * @param game game to be returned
     * @param user user that returns it
     * @throws SQLException in case of databse erros
     */
    @Override
    public void returnGame(Game game, User user) throws SQLException
    {
        games.findGameInList(game).returnGame();
        transactionDAO.create(new Transaction(user.getUsername(), "Returned " + game.getName()));
        refreshTransactionList();
        gameDAO.returnGame(game);
        System.out.println(user.getUsername() + " returned " + game.getName() + " on " + game.getConsole());
    }

    public void changePassword(User user, String newPassword) throws SQLException
    {
        User storedOnServer = users.findUserInList(user);
        storedOnServer.changePassword(newPassword);
        userDAO.update(storedOnServer);
        refreshUserList();
    }

}
