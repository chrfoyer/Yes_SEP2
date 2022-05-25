package databaseAdapters;

import Model.Game;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class implementing the game data access object. Statements are prepared and then executed on the database. The
 * singleton design pattern is used to avoid registering the driver too many times.
 *
 * @author Chris, Martin, Levente, Kruno
 * @version v0.3 23/5/22
 */
public class GameImpl implements GameDAO
{
    private static final Object lock = new Object();
    private static GameImpl instance;

    /**
     * A zero argument private constructor creating the object by registering the driver.
     *
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    private GameImpl() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    /**
     * A static method returning the instance of the object. The private constructor is called when the instance is
     * null.
     *
     * @return The instance of the game implementation
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    public static GameImpl getInstance() throws SQLException
    {
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new GameImpl();
                }
            }
        }
        return instance;
    }

    /**
     * A method for returning the connection to the database. It uses the credentials held within the DBKey class to
     * authenticate.
     *
     * @return The connection to the database
     * @throws SQLException Thrown when the credentials or url do not match the database
     */
    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=game_test",
                DBKey.username, DBKey.password);
    }

    /**
     * Inserts the game in the games relation within the database and returns the game object with the id.
     *
     * @param game The game to add to the database
     * @return The game that was inserted with the serial id included
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public Game create(Game game) throws SQLException
    {
        Game createdGame = null;
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO games"
                            + "(name, producer, console, rented, days_left," +
                            " review_count, review_sum, review_avg, esrb, date_added) "
                            + "VALUES (?, ?, ?, ?, ?," +
                            " ?, ?, ?, ?, ?);");
            statement.setString(1, game.getName());
            statement.setString(2, game.getProducer());
            statement.setString(3, game.getConsole());
            statement.setBoolean(4, game.isRented());
            statement.setInt(5, game.getDaysLeft());
            statement.setInt(6, game.getReviewCount());
            statement.setInt(7, game.getReviewSum());
            statement.setFloat(8, game.getReviewAverage());
            statement.setString(9, game.getEsrb());
            statement.setDate(10, Date.valueOf(game.getDateAdded()));
            statement.executeUpdate();
            statement.close();

            createdGame = readMaxId();
        }
        return createdGame;
    }

    /**
     * Returns the game with the highest id and therefore the most recent game to be added to the database
     *
     * @return The game object with the highest id
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public Game readMaxId() throws SQLException
    {
        Game readGame = null;
        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " +
                            "FROM games " +
                            "ORDER BY id DESC " +
                            "LIMIT 1");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String producer = rs.getString("producer");
                String console = rs.getString("console");
                boolean rented = rs.getBoolean("rented");
                int daysLeft = rs.getInt("days_left");
                int reviewCount = rs.getInt("review_count");
                int reviewSum = rs.getInt("review_sum");
                float reviewAvg = rs.getFloat("review_avg");
                String esrb = rs.getString("esrb");
                Date dateSQL = rs.getDate("date_added");
                LocalDate dateAdded = null;
                if (dateSQL != null)
                {
                    dateAdded = dateSQL.toLocalDate();
                }
                readGame = new Game(id, name, producer, console, rented, daysLeft, reviewCount, reviewSum, reviewAvg, esrb, dateAdded);
            }
            rs.close();
            st.close();
        }
        return readGame;
    }

    /**
     * Returns all the games within the database
     *
     * @return An arraylist of all the games in the database
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public ArrayList<Game> getAllGames() throws SQLException
    {
        ArrayList<Game> gameArrayList = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " +
                            "FROM games;");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String producer = rs.getString("producer");
                String console = rs.getString("console");
                boolean rented = rs.getBoolean("rented");
                int daysLeft = rs.getInt("days_left");
                int reviewCount = rs.getInt("review_count");
                int reviewSum = rs.getInt("review_sum");
                float reviewAvg = rs.getFloat("review_avg");
                String esrb = rs.getString("esrb");
                Date dateSQL = rs.getDate("date_added");
                LocalDate dateAdded = null;
                if (dateSQL != null)
                {
                    dateAdded = dateSQL.toLocalDate();
                }
                gameArrayList.add(new Game(id, name, producer, console, rented, daysLeft, reviewCount, reviewSum, reviewAvg, esrb, dateAdded));
            }
            st.close();
            rs.close();
        }
        return gameArrayList;
    }

    /**
     * A method to get all the games currently rented by a specified user.
     *
     * @param user The user that the information is requested for
     * @return The arraylist of all games that are actively rented by the user
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public ArrayList<Game> getRentedGamesByUser(User user) throws SQLException
    {
        ArrayList<Game> gameArrayList = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT *\n" +
                            "FROM games\n" +
                            "         JOIN game_test.rentals r ON games.id = r.game_id\n" +
                            "WHERE username = ? AND active = TRUE; "
            );
            statement.setString(1, user.getUsername());
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String producer = rs.getString("producer");
                String console = rs.getString("console");
                boolean rented = rs.getBoolean("rented");
                int daysLeft = rs.getInt("days_left");
                int reviewCount = rs.getInt("review_count");
                int reviewSum = rs.getInt("review_sum");
                float reviewAvg = rs.getFloat("review_avg");
                String esrb = rs.getString("esrb");
                Date dateSQL = rs.getDate("date_added");
                LocalDate dateAdded = null;
                if (dateSQL != null)
                {
                    dateAdded = dateSQL.toLocalDate();
                }
                gameArrayList.add(new Game(id, name, producer, console, rented, daysLeft, reviewCount, reviewSum, reviewAvg, esrb, dateAdded));
            }
        }
        return gameArrayList;
    }

    /**
     * A method to set the days left in the game object to the value within the rental in the database.
     *
     * @param game The game that needs the days left set
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void updateRentalInfo(Game game) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM rentals WHERE game_id=" + game.getId() + ";");
            while (rs.next())
            {
                game.setDaysLeft(rs.getInt("days_left"));
                update(game);
            }
            rs.close();
            st.close();
        }

    }

    /**
     * A method to update the information of the game by the admin.
     *
     * @param game The game that needs to be updated
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void update(Game game) throws SQLException
    {
        // This is not ideal because a game should have an id unseen by the user
        // For now, this checks the name and console, which effectively means those cannot be accurately changed
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE games " +
                            "SET name = ?, " +
                            "producer = ?, " +
                            "console = ?, " +
                            "esrb = ?, " +
                            "review_count = ?, " +
                            "review_sum = ?, " +
                            "review_avg = ?, " +
                            "days_left = ? " +
                            "WHERE id = ?;"
            );
            statement.setString(1, game.getName());
            statement.setString(2, game.getProducer());
            statement.setString(3, game.getConsole());
            statement.setString(4, game.getEsrb());
            statement.setInt(5, game.getReviewCount());
            statement.setInt(6, game.getReviewSum());
            statement.setFloat(7, game.getReviewAverage());

            statement.setInt(8, game.getDaysLeft());
            statement.setInt(9, game.getId());
            statement.executeUpdate();
            statement.close();
        }
    }

    /**
     * A method to communicate the rental of the game to the database. This inserts a new line in the rentals relation
     * that contains information about the rental.
     *
     * @param game The game to be rented
     * @param user The user renting the game
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void rent(Game game, User user) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO rentals " +
                            "(game_id, username, date_rented, rental_length_allowed, active, overdue) " +
                            "VALUES (?, ?, CURRENT_DATE, 14, TRUE, FALSE); " +

                            "UPDATE games " +
                            "SET rented = TRUE " +
                            "WHERE id = ?; "
            );
            st.setInt(1, game.getId());
            st.setString(2, user.getUsername());
            st.setInt(3, game.getId());
            st.executeUpdate();
            st.close();
        }
    }

    /**
     * A method to communicate with the database that a game has been returned.
     *
     * @param game The game to be returned
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    public void returnGame(Game game) throws SQLException
    {
        // TODO: 19/05/2022 Trigger rented within games from active in rental
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE rentals " +
                            "SET date_returned = CURRENT_DATE, " +
                            "    active        = FALSE " +
                            "WHERE game_id = ? " +
                            "  AND active = TRUE; " +

                            "UPDATE games " +
                            "SET rented = FALSE, " +
                            "days_left = 0 " +
                            "WHERE id = ?; "
            );
            statement.setInt(1, game.getId());
            statement.setInt(2, game.getId());
            statement.executeUpdate();
            statement.close();
        }
    }

    @Override public void extend(Game game) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement1 = connection.prepareStatement(
                    "SELECT rental_period_allowed " +
                            "FROM rentals " +
                            "WHERE game_id = ? " +
                            "AND active = true;"
            );
            statement1.setInt(1, game.getId());
            ResultSet rs = statement1.executeQuery();
        }
    }

    /**
     * A method to delete a game from the database.
     *
     * @param game The game to be deleted
     * @throws SQLException Thrown when the connection with the database cannot be established
     */
    @Override
    public void delete(Game game) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM games " + "WHERE id = ? ");
            statement.setInt(1, game.getId());
            statement.executeUpdate();
            statement.close();
        }
    }
}
