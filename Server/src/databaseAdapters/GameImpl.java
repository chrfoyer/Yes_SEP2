package databaseAdapters;

import Model.Game;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GameImpl implements GameDAO
{
    private static final Object lock = new Object();
    private static GameImpl instance;

    private GameImpl() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

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

    // TEST
    public static void main(String[] args)
    {
        try
        {
            GameImpl testThing = GameImpl.getInstance();
            Game game1 = new Game("Beast", "Bronzy", "PC", "T");
            System.out.println(testThing.create(game1));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=game_test",
                DBKey.username, DBKey.password);
    }

    @Override
    public Game create(String name, String producer, String console,
                       String esrb) throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO games"
                            + "(name, producer, console, rented, daysLeft, review, esrb, dateAdded) "
                            + "VALUES (?, ?, ?, false, 14, 3.0, ?, CURRENT_DATE);");
            statement.setString(1, name);
            statement.setString(2, producer);
            statement.setString(3, console);
            statement.setString(4, esrb);
            statement.executeUpdate();
            statement.close();
            return new Game(name, producer, console, esrb);
        }
    }

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

    @Override
    public Game readById(int id) throws SQLException
    {
        Game readGame = null;
        try (Connection connection = getConnection())
        {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * " +
                            "FROM games " +
                            "WHERE id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                System.out.println("Id: " + rs.getString("id"));
                String name = rs.getString("name");
                String producer = rs.getString("producer");
                String console = rs.getString("console");
                String esrb = rs.getString("esrb");
                readGame = new Game(name, producer, console, esrb);

            }
            rs.close();
            st.close();
        }
        return readGame;
    }

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

    @Override
    public ArrayList<Game> getRentedGamesByUser(User user) throws SQLException
    {
        ArrayList<Game> temp = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT *\n" +
                            "FROM games\n" +
                            "         JOIN game_test.rentals r ON games.id = r.game_id\n" +
                            "WHERE username = ? AND active = TRUE; "
            );
            statement.setString(1, user.getUsername());
        }
        return temp;
    }

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
                            "esrb = ? " +
                            "WHERE id = ?;"
            );
            statement.setString(1, game.getName());
            statement.setString(2, game.getProducer());
            statement.setString(3, game.getConsole());
            statement.setString(4, game.getEsrb());
            statement.setInt(5, game.getId());
            statement.executeUpdate();
            statement.close();
        }
    }

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
